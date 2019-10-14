package com.study.potheraj.triviaquiz;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button startNowButton;
    private CheckBox answer2aCheckbox, answer2bCheckbox, answer2cCheckbox, answer2dCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNowButton = (Button) findViewById(R.id.start_now_button);
        answer2aCheckbox = (CheckBox) findViewById(R.id.answer2a_checkbox);
        answer2bCheckbox = (CheckBox) findViewById(R.id.answer2b_checkbox);
        answer2cCheckbox = (CheckBox) findViewById(R.id.answer2c_checkbox);
        answer2dCheckbox = (CheckBox) findViewById(R.id.answer2d_checkbox);
    }

    /**
     * This method is called when the START NOW button is clicked.
     * <p>
     * When the button is clicked once;
     * the Questions are displayed,
     * And the button is Not clickable again
     * The view focus shifts to Question -1
     */
    public void startQuiz(View view) {
        // Make the button non-tappable
        startNowButton.setClickable(false);

        // Display the questions
        LinearLayout questionsLayout = (LinearLayout) findViewById(R.id.questions_layout);
        questionsLayout.setVisibility(view.VISIBLE);
        resetAnswers(view);

        // To shift the focus of view to Question - 1
        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
        scrollView.smoothScrollTo(0, questionsLayout.getTop());
    }

    /**
     * This method is called when the SUBMIT button is clicked.
     */
    public void submitAnswers(View view) {

        int totalScore = 0;

        // Hide the submit button
        view.setVisibility(view.INVISIBLE);

        // Hide the keyboard
        hideKeyboard(this);

        // Make the START QUIZ button tappable for the user to retry
        startNowButton.setClickable(true);

        // if 'Yes' selected for Q-1, then add 1 point
        RadioButton answer1YesRadioButton = (RadioButton) findViewById(R.id.answer1_yes_radio_button);
        if (answer1YesRadioButton.isChecked()) {
            totalScore += 1;
        }

        // if ONLY 'Romania' and 'Ukraine' are selected for Q-2, then add 1 point
        // No mark for partially correct answer
        if ((answer2aCheckbox.isChecked() && answer2bCheckbox.isChecked()) && (!(answer2cCheckbox).isChecked() && !(answer2dCheckbox.isChecked()))) {
            totalScore += 1;
        }

        // if 'Yellow' selected for Q-3, then add 1 point
        RadioButton answer3RadioButton = (RadioButton) findViewById(R.id.answer3_yellow_radio_button);
        if (answer3RadioButton.isChecked()) {
            totalScore += 1;
        }

        // if 'Revolutions per minute' selected for Q-4, then add 1 point
        RadioButton answer4RadioButton = (RadioButton) findViewById(R.id.answer4c_radio_button);
        if (answer4RadioButton.isChecked()) {
            totalScore += 1;
        }

        // if Q-5 is answered as 'Luxemberg' or 'Mexico' then add 1 point
        EditText answer5EditText = (EditText) findViewById(R.id.answer5_edit_text);
        String answer5 = answer5EditText.getText().toString();
        if ((answer5.equalsIgnoreCase("LUXEMBERG")) | (answer5.equalsIgnoreCase("MEXICO"))) {
            totalScore += 1;
        }

        // Display the Toast message
        Toast.makeText(this, getToastMessage(totalScore), Toast.LENGTH_LONG).show();

    }

    /**
     * This method is to generate the toast message based on the score.
     *
     * @param score the final quiz score
     * @return the message to be displayed to the user as toast
     */
    private String getToastMessage(int score) {
        String toastMessage;
        if (score == 5) {
            toastMessage = "BINGO! You scored a perfect 5.";
        } else if (score >= 3) {
            toastMessage = "Good Job! You scored " + score + " out of 5.";
        } else if (score >= 1) {
            toastMessage = "You scored " + score + " out of 5.";
        } else {
            toastMessage = "Oh No! You scored 0 out of 5.";
        }
        return toastMessage;
    }

    /**
     * This method is used to reset all answers for user to restart the quiz
     */
    private void resetAnswers(View view) {
        RadioGroup answer1RadioGroup = (RadioGroup) findViewById(R.id.answer1_radio_group);
        answer1RadioGroup.clearCheck();

        answer2aCheckbox.setChecked(false);
        answer2bCheckbox.setChecked(false);
        answer2cCheckbox.setChecked(false);
        answer2dCheckbox.setChecked(false);

        RadioGroup answer3RadioGroup = (RadioGroup) findViewById(R.id.answer3_radio_group);
        answer3RadioGroup.clearCheck();
        RadioGroup answer4RadioGroup = (RadioGroup) findViewById(R.id.answer4_radio_group);
        answer4RadioGroup.clearCheck();
        EditText answer5EditText = (EditText) findViewById(R.id.answer5_edit_text);
        answer5EditText.setText("");
        answer5EditText.clearFocus();

        // make the Submit button visible
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setVisibility(view.VISIBLE);

    }

    /**
     * This method is to hide the keyboard.
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
