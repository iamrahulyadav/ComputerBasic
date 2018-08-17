package app.computer.basic.quiz.craftystudio.computerbasic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

import utils.ClickListener;
import utils.FireBaseHandler;
import utils.ItemListAdapter;
import utils.Questions;

public class ComputerQuizTopicActivity extends AppCompatActivity {

    ArrayList<String> mArraylist = new ArrayList<>();

    ItemListAdapter adapter;
    ListView computerQuizTopicListview;

    FireBaseHandler fireBaseHandler;
    ProgressDialog progressDialog;


    private AdView adView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_quiz_topic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // upload topic

                FireBaseHandler fireBaseHandler =new FireBaseHandler();
                fireBaseHandler.uploadComputerTopicName("Hey", new FireBaseHandler.OnTopiclistener() {
                    @Override
                    public void onTopicDownLoad(String topic, boolean isSuccessful) {

                    }

                    @Override
                    public void onTopicListDownLoad(ArrayList<String> topicList, boolean isSuccessful) {

                    }

                    @Override
                    public void onTopicUpload(boolean isSuccessful) {

                    }
                });


            }
        });

        mArraylist = new ArrayList<>();
        computerQuizTopicListview = (ListView) findViewById(R.id.computerTopicActivity_topic_listview);

        fireBaseHandler = new FireBaseHandler();

        adapter = new ItemListAdapter(ComputerQuizTopicActivity.this, R.layout.custom_quiz_textview, mArraylist);
        adapter.setOnItemCLickListener(new ClickListener() {
            @Override
            public void onItemCLickListener(View view, int position) {
                String topic = (String) mArraylist.get(position);
                openComputerQuizActivity(topic);
            }
        });

        computerQuizTopicListview.setAdapter(adapter);
        downloadComputerTopicList();


        initializeFanBanner();


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void initializeFanBanner(){

        //Display banner add
        // Instantiate an AdView view
        adView = new AdView(this, "1359885114112144_1359886577445331", AdSize.BANNER_HEIGHT_50);

        LinearLayout adcontainer = (LinearLayout) findViewById(R.id.display_list_banner_container);
        adcontainer.addView(adView);

        // Request an ad
        adView.loadAd();

        adView.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                initializeTopAdmobAds();

            }

            @Override
            public void onAdLoaded(Ad ad) {

                //Toast.makeText(DiplayListActivity.this, "Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

    }


    public void initializeTopAdmobAds() {


        try {
            final com.google.android.gms.ads.AdView admobView = new com.google.android.gms.ads.AdView(ComputerQuizTopicActivity.this);
            admobView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
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

                    LinearLayout admobContainer1 = findViewById(R.id.display_list_banner_container);
                    admobContainer1.setVisibility(View.VISIBLE);
                    admobContainer1.removeAllViews();
                    admobContainer1.addView(admobView);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void downloadComputerTopicList() {

        showDialog("Loading...");
        fireBaseHandler.downloadComputerTopicList(30, new FireBaseHandler.OnTopiclistener() {
            @Override
            public void onTopicDownLoad(String topic, boolean isSuccessful) {

            }

            @Override
            public void onTopicListDownLoad(ArrayList<String> topicList, boolean isSuccessful) {
                if (isSuccessful) {
                    mArraylist.clear();

//                   Add every downloaded topic in list name - mArraylist
                    for (String name : topicList) {
                        mArraylist.add(name);
                    }
//                    set adapter for Item to CLick
                    computerQuizTopicListview.post(new Runnable() {
                        public void run() {
                            computerQuizTopicListview.setAdapter(adapter);
                        }
                    });
                }
                hideDialog();
            }

            @Override
            public void onTopicUpload(boolean isSuccessful) {

            }
        });

    }

    private void openComputerQuizActivity(String topic) {

        Intent intent = new Intent(ComputerQuizTopicActivity.this, ComputerQuizActivity.class);
        intent.putExtra("Topic", topic);
        startActivity(intent);
    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(ComputerQuizTopicActivity.this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        try {
            progressDialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
