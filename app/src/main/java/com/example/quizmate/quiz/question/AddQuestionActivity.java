package com.example.quizmate.quiz.question;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmate.R;

public class AddQuestionActivity extends AppCompatActivity {

    private Button btnShowPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
    }
}
