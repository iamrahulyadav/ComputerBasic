package app.computer.basic.quiz.craftystudio.computerbasic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.facebook.ads.AdListener;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.ArrayList;


import utils.AppRater;
import utils.FireBaseHandler;
import utils.KeyBoardShortcut;

import com.facebook.ads.*;


public class DisplayTextActivity extends AppCompatActivity {


    String mSubTopic, mMainTopic;
    WebView webView;

    ProgressDialog progressDialog;

    private NativeAd nativeAd_above, nativeAd_below;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onShareClick();
            }
        });


        mMainTopic = getIntent().getExtras().getString("MainTopic");
        mSubTopic = getIntent().getExtras().getString("SubTopic");

        toolbar.setTitle(mSubTopic);
        setSupportActionBar(toolbar);

        showDialog();
        //download data from database
        downloadFullData(mMainTopic, mSubTopic);

        try {
            Answers.getInstance().logContentView(new ContentViewEvent().putContentName(mSubTopic + " - " + mMainTopic).putContentType(mMainTopic));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        AppRater.app_launched(this);

        //above article native ad 1
        initializeTopNativeAd();

        //below article native ad 2
        initializeBottomNativeAd();
    }

    public void initializeTopAdmobAds() {


        try {
            final AdView admobView = new AdView(DisplayTextActivity.this);
            admobView.setAdSize(AdSize.SMART_BANNER);
            admobView.setAdUnitId("ca-app-pub-8455191357100024/5825604225");

            AdRequest adRequest = new AdRequest.Builder().build();
            admobView.loadAd(adRequest);

            admobView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

            admobView.setAdListener(new com.google.android.gms.ads.AdListener() {

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    LinearLayout admobContainer1 = findViewById(R.id.displayText_topNative_adcontainer);
                    admobContainer1.setVisibility(View.VISIBLE);
                    admobContainer1.removeAllViews();
                    admobContainer1.addView(admobView);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void initializeBottomAdmobAds() {


        try {
            final AdView admobView = new AdView(DisplayTextActivity.this);
            admobView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            admobView.setAdUnitId("ca-app-pub-8455191357100024/6321972835");

            AdRequest adRequest = new AdRequest.Builder().build();
            admobView.loadAd(adRequest);

            admobView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

            admobView.setAdListener(new com.google.android.gms.ads.AdListener() {

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    LinearLayout admobContainer1 = findViewById(R.id.displayText_bottomNative_adcontainer);
                    admobContainer1.setVisibility(View.VISIBLE);
                    admobContainer1.removeAllViews();
                    admobContainer1.addView(admobView);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initializeBottomNativeAd() {

        nativeAd_below = new NativeAd(this, "1359885114112144_1362006790566643");

        nativeAd_below.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

                initializeBottomAdmobAds();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(DisplayTextActivity.this, nativeAd_below, NativeAdView.Type.HEIGHT_300);
                LinearLayout nativeAdContainer = (LinearLayout) findViewById(R.id.displayText_bottomNative_adcontainer);
                // Add the Native Ad View to your ad container
                nativeAdContainer.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeAd_below.loadAd();

    }

    public void initializeTopNativeAd() {
        //above article native ad 1
        nativeAd_above = new NativeAd(this, "1359885114112144_1362002810567041");

        nativeAd_above.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {

                initializeTopAdmobAds();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(DisplayTextActivity.this, nativeAd_above, NativeAdView.Type.HEIGHT_120);
                LinearLayout nativeAdContainer = (LinearLayout) findViewById(R.id.displayText_topNative_adcontainer);
                // Add the Native Ad View to your ad container
                nativeAdContainer.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        nativeAd_above.loadAd();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void downloadFullData(String mainTopic, String subTopic) {
        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadItemData(mainTopic, subTopic, new FireBaseHandler.OnDatalistener() {
            @Override
            public void onDataDownLoad(String itemData, boolean isSuccessful) {


                if (isSuccessful) {
                    if (itemData != null) {
                        webView = (WebView) findViewById(R.id.displayText_Main_Webview);
                        webView.loadDataWithBaseURL("", itemData, "text/html", "UTF-8", "");

                    } else {
                        webView.loadDataWithBaseURL("", "Data Cannot be loaded", "text/html", "UTF-8", "");

                    }
                }
                hideDialog();
            }

            @Override
            public void onShortkeyDownload(ArrayList<KeyBoardShortcut> keyBoardShortcut, boolean isSuccessful) {

            }

            @Override
            public void onDataUpload(boolean isSuccessful) {

            }
        });
    }

    private void onShareClick() {
        showDialog();
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://play.google.com/store/apps/details?id=app.computer.basic.quiz.craftystudio.computerbasic"))
                .setDynamicLinkDomain("f5kn7.app.goo.gl")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("app.computer.basic.quiz.craftystudio.computerbasic")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Learn Computer Quickly")
                                .setDescription("Download the App Now")
                                .build())
                .setGoogleAnalyticsParameters(
                        new DynamicLink.GoogleAnalyticsParameters.Builder()
                                .setSource("share")
                                .setMedium("social")
                                .setCampaign("example-promo")
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            Uri shortLink = task.getResult().getShortLink();

                            openShareDialog(shortLink);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }


    private void openShareDialog(Uri shortUrl) {

        try {

            Answers.getInstance().logCustom(new CustomEvent("Share link created").putCustomAttribute("Topic", mMainTopic).putCustomAttribute("sub topic", mSubTopic));

        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");

        //sharingIntent.putExtra(Intent.EXTRA_STREAM, newsMetaInfo.getNewsImageLocalPath());

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Learn Computer on the Go! " + shortUrl);
        startActivity(Intent.createChooser(sharingIntent, "Share Basic Computer App via"));
        hideDialog();

        try {
            //  Answers.getInstance().logCustom(new CustomEvent("Share question").putCustomAttribute("question",questions.getQuestionName()).putCustomAttribute("question topic",questions.getQuestionTopicName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    void showDialog() {
        progressDialog = new ProgressDialog(DisplayTextActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    void hideDialog() {
        progressDialog.hide();
    }
}
