package app.computer.basic.quiz.craftystudio.computerbasic;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.KeyBoardShortcut;

public class DisplayTextActivity extends AppCompatActivity {


    String mSubTopic, mMainTopic;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);
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


        mMainTopic = getIntent().getExtras().getString("MainTopic");
        mSubTopic = getIntent().getExtras().getString("SubTopic");

        //download data from database
        downloadFullData(mMainTopic, mSubTopic);


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

                    }else {
                        webView.loadDataWithBaseURL("", "Data Cannot be loaded", "text/html", "UTF-8", "");

                    }
                }


            }

            @Override
            public void onShortkeyDownload(ArrayList<KeyBoardShortcut> keyBoardShortcut, boolean isSuccessful) {

            }

            @Override
            public void onDataUpload(boolean isSuccessful) {

            }
        });
    }

}
