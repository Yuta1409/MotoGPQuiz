package fr.delcey.topquiz.controleur;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import fr.delcey.topquiz.R;
import fr.delcey.topquiz.modele.Users;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private EditText mEditText;
    private Button mButton;
    private Users mUsers;
    private static final int GAME_ACTIVITY_RESQUEST_CODE = 42;
    public static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    public static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_RESQUEST_CODE == requestCode && RESULT_OK == resultCode)
        {
            //fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
        }
    }
    private static final String Tag_Main = "Tag_Main";

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(Tag_Main, "onRestart() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag_Main, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag_Main, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag_Main, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag_Main, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag_Main, "onDestroy() called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        mEditText = findViewById(R.id.edit_text);
        mButton = findViewById(R.id.button);
        mButton.setEnabled(false);

        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO, null);

        mEditText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mButton.setEnabled(!editable.toString().isEmpty());
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mEditText.getText().toString())
                        .apply();

                Intent intentGameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(intentGameActivity, GAME_ACTIVITY_RESQUEST_CODE);

            }
        });

        }
    }