package utils;

import android.hardware.camera2.CaptureResult;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Aisha on 12/24/2017.
 */

public class FireBaseHandler {

    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mFirebaseDatabase;

    public FireBaseHandler() {

        mFirebaseDatabase = FirebaseDatabase.getInstance();

    }

    public void downloadItemData(String TopicName, final String subTopicName, final OnDatalistener onDatalistener) {


        DatabaseReference myRef = mFirebaseDatabase.getReference().child(TopicName + "/" + subTopicName);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String textData = dataSnapshot.getValue(String.class);

                onDatalistener.onDataDownLoad(textData, true);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDatalistener.onDataDownLoad(null, false);
            }
        });


    }

    public void uploadShortKeys(final KeyBoardShortcut keyBoardShortcut, String shortkeyType, final OnDatalistener onDatalistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("ShortKey Data/" + shortkeyType + "/");

        keyBoardShortcut.setShortKeyUID(mDatabaseRef.push().getKey());

        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("ShortKey Data/" + shortkeyType + "/" + keyBoardShortcut.getShortKeyUID());


        mDatabaseRef1.setValue(keyBoardShortcut).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // onDatalistener.onDataDownLoad(, true);
                onDatalistener.onDataUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                onDatalistener.onDataUpload(false);
                onDatalistener.onDataDownLoad(null, false);
            }
        });


    }

    public void downloadKeyList(int limit, String shortKeyType, final OnDatalistener onDatalistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("ShortKey Data/" + shortKeyType + "/");

        Query myref2 = mDatabaseRef.orderByKey().limitToFirst(limit);

        myref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<KeyBoardShortcut> keyArrayList = new ArrayList<KeyBoardShortcut>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    KeyBoardShortcut keyBoardShortcut = snapshot.getValue(KeyBoardShortcut.class);
                    if (keyBoardShortcut != null) {
                        keyBoardShortcut.setShortKeyUID(snapshot.getKey());
                    }
                    keyArrayList.add(keyBoardShortcut);
                }

                // Collections.reverse(questionsArrayList);

                onDatalistener.onShortkeyDownload(keyArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onDatalistener.onShortkeyDownload(null, false);

            }
        });


    }

    public void uploadComputerTopicName(final String topic, final OnTopiclistener onTopiclistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("ComputerQuiz/Topic/");

        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("ComputerQuiz/Topic/" + mDatabaseRef.push().getKey());

        //DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("Topic/");


        mDatabaseRef1.setValue(topic).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onTopiclistener.onTopicDownLoad(topic, true);
                onTopiclistener.onTopicUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Topic", e.getMessage());

                onTopiclistener.onTopicUpload(false);
                onTopiclistener.onTopicDownLoad(null, false);
            }
        });


    }

    public void downloadComputerTopicList(int limit, final OnTopiclistener onTopiclistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("ComputerQuiz/Topic/");

        Query myref2 = mDatabaseRef.orderByKey().limitToLast(limit);

        ValueEventListener valueEventListener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> computerTopicArrayList = new ArrayList<String>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String topic = snapshot.getValue(String.class);
                    if (topic != null) {
                        computerTopicArrayList.add(topic);

                    }
                }
                // Collections.reverse(verbalTopicArrayList);
                onTopiclistener.onTopicListDownLoad(computerTopicArrayList, true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onTopiclistener.onTopicListDownLoad(null, false);

            }
        });


    }

    public void uploadComputerQuestion(final Questions questions, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("ComputerQuiz/Questions/");

        questions.setQuestionUID(mDatabaseRef.push().getKey());

        DatabaseReference mDatabaseRef1 = mFirebaseDatabase.getReference().child("ComputerQuiz/Questions/" + questions.getQuestionUID());


        mDatabaseRef1.setValue(questions).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onQuestionlistener.onQuestionDownLoad(questions, true);
                onQuestionlistener.onQuestionUpload(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Failed to Upload Story", e.getMessage());

                onQuestionlistener.onQuestionUpload(false);
                onQuestionlistener.onQuestionDownLoad(null, false);
            }
        });


    }

    public void downloadComputerQuestionList(int limit, String topicName, final OnQuestionlistener onQuestionlistener) {


        mDatabaseRef = mFirebaseDatabase.getReference().child("ComputerQuiz/Questions/");

        Query myref2 = mDatabaseRef.orderByChild("questionTopicName").equalTo(topicName).limitToLast(limit);

        ValueEventListener valueEventListener = myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Questions> questionsArrayList = new ArrayList<Questions>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Questions questions = snapshot.getValue(Questions.class);
                    if (questions != null) {

                        questions.setQuestionUID(snapshot.getKey());

                    }
                    questionsArrayList.add(questions);
                }

                Collections.reverse(questionsArrayList);

                onQuestionlistener.onQuestionListDownLoad(questionsArrayList, true);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onQuestionlistener.onQuestionListDownLoad(null, false);

            }
        });
    }


    public interface OnTopiclistener {


        public void onTopicDownLoad(String topic, boolean isSuccessful);

        public void onTopicListDownLoad(ArrayList<String> topicList, boolean isSuccessful);


        public void onTopicUpload(boolean isSuccessful);
    }


    public interface OnQuestionlistener {


        public void onQuestionDownLoad(Questions questions, boolean isSuccessful);

        public void onQuestionListDownLoad(ArrayList<Questions> questionList, boolean isSuccessful);


        public void onQuestionUpload(boolean isSuccessful);
    }

    public interface OnDatalistener {


        public void onDataDownLoad(String itemData, boolean isSuccessful);

        public void onShortkeyDownload(ArrayList<KeyBoardShortcut> keyBoardShortcut, boolean isSuccessful);

        public void onDataUpload(boolean isSuccessful);
    }
}
