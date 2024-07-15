package com.example.quizmate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmate.R;
import com.example.quizmate.quiz.question.AddQuestionActivity;
import com.example.quizmate.quiz.question.EditQuestionActivity;
import com.example.quizmate.quiz.question.Question;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;
    private Context context;

    public QuestionAdapter(List<Question> questionList, Context context) {
        this.questionList = questionList;
        this.context = context;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestion.setText(question.question);
        holder.tvOption1.setText(question.option1);
        holder.tvOption2.setText(question.option2);
        holder.tvOption3.setText(question.option3);
        holder.tvOption4.setText(question.option4);
        holder.tvCorrectAnswer.setText(question.correctAnswer);

        holder.btnEditQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditQuestionActivity.class);
            intent.putExtra("questionId", question.getQuestionId());
            intent.putExtra("question", question.question);
            intent.putExtra("option1", question.option1);
            intent.putExtra("option2", question.option2);
            intent.putExtra("option3", question.option3);
            intent.putExtra("option4", question.option4);
            intent.putExtra("correctAnswer", question.correctAnswer);
            context.startActivity(intent);
        });

        holder.btnDeleteQuestion.setOnClickListener(v -> {
            deleteQuestion(question);
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    private void deleteQuestion(Question question) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("questions");
        databaseReference.child(question.getQuestionId()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Question deleted successfully", Toast.LENGTH_SHORT).show();
                questionList.remove(question);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Failed to delete question", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvOption1, tvOption2, tvOption3, tvOption4, tvCorrectAnswer;
        Button btnEditQuestion, btnDeleteQuestion;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvOption1 = itemView.findViewById(R.id.tvOption1);
            tvOption2 = itemView.findViewById(R.id.tvOption2);
            tvOption3 = itemView.findViewById(R.id.tvOption3);
            tvOption4 = itemView.findViewById(R.id.tvOption4);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
            btnEditQuestion = itemView.findViewById(R.id.btnEditQuestion);
            btnDeleteQuestion = itemView.findViewById(R.id.btnDeleteQuestion);
        }
    }
}
