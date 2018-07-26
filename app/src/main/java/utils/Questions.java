package utils;

import java.io.Serializable;

/**
 * Created by Aisha on 7/6/2018.
 */

public class Questions implements Serializable {

    private String questionName;
    private String correctAnswer;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String questionExplaination;
    private String questionTopicName;
    private String questionUID;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getQuestionExplaination() {
        return questionExplaination;
    }

    public void setQuestionExplaination(String questionExplaination) {
        this.questionExplaination = questionExplaination;
    }

    public String getQuestionTopicName() {
        return questionTopicName;
    }

    public void setQuestionTopicName(String questionTopicName) {
        this.questionTopicName = questionTopicName;
    }

    public String getQuestionUID() {
        return questionUID;
    }

    public void setQuestionUID(String questionUID) {
        this.questionUID = questionUID;
    }
}
