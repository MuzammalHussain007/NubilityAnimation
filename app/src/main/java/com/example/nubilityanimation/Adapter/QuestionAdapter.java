package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Modal.Quiz_Question;
import com.example.nubilityanimation.R;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionHolder> {
    private ArrayList<Quiz_Question> questionArrayList;
    private Context context;
    private int marks=0;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    public QuestionAdapter()
    {

    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public QuestionAdapter(ArrayList<Quiz_Question> questionArrayList, Context context) {
        this.questionArrayList = questionArrayList;
        this.context = context;
        sharedPreference = context.getSharedPreferences("Preferonces",context.MODE_MULTI_PROCESS);
        editor=sharedPreference.edit();
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_question_paper,null);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionHolder holder, int position) {
        final Quiz_Question quiz_question = questionArrayList.get(position);
        holder.question_text_view.setText(quiz_question.getQuiz_question());
        holder.Option1.setText(quiz_question.getOption_1());
        holder.Option2.setText(quiz_question.getOption_2());
        holder.Option3.setText(quiz_question.getOption_3());

        holder.Option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.Option1.getText().equals(quiz_question.getCorrect_Answer()))
                {


                    marks++;
                    editor.putString("mark", String.valueOf(marks)).commit();
                    Toast.makeText(context,"Answer is correct",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(context,"Answer is wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.Option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.Option2.getText().equals(quiz_question.getCorrect_Answer()))
                {
                    Toast.makeText(context,"Answer is correct",Toast.LENGTH_SHORT).show();
                    marks++;
                    editor.putString("mark", String.valueOf(marks)).commit();

                }
                else
                {
                    Toast.makeText(context,"Answer is wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.Option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.Option3.getText().equals(quiz_question.getCorrect_Answer()))
                {
                    Toast.makeText(context,"Answer is correct",Toast.LENGTH_SHORT).show();
                    marks++;
                    editor.putString("mark", String.valueOf(marks)).commit();

                }
                else
                {
                    Toast.makeText(context,"Answer is wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    public class QuestionHolder extends RecyclerView.ViewHolder
    {
        private TextView question_text_view;
        private Button Option1,Option2,Option3;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            question_text_view = itemView.findViewById(R.id.custom_question_statement);
            Option1 = itemView.findViewById(R.id.custom_option1);
            Option2 = itemView.findViewById(R.id.custom_option2);
            Option3 = itemView.findViewById(R.id.custom_option3);
        }
    }
}
