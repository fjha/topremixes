package musicplayer.flora.android.application.remixes;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayListActivity extends ListActivity {
	// Songs list
	public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	//Ads
	InterstitialAd mInterstitialAd5;
	AdRequest adRequestInterstital5;

	private AdView mAdView5;
    private AdView mAdView6;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);

		// Ads
		mAdView5 = (AdView) findViewById(R.id.adView5);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView5.loadAd(adRequest);


        mAdView6 = (AdView) findViewById(R.id.adView6);
        mAdView6.loadAd(adRequest);

		MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_ad_id));

		// Ads Interstitial
		mInterstitialAd5 = new InterstitialAd(this);
		mInterstitialAd5.setAdUnitId(getString(R.string.ad_unit_id7));
		adRequestInterstital5 = new AdRequest.Builder().build();
		mInterstitialAd5.loadAd(adRequestInterstital5);


		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

		SongsManager plm = new SongsManager();
		// get all songs from sdcard
		this.songsList = plm.getPlayList();

		// looping through playlist
		for (int i = 0; i < songsList.size(); i++) {
			// creating new HashMap
			HashMap<String, String> song = songsList.get(i);

			// adding HashList to ArrayList
			songsListData.add(song);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, songsListData,
				R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
				R.id.songTitle });

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();
		// listening to single listitem click
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
				// getting listitem index
				int songIndex = position;

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						AndroidBuildingMusicPlayerActivity.class);
				// Sending songIndex to PlayerActivity
				in.putExtra("songIndex", songIndex);
				setResult(100, in);
				// Closing PlayListView
				finish();
			}
		});

	}


	public void onBackPressed() {
		// Interstital ad
		adRequestInterstital5 = new AdRequest.Builder().build();
		mInterstitialAd5.loadAd(adRequestInterstital5);
		mInterstitialAd5.show();
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogBox));
		builder.setMessage(" Exit Player List ?")

				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// showInterstitial();


						finish();
					}

				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//  showInterstitial();
						dialog.cancel();
					}

				});
		android.app.AlertDialog alert = builder.create();

		//  AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogBox));
		alert.setTitle("Exit ?");
		alert.show();

		//moveTaskToBack(true);
	}

}
