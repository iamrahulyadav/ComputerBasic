package app.computer.basic.quiz.craftystudio.computerbasic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

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

                Questions questions = new Questions();
                questions.setQuestionTopicName("Basic");
                questions.setOptionA("ab");
                questions.setOptionB("bc");
                questions.setOptionC("cd");
                questions.setOptionD("Basic");
                questions.setCorrectAnswer("Basic");
                questions.setQuestionExplaination("hello there");
                questions.setQuestionName("what ar hugjb dfb?");

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
                        Toast.makeText(ComputerQuizTopicActivity.this, "QUestion Uploaded", Toast.LENGTH_SHORT).show();
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

    }

    public void downloadComputerTopicList() {

        showDialog("Downloading..Please Wait..");
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
