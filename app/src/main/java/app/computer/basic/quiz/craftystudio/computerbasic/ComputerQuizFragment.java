package app.computer.basic.quiz.craftystudio.computerbasic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import utils.Questions;

/**
 * Created by Aisha on 7/6/2018.
 */

public class ComputerQuizFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    static Context computerQuizActivity;

    Questions questions;

    TextView optionA;
    TextView optionB;
    TextView optionC;
    TextView optionD, questionExplaination;

    CardView optionACardView;
    CardView optionBCardView;
    CardView optionCCardView;
    CardView optionDCardView;

    CardView explainationDisplayCardview;

    public static ComputerQuizFragment newInstance(Questions questions, Context context, boolean randomTestOn) {

        computerQuizActivity = context;
        ComputerQuizFragment fragment = new ComputerQuizFragment();
        Bundle args = new Bundle();
        args.putSerializable("Question", questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.questions = (Questions) getArguments().getSerializable("Question");

           /* Answers.getInstance().logContentView(new ContentViewEvent()
                    .putContentName(story.getStoryTitle())
                    .putContentId(story.getStoryID())
            );
            */

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computer_quiz, container, false);
        //initializeView

        TextView questionName = (TextView) view.findViewById(R.id.fragmentComputerQuiz_QuestionName_Textview);
        optionA = (TextView) view.findViewById(R.id.fragmentComputerQuiz_optionA_Textview);
        optionB = (TextView) view.findViewById(R.id.fragmentComputerQuiz_optionB_Textview);
        optionC = (TextView) view.findViewById(R.id.fragmentComputerQuiz_optionC_Textview);
        optionD = (TextView) view.findViewById(R.id.fragmentComputerQuiz_optionD_Textview);
        questionExplaination=(TextView) view.findViewById(R.id.question_explaination_textview);

        optionACardView = (CardView) view.findViewById(R.id.fragmentComputerQuiz_optionA_Cardview);
        optionBCardView = (CardView) view.findViewById(R.id.fragmentComputerQuiz_optionB_Cardview);
        optionCCardView = (CardView) view.findViewById(R.id.fragmentComputerQuiz_optionC_Cardview);
        optionDCardView = (CardView) view.findViewById(R.id.fragmentComputerQuiz_optionD_Cardview);


        questionName.setText("Q. " + questions.getQuestionName());
        optionA.setText(questions.getOptionA());
        optionB.setText(questions.getOptionB());
        optionC.setText(questions.getOptionC());
        optionD.setText(questions.getOptionD());
        questionExplaination.setText(questions.getQuestionExplaination());


        optionACardView.setOnClickListener(this);
        optionBCardView.setOnClickListener(this);
        optionCCardView.setOnClickListener(this);
        optionDCardView.setOnClickListener(this);


        questionExplaination = (TextView) view.findViewById(R.id.question_explaination_textview);
        explainationDisplayCardview = (CardView) view.findViewById(R.id.question_explaination_cardview);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

//            User selects option A
            case R.id.fragmentComputerQuiz_optionA_Cardview:
                if (questions.getOptionA().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(computerQuizActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionACardView.setBackgroundResource(R.drawable.mygreenbutton);
                } else {
                    optionACardView.setBackgroundResource(R.drawable.myredbutton);
                    getRightAnswer();
                }
                break;

//           user selects option B
            case R.id.fragmentComputerQuiz_optionB_Cardview:
               if (questions.getOptionB().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(computerQuizActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionBCardView.setBackgroundResource(R.drawable.mygreenbutton);
               } else {
                    optionBCardView.setBackgroundResource(R.drawable.myredbutton);
                    getRightAnswer();
               }
                break;

//          user selects Option C
            case R.id.fragmentComputerQuiz_optionC_Cardview:
               if (questions.getOptionC().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(computerQuizActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionCCardView.setBackgroundResource(R.drawable.mygreenbutton);
               } else {
                    optionCCardView.setBackgroundResource(R.drawable.myredbutton);
                    getRightAnswer();
               }
                break;

//           user selects option D
            case R.id.fragmentComputerQuiz_optionD_Cardview:
               if (questions.getOptionD().equalsIgnoreCase(questions.getCorrectAnswer())) {
                    Toast.makeText(computerQuizActivity, "Right Answer", Toast.LENGTH_SHORT).show();
                    optionDCardView.setBackgroundResource(R.drawable.mygreenbutton);
               } else {
                    optionDCardView.setBackgroundResource(R.drawable.myredbutton);
                    getRightAnswer();
                }
                break;

        }

        explainationDisplayCardview.setVisibility(View.VISIBLE);


    }

    private void getRightAnswer() {
        String correctAnswer = questions.getCorrectAnswer().trim();

//        Set Green Color to the Correct Answer
//        matches Correct Answer with Every Options
        if (questions.getOptionA().trim().equalsIgnoreCase(correctAnswer)) {
            optionACardView.setBackgroundResource(R.drawable.mygreenbutton);
        } else if (questions.getOptionB().trim().equalsIgnoreCase(correctAnswer)) {
            optionBCardView.setBackgroundResource(R.drawable.mygreenbutton);
        } else if (questions.getOptionC().trim().equalsIgnoreCase(correctAnswer)) {
            optionCCardView.setBackgroundResource(R.drawable.mygreenbutton);
        } else if (questions.getOptionD().trim().equalsIgnoreCase(correctAnswer)) {
            optionDCardView.setBackgroundResource(R.drawable.mygreenbutton);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
