package com.ultralegends.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.stream.IntStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA,ansB,ansC,ansD,submitBtn;

    DBHandler DB;

    int score = 0;
    int totalQuestions = QuestionAnswer.questions.length;
    int []array = genArray(totalQuestions);
    int[] mcq = {0,1,2,3};
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        DB = new DBHandler(this);

        totalQuestionsTextView.setText("Total Questions: "+totalQuestions);

        loadNewQuestion(false);

    }

    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswer[array[currentQuestionIndex]]))
            {
                score++;
            }
            currentQuestionIndex++;
            String correctAns = QuestionAnswer.correctAnswer[array[currentQuestionIndex]];
            QuizData quiz = new QuizData(selectedAnswer,correctAns,selectedAnswer.equals(correctAns));

            boolean insRes = DB.insertQuiz(quiz);

            loadNewQuestion(insRes);
//            if(insRes)
//            {
//                Toast.makeText(this,"Answer Saved to DB",Toast.LENGTH_SHORT).show();
//                loadNewQuestion();
//            }
//            else
//            {
//                Toast.makeText(this,"Answer did not save to DB. Check!",Toast.LENGTH_SHORT).show();
//                loadNewQuestion();
//            }
        }
        else{
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
        }
    }
    void loadNewQuestion(boolean insRes) {
        if(insRes)
        {
            Toast.makeText(this,"Answer Saved to DB",Toast.LENGTH_SHORT).show();
        }
        else if(currentQuestionIndex == 0 && !insRes)
        {
            Toast.makeText(this,"Welcome to quiz game",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Answer did not save to DB. Check!",Toast.LENGTH_SHORT).show();
        }
        if(currentQuestionIndex == totalQuestions)
        {
            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.questions[array[currentQuestionIndex]]);

        shuffleArray(mcq);
        Toast.makeText(this, ""+mcq.toString(), Toast.LENGTH_SHORT).show();
        ansA.setText(QuestionAnswer.choices[array[currentQuestionIndex]][mcq[0]]);
        ansB.setText(QuestionAnswer.choices[array[currentQuestionIndex]][mcq[1]]);
        ansC.setText(QuestionAnswer.choices[array[currentQuestionIndex]][mcq[2]]);
        ansD.setText(QuestionAnswer.choices[array[currentQuestionIndex]][mcq[3]]);
    }

    void finishQuiz() {
        String passStatus = "";
        if (score>totalQuestions*0.6)
        {
            passStatus = "Passed";
        }
        else
        {
            passStatus = "Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Correct "+ score + "\nWrong " + (totalQuestions-score))
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();
    }

    public int[] genArray(int n){
        int[] array = new int[n];
        for (int i=0;i<array.length; i++)
        {
            array[i] = i;
        }

        int arrLength = array.length;
        for (int i=0;i<arrLength; i++)
        {
            int s = i+ (int)(Math.random()*(arrLength-i));
            int temp =array[s];
            array[s] = array[i];
            array[i] = temp;
        }
        return array;
    }

    void restartQuiz(){
        score = 0;
        currentQuestionIndex = 0;
        array = genArray(totalQuestions);
        loadNewQuestion(false);
    }

    public void shuffleArray(int[] array)
    {
        int arrLength = array.length;
        for (int i=0;i<arrLength; i++)
        {
            int s = i+ (int)(Math.random()*(arrLength-i));
            int temp =array[s];
            array[s] = array[i];
            array[i] = temp;
        }
    }

}