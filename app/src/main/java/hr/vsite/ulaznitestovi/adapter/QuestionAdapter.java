package hr.vsite.ulaznitestovi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.models.Question;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final List<Question> questionList;
    private QuestionAdapterListener listener;

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
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
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void setQuestionAdapterListener(QuestionAdapterListener listener) {
        this.listener = listener;
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        private final TextView questionText;
        private final ImageView editQuestionIcon;
        private final ImageView deleteQuestionIcon;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionTextView);
            editQuestionIcon = itemView.findViewById(R.id.editQuestionImageView);
            deleteQuestionIcon = itemView.findViewById(R.id.deleteQuestionImageView);
        }

        public void bind(Question question) {
            questionText.setText(question.getQuestionText());

            // Set click listeners for edit and delete icons
            editQuestionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onQuestionEditClick(position);
                        }
                    }
                }
            });

            deleteQuestionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onQuestionDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface QuestionAdapterListener {
        void onQuestionEditClick(int position);
        void onQuestionDeleteClick(int position);
    }
}
