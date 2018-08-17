package app.computer.basic.quiz.craftystudio.computerbasic;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import java.util.ArrayList;

import utils.ClickListener;
import utils.FireBaseHandler;
import utils.ItemListAdapter;
import utils.KeyBoardShortcut;

import com.facebook.ads.*;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.android.gms.ads.*;

public class DiplayListActivity extends AppCompatActivity {

    ListView displayItemListview;
    ItemListAdapter mAdapter;
    ArrayList<String> mArrayList = new ArrayList<>();

    String mainTopicName;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diplay_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mArrayList = getIntent().getExtras().getStringArrayList("ArrayList");
        mainTopicName = getIntent().getExtras().getString("Name");

        try {
            getActionBar().setTitle(mainTopicName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mAdapter = new ItemListAdapter(getApplicationContext(), R.layout.custom_textview, mArrayList);

        displayItemListview = (ListView) findViewById(R.id.display_item_listview);
        displayItemListview.setAdapter(mAdapter);


        mAdapter.setOnItemCLickListener(new ClickListener() {
            @Override
            public void onItemCLickListener(View view, int position) {

                final String subTopicName = mArrayList.get(position);

                Intent intent = new Intent(getApplicationContext(), DisplayTextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("SubTopic", subTopicName);
                bundle.putString("MainTopic", mainTopicName);
                intent.putExtras(bundle);
                startActivity(intent);

                //  Toast.makeText(DiplayListActivity.this, mArrayList.get(position) + " position " + position + subTopicName, Toast.LENGTH_SHORT).show();

            }
        });


        try {
            Answers.getInstance().logCustom(new CustomEvent("Topic list open").putCustomAttribute("Topic name", mainTopicName));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
            final com.google.android.gms.ads.AdView admobView = new com.google.android.gms.ads.AdView(DiplayListActivity.this);
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


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


}
