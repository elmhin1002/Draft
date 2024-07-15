package com.example.quizmate.quiz.question;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmate.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText etQuestion, etOption1, etOption2, etOption3, etOption4, etCorrectAnswer;
    private Button btnAddQuestion;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_question);

        etQuestion = findViewById(R.id.et_dialog_question);
        etOption1 = findViewById(R.id.et_dialog_option1);
        etOption2 = findViewById(R.id.et_dialog_option2);
        etOption3 = findViewById(R.id.et_dialog_option3);
        etOption4 = findViewById(R.id.et_dialog_option4);
        etCorrectAnswer = findViewById(R.id.et_dialog_correct_answer);
        btnAddQuestion = findViewById(R.id.btn_dialog_add_question);

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
            }
        });
    }

    private void addQuestion() {
        String question = etQuestion.getText().toString().trim();
        String option1 = etOption1.getText().toString().trim();
        String option2 = etOption2.getText().toString().trim();
        String option3 = etOption3.getText().toString().trim();
        String option4 = etOption4.getText().toString().trim();
        String correctAnswer = etCorrectAnswer.getText().toString().trim();

        if (TextUtils.isEmpty(question) || TextUtils.isEmpty(option1) || TextUtils.isEmpty(option2) ||
                TextUtils.isEmpty(option3) || TextUtils.isEmpty(option4) || TextUtils.isEmpty(correctAnswer)) {
            Toast.makeText(AddQuestionActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String questionId = mDatabase.push().getKey();
        if (questionId != null) {
            Question newQuestion = new Question(question, option1, option2, option3, option4, correctAnswer);
            mDatabase.child(questionId).setValue(newQuestion).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddQuestionActivity.this, "Question added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddQuestionActivity.this, "Failed to add question", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}



