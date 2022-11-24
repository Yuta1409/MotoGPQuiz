package fr.delcey.topquiz.modele;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class QuestionBank implements Serializable {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        // Shuffle the question list before storing it
        mQuestionList = questionList;

        Collections.shuffle(mQuestionList);
    }

    public Question getCurrentQuestion(){
        return mQuestionList.get(mNextQuestionIndex);
    }

    public Question getNextQuestion() {
        // Loop over the questions and return a new one at each call
        mNextQuestionIndex++;
        return getCurrentQuestion();
    }
}
