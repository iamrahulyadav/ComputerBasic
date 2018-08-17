package app.computer.basic.quiz.craftystudio.computerbasic;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.Random;

import utils.FireBaseHandler;
import utils.Questions;
import utils.ZoomOutPageTransformer;

public class ComputerQuizActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    FireBaseHandler fireBaseHandler;
    ProgressDialog progressDialog;
    ArrayList<Questions> mQuestionsList = new ArrayList<>();

    TextView questionNumberDisplayTextview;

    Questions questions;

    String topicName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Generating a random number between 0-1000
                int min = 0;
                int max = 1000;
                Random r = new Random();
                int randomNumber = r.nextInt(max - min + 1) + min;

                //upload question
                Questions questions = new Questions();
                questions.setQuestionTopicName("Basic");
                questions.setOptionA("ab");
                questions.setOptionB("bc");
                questions.setOptionC("cd");
                questions.setOptionD("Basic");
                questions.setCorrectAnswer("Basic");
                questions.setQuestionExplaination("hello there");
                questions.setQuestionName("what ar hugjb dfb?");
                questions.setPusnNotification(false);
                questions.setNotificationText("");
                questions.setRandomNumber(randomNumber);


                FireBaseHandler fireBaseHandler = new FireBaseHandler();
                fireBaseHandler.uploadComputerQuestion(questions, new FireBaseHandler.OnQuestionlistener() {
                    @Override
                    public void onQuestionDownLoad(Questions questions, boolean isSuccessful) {

                    }

                    @Override
                    public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful) {

                    }

                    @Override
                    public void onQuestionUpload(boolean isSuccessful) {
                        Toast.makeText(ComputerQuizActivity.this, "QUestion Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
*/


        questionNumberDisplayTextview = (TextView) findViewById(R.id.computer_quiz_questionNumber_Textview);

//       Get Clicked Topic from List Activity
        topicName = getIntent().getStringExtra("Topic").toString();

//        set toolbar title as CLicked Topic Name
        toolbar.setTitle(topicName);
        setSupportActionBar(toolbar);

//        setting up Pager
        mPager = (ViewPager) findViewById(R.id.computer_quiz_activity_viewpager);
        initializeViewPager();

//        Download QUestions from CLicked Topic Name
        downloadQuestionsByTopic(topicName);


        try {
            Answers.getInstance().logContentView(new ContentViewEvent().putContentName("Quiz" + " - " + topicName).putContentType("Quiz"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void downloadQuestionsByTopic(String topic) {

        showDialog("Downloading Questions..Please Wait..");
        fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadComputerQuestionList(30, topic, new FireBaseHandler.OnQuestionlistener() {
            @Override
            public void onQuestionDownLoad(Questions questions, boolean isSuccessful) {

            }

            @Override
            public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful) {

                if (isSuccessful) {

                    for (Questions questions : questionList) {
                        mQuestionsList.add(questions);
                    }

                    addNativeAds();

                    mPagerAdapter.notifyDataSetChanged();
                }

                hideDialog();
            }

            @Override
            public void onQuestionUpload(boolean isSuccessful) {

            }


        });

    }



    public void addNativeAds() {

        for (int i = 1; i < mQuestionsList.size(); i = i + 3) {
            if (mQuestionsList.get(i).getNativeAd() == null) {

                NativeAd nativeAd = new NativeAd(this, "1359885114112144_1461096457324342");
                nativeAd.setAdListener(new AdListener() {

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        Log.d("TAG", "onError: " + adError.getErrorMessage());

                        try {
                            Answers.getInstance().logCustom(new CustomEvent("Ad failed").putCustomAttribute("message", adError.getErrorMessage()).putCustomAttribute("Placement", "banner"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {

                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }


                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                });

                // Initiate a request to load an ad.
                nativeAd.loadAd();

                mQuestionsList.get(i).setNativeAd(nativeAd);

            }
        }
    }


    private void initializeViewPager() {

        // Instantiate a ViewPager and a PagerAdapter.

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        //change to zoom
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

//                set question number display
                questionNumberDisplayTextview.setText("Question " + (position + 1));

                //checkInterstitialAds();
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

    }

    public void showDialog(String message) {
        progressDialog = new ProgressDialog(ComputerQuizActivity.this);
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


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ComputerQuizFragment.newInstance(mQuestionsList.get(position), ComputerQuizActivity.this, false);
        }

        @Override
        public int getCount() {
            return mQuestionsList.size();
        }

    }


}
