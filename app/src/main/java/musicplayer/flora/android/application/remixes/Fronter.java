package musicplayer.flora.android.application.remixes;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Fronter extends Activity {
	Button button , exit , like;

    InterstitialAd mInterstitialAd1;
    InterstitialAd mInterstitialAd2;
    InterstitialAd mInterstitialAd8;

    AdRequest adRequestInterstital1;
    AdRequest adRequestInterstital2;
    AdRequest adRequestInterstital8;

    private AdView mAdView1;
    private AdView mAdView2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fronter);



        // Ads
        mAdView1 = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest);


        mAdView2 = (AdView) findViewById(R.id.adView2);
        mAdView2.loadAd(adRequest);

// Ads Interstitial
        mInterstitialAd1= new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId(getString(R.string.ad_unit_id1));
        adRequestInterstital1 = new AdRequest.Builder().build();
        mInterstitialAd1.loadAd(adRequestInterstital1);

        mInterstitialAd2= new InterstitialAd(this);

        mInterstitialAd8= new InterstitialAd(this);
        mInterstitialAd8.setAdUnitId(getString(R.string.ad_unit_id8));
        adRequestInterstital8 = new AdRequest.Builder().build();
        mInterstitialAd8.loadAd(adRequestInterstital8);


        addListenerOnButton();

	}

	public void addListenerOnButton()
	{
		final Context context =this;
		button =(Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(context ,AndroidBuildingMusicPlayerActivity.class);
				startActivity(intent);
			}
		});


		 exit =(Button) findViewById(R.id.button2);

		 exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// show Interstitial ad on exit



                mInterstitialAd8.loadAd(adRequestInterstital8);
                mInterstitialAd8.show();


                finish();
				System.exit(0);
			}
		});



		like =(Button) findViewById(R.id.button3);

		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

                adRequestInterstital8 = new AdRequest.Builder().build();
                mInterstitialAd8.loadAd(adRequestInterstital8);

				Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				// To count with Play market backstack, After pressing back button,
				// to taken back to our application, we need to add following flags to intent.
				goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
						Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
						Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				try {
					startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
				}
			}
		});

	}

}
