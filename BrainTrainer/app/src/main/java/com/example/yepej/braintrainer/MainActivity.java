package com.example.yepej.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    enum Operations
    {
        Addition,
        Subtraction
    }

    Operations activeOP;
    Random numberGenerator = new Random();
    Button answwerButton1;
    Button answwerButton2;
    Button answwerButton3;
    Button answwerButton4;
    Button playButton;
    Button[] answerButtons = new Button[4];
    TextView operationText;
    TextView timerText;
    TextView finalScoreText;
    String correctAnswer = "";
    int problemsAnswered = 0;
    int problemsCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Initializes instance variables
    private void setVariables()
    {
        answwerButton1 = ((Button) findViewById(R.id.answerbutton1));
        answwerButton2 = ((Button) findViewById(R.id.answerbutton2));
        answwerButton3 = ((Button) findViewById(R.id.answerbutton3));
        answwerButton4 = ((Button) findViewById(R.id.answerbutton4));
        playButton = ((Button) findViewById(R.id.playButton));

        answerButtons[0] = answwerButton1;
        answerButtons[1] = answwerButton2;
        answerButtons[2] = answwerButton3;
        answerButtons[3] = answwerButton4;

        operationText = ((TextView) findViewById(R.id.operationText));
        timerText = ((TextView) findViewById(R.id.timerText));
        finalScoreText = ((TextView) findViewById(R.id.finalScoreText));
    }

    //Which challenge did the user click
    public void operatorButtonClick(View control)
    {
        if (control.getTag().equals("add"))
        {
            activeOP = Operations.Addition;
        }
        else if (control.getTag().equals("sub"))
        {
            activeOP = Operations.Subtraction;
        }

        setContentView(R.layout.game);
        setVariables();

        startTimer();

        startGame();
    }

    //Starts timer for the game
    private void startTimer()
    {
        new CountDownTimer(30000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timerText.setText("00:" + Long.toString(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish()
            {
                timerText.setText("00:00");
                finalScoreText.setText("Your final score is " + problemsCorrect + "/" + problemsAnswered);
                disableAllButtons();
                playButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void disableAllButtons()
    {
        for (int i = 0; i < answerButtons.length; i++)
        {
            answerButtons[i].setEnabled(false);
        }
    }

    public void answerButtonClick(View control)
    {

        Button clickedButton = ((Button) control);
        TextView scoreText = ((TextView) findViewById(R.id.scoreText));
        problemsAnswered++;

        if (clickedButton.getText().equals(correctAnswer))
        {
            problemsCorrect++;
        }

        scoreText.setText(Integer.toString(problemsCorrect) + "/" + Integer.toString(problemsAnswered));

        clearAnswerButtons();
        startGame();
    }

    private void clearAnswerButtons()
    {
        for (int i = 0; i < answerButtons.length; i++)
        {
            answerButtons[i].setText("");
        }
    }

    private void startGame()
    {
        int firstNum = numberGenerator.nextInt(99) + 1;
        int secondNum = numberGenerator.nextInt(99) + 1;

        if (activeOP == Operations.Addition)
        {
            operationText.setText( Integer.toString(firstNum) + " + " + Integer.toString(secondNum));
            correctAnswer = Integer.toString(firstNum + secondNum);
        }
        else if (activeOP == Operations.Subtraction)
        {
            if (firstNum < secondNum)
            {
                operationText.setText( Integer.toString(secondNum) + " - " + Integer.toString(firstNum));
                correctAnswer = Integer.toString(secondNum - firstNum);
            }
            else
            {
                operationText.setText( Integer.toString(firstNum) + " - " + Integer.toString(secondNum));
                correctAnswer = Integer.toString(firstNum - secondNum);
            }
        }

        setCorrectAnswer();
        setRandomAnswers();
    }

    private void setCorrectAnswer()
    {
        int buttonToPlace = numberGenerator.nextInt(3);

        answerButtons[buttonToPlace].setText(correctAnswer);
    }

    private void setRandomAnswers()
    {
        for (int i = 0; i < answerButtons.length; i++)
        {
            if (answerButtons[i].getText().equals(""))
            {
                String randomNum = Integer.toString(numberGenerator.nextInt(99) + 1);
                answerButtons[i].setText(randomNum);
            }
        }
    }

    public void restart (View control)
    {
        problemsCorrect = 0;
        problemsAnswered = 0;
        setContentView(R.layout.activity_main);
    }
}
