package hr.vsite.ulaznitestovi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.models.Question;

public class QuestionEditActivity extends AppCompatActivity {

    private EditText questionEditText;
    private RecyclerView optionsRecyclerView;
    private OptionAdapter optionAdapter;
    private Button saveButton;

    private Question question;
    private List<String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item2_test_create);

        questionEditText = findViewById(R.id.questionEditText);
        optionsRecyclerView = findViewById(R.id.optionsRecyclerView);
        saveButton = findViewById(R.id.saveButton);

        // Retrieve the question and options from the intent
        question = getIntent().getParcelableExtra("question");
        options = new ArrayList<>(question.getOptions());

        // Set the question text in the EditText
        questionEditText.setText(question.getQuestionText());

        // Set up the RecyclerView for options
        optionAdapter = new OptionAdapter(options);
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        optionsRecyclerView.setAdapter(optionAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });
    }

    private void saveQuestion() {
        String questionText = questionEditText.getText().toString();

        // Update the question object with the new question text and options
        question.setQuestionText(questionText);
        question.setOptions(options);

        // TODO: Save the updated question to the database or perform any necessary operations

        // Finish the activity and return to the previous screen
        finish();
    }

    private class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

        private List<String> options;

        public OptionAdapter(List<String> options) {
            this.options = options;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String option = options.get(position);
            holder.optionEditText.setText(option);

            holder.optionEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        options.set(adapterPosition, s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        @Override
        public int getItemCount() {
            return options.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            EditText optionEditText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                optionEditText = itemView.findViewById(R.id.optionEditText);
            }
        }
    }
}
