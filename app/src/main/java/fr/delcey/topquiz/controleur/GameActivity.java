package fr.delcey.topquiz.controleur;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.delcey.topquiz.R;
import fr.delcey.topquiz.modele.Question;
import fr.delcey.topquiz.modele.QuestionBank;

import java.util.Arrays;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTextView;
    Button mGameButton1;
    Button mGameButton2;
    Button mGameButton3;
    Button mGameButton4;
    Question mCurrentQuestion;
    private final QuestionBank mQuestionBank = generateQuestions();
    private int mRemainingQuestionCount;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";
    private boolean mEnableTouchEvent;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvent && super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }
    private static final String Tag_Game = "Tag_Game";

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag_Game, "onRestart() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag_Game, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag_Game, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag_Game, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag_Game, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag_Game, "onDestroy() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mTextView = findViewById(R.id.game_activity_textview_question);
        mGameButton1 = findViewById(R.id.game_activity_button_1);
        mGameButton2 = findViewById(R.id.game_activity_button_2);
        mGameButton3 = findViewById(R.id.game_activity_button_3);
        mGameButton4 = findViewById(R.id.game_activity_button_4);

        mGameButton1.setOnClickListener(this);
        mGameButton2.setOnClickListener(this);
        mGameButton3.setOnClickListener(this);
        mGameButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mCurrentQuestion);

        mEnableTouchEvent = true;

        if (savedInstanceState != null){
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
        } else {
            mRemainingQuestionCount = 4;
            mScore = 0;
        }
    }

    private void displayQuestion(final Question question){

        mTextView.setText(question.getQuestion());
        mGameButton1.setText(question.getChoiceList().get(0));
        mGameButton2.setText(question.getChoiceList().get(1));
        mGameButton3.setText(question.getChoiceList().get(2));
        mGameButton4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Quel pilote de Moto GP est 9 fois champion du monde ?",
                Arrays.asList(
                        "Dani Pedrosa",
                        "Valentino Rossi",
                        "Giacomo Agostini",
                        "Fabio Quartararo"
                ),
                1
        );

        Question question2 = new Question(
                "Qui est le détenteur du titre actuellement ?",
                Arrays.asList(
                        "Marc Marquez",
                        "Valentino Rossi",
                        "Jorge Lorenzo",
                        "Francesco Bagnaia"
                ),
                3
        );

        Question question3 = new Question(
                "Quel est le numéro de Valentino Rossi?",
                Arrays.asList(
                        "25",
                        "8",
                        "46",
                        "93"
                ),
                2
        );

        Question question4 = new Question(
                "Qui est pilote chez l'écurie Yamaha?",
                Arrays.asList(
                        "Jorge Lorenzo",
                        "Fabio Quartararo",
                        "Bradley Smith",
                        "Enea Bastianini"
                ),
                1
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3, question4));
    }


    @Override
    public void onClick(View v) {
        int index;

        if (v == mGameButton1) {
            index = 0;
        } else if (v == mGameButton2) {
            index = 1;
        } else if (v == mGameButton3) {
            index = 2;
        } else if (v == mGameButton4) {
            index = 3;
        } else {
            throw new IllegalStateException("Unknown clicked view : " + v);
        }

        if (index == mQuestionBank.getCurrentQuestion().getAnswerIndex()){
            Toast.makeText(this, "Bonne réponse !", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvent = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemainingQuestionCount--;

                if (mRemainingQuestionCount > 0) {
                    mCurrentQuestion = mQuestionBank.getNextQuestion();
                    displayQuestion(mCurrentQuestion);
                }else{
                    endGame();
                }
                mEnableTouchEvent = true;
            }
        }, 2000);
    }

    private void endGame(){
        /*
                ligne 1 : il est nécessaire d'utiliser un objet spécifique pour "construire" la boîte de dialogue. C'est la sous-classe Builder qui s'en charge ;
                ligne 3 : nous définissons le titre de la boîte de dialogue ;
                ligne 4 : nous définissons le texte à afficher dans la boîte de dialogue ;
                ligne 5 : nous définissons le texte du bouton à afficher, et fournissons l'implémentation de l'interface permettant de gérer le clic sur le bouton ;
                ligne 11 : nous demandons à l'instance de Builder de construire la boîte de dialogue avec les paramètres que nous avons prédéfinis ;
                ligne 12 : nous affichons notre belle boîte de dialogue.
            */


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien jouer " + " ! ")
                .setMessage("Votre score est de : " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            /*
                                ligne 1 : nous créons une instance de la la classe Intent ;
                                ligne 2 : nous attachons le score à l'Intent, avec la clé associée BUNDLE_EXTRA_SCORE. Cette clé sera par la suite utilisée par la MainActivity pour récupérer le score ;
                                ligne 3 : nous précisons au système Android que l'activité s'est correctement terminée, et nous lui indiquons en second paramètre notre Intent (qui contient le score) ;
                                ligne 4 : nous terminons l'activité.
                            */
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }
}