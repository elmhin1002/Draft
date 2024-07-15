package com.example.quizmate.quiz.question;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmate.R;
import com.example.quizmate.adapter.QuestionAdapter;
import com.example.quizmate.quiz.question.AddQuestionActivity;
import com.example.quizmate.adapter.QuizAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class ViewQuestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewQuestions;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        recyclerViewQuestions = findViewById(R.id.recycler_view);
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(this));
        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(questionList, this);
        recyclerViewQuestions.setAdapter(questionAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("questions");

        fetchQuestions();
    }

    private void fetchQuestions() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    question.setQuestionId(questionSnapshot.getKey()); // Set the question ID for editing and deleting
                    questionList.add(question);
                }
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewQuestionsActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
