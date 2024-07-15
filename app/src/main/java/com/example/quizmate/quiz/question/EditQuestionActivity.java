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

public class EditQuestionActivity extends AppCompatActivity {

    private EditText etQuestion, etOption1, etOption2, etOption3, etOption4, etCorrectAnswer;
    private Button btnUpdateQuestion;
    private DatabaseReference mDatabase;
    private String questionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        etQuestion = findViewById(R.id.et_question);
        etOption1 = findViewById(R.id.et_option1);
        etOption2 = findViewById(R.id.et_option2);
        etOption3 = findViewById(R.id.et_option3);
        etOption4 = findViewById(R.id.et_option4);
        etCorrectAnswer = findViewById(R.id.et_correct_answer);
        btnUpdateQuestion = findViewById(R.id.btn_update_question);

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");

        // Retrieve question details from intent
        questionId = getIntent().getStringExtra("questionId");
        String question = getIntent().getStringExtra("question");
        String option1 = getIntent().getStringExtra("option1");
        String option2 = getIntent().getStringExtra("option2");
        String option3 = getIntent().getStringExtra("option3");
        String option4 = getIntent().getStringExtra("option4");
        String correctAnswer = getIntent().getStringExtra("correctAnswer");

        // Populate EditText fields with existing question data
        etQuestion.setText(question);
        etOption1.setText(option1);
        etOption2.setText(option2);
        etOption3.setText(option3);
        etOption4.setText(option4);
        etCorrectAnswer.setText(correctAnswer);

        btnUpdateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        String question = etQuestion.getText().toString().trim();
        String option1 = etOption1.getText().toString().trim();
        String option2 = etOption2.getText().toString().trim();
        String option3 = etOption3.getText().toString().trim();
        String option4 = etOption4.getText().toString().trim();
        String correctAnswer = etCorrectAnswer.getText().toString().trim();

        if (TextUtils.isEmpty(question) || TextUtils.isEmpty(option1) || TextUtils.isEmpty(option2) ||
                TextUtils.isEmpty(option3) || TextUtils.isEmpty(option4) || TextUtils.isEmpty(correctAnswer)) {
            Toast.makeText(EditQuestionActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Question updatedQuestion = new Question(question, option1, option2, option3, option4, correctAnswer);
        mDatabase.child(questionId).setValue(updatedQuestion).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditQuestionActivity.this, "Question updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditQuestionActivity.this, "Failed to update question", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

