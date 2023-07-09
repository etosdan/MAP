package hr.vsite.ulaznitestovi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.adapter.TestSave;
import hr.vsite.ulaznitestovi.models.Question;
import hr.vsite.ulaznitestovi.models.Test;
import hr.vsite.ulaznitestovi.repository.TestRepository;

public class CreateTestActivity extends AppCompatActivity {

    private EditText testTitleEditText;
    private Spinner universityGroupSpinner;
    private EditText timerEditText;
    private Button addQuestionButton;
    private Button createTestButton;

    private List<Question> questionList;
    private ArrayAdapter<String> universityGroupAdapter;
    private LinearLayout questionContainer;
    private TestRepository testRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        testTitleEditText = findViewById(R.id.testTitleEditText);
        universityGroupSpinner = findViewById(R.id.universityGroupSpinner);
        timerEditText = findViewById(R.id.timerEditText);
        addQuestionButton = findViewById(R.id.addQuestionButton);
        createTestButton = findViewById(R.id.createTestButton);
        questionContainer = findViewById(R.id.questionContainer);

        questionList = new ArrayList<>();
        universityGroupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        universityGroupSpinner.setAdapter(universityGroupAdapter);

        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddQuestionDialog();
            }
        });

        createTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testTitle = testTitleEditText.getText().toString();
                String universityGroup = universityGroupSpinner.getSelectedItem().toString();
                String timer = timerEditText.getText().toString();

                if (testTitle.isEmpty() || universityGroup.isEmpty() || timer.isEmpty() || questionList.isEmpty()) {
                    Toast.makeText(CreateTestActivity.this, "Please fill in all fields and add questions", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveTestDataToDatabase(testTitle, universityGroup, timer, questionList);

                Toast.makeText(CreateTestActivity.this, "Test created successfully", Toast.LENGTH_SHORT).show();

                clearForm();
                finish();
            }
        });
    }

    private void openAddQuestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_question, null);
        builder.setView(dialogView);
        builder.setTitle("Add Question");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText questionTextEditText = dialogView.findViewById(R.id.questionTextEditText);
                EditText option1EditText = dialogView.findViewById(R.id.option1EditText);
                EditText option2EditText = dialogView.findViewById(R.id.option2EditText);
                EditText option3EditText = dialogView.findViewById(R.id.option3EditText);
                EditText option4EditText = dialogView.findViewById(R.id.option4EditText);
                CheckBox option1CheckBox = dialogView.findViewById(R.id.option1CheckBox);
                CheckBox option2CheckBox = dialogView.findViewById(R.id.option2CheckBox);
                CheckBox option3CheckBox = dialogView.findViewById(R.id.option3CheckBox);
                CheckBox option4CheckBox = dialogView.findViewById(R.id.option4CheckBox);

                String questionText = questionTextEditText.getText().toString();
                String option1 = option1EditText.getText().toString();
                String option2 = option2EditText.getText().toString();
                String option3 = option3EditText.getText().toString();
                String option4 = option4EditText.getText().toString();
                List<String> options = new ArrayList<>();
                options.add(option1);
                options.add(option2);
                options.add(option3);
                options.add(option4);
                List<Integer> correctAnswers = new ArrayList<>();
                if (option1CheckBox.isChecked()) {
                    correctAnswers.add(1);
                }
                if (option2CheckBox.isChecked()) {
                    correctAnswers.add(2);
                }
                if (option3CheckBox.isChecked()) {
                    correctAnswers.add(3);
                }
                if (option4CheckBox.isChecked()) {
                    correctAnswers.add(4);
                }

                Question question = new Question(questionText, options, correctAnswers);
                questionList.add(question);

                // Update the UI to display the added question
                displayQuestion(question);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void displayQuestion(Question question) {
        View questionView = getLayoutInflater().inflate(R.layout.item_question, null);
        TextView questionTextView = questionView.findViewById(R.id.questionTextView);
        CheckBox option1CheckBox = questionView.findViewById(R.id.option1CheckBox);
        CheckBox option2CheckBox = questionView.findViewById(R.id.option2CheckBox);
        CheckBox option3CheckBox = questionView.findViewById(R.id.option3CheckBox);
        CheckBox option4CheckBox = questionView.findViewById(R.id.option4CheckBox);

        questionTextView.setText(question.getQuestionText());
        List<String> options = question.getOptions();
        option1CheckBox.setText(options.get(0));
        option2CheckBox.setText(options.get(1));
        option3CheckBox.setText(options.get(2));
        option4CheckBox.setText(options.get(3));

        questionContainer.addView(questionView);
    }
    private void saveTestDataToDatabase(String testTitle, String universityGroup, String timer, List<Question> questionList) {
        String authorId = getIntent().getStringExtra("userId");

        Test test = new Test(testTitle, Integer.parseInt(timer), questionList, authorId); // Pass null for authorId for now
    testRepository.saveTest(test,new TestSave() {
        @Override
        public void onTestSaved(String testId) {
            Toast.makeText(CreateTestActivity.this, "Test created successfully", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onFailure(String errorMessage) {
            Toast.makeText(CreateTestActivity.this, "Failed to create test: " + errorMessage, Toast.LENGTH_SHORT).show();

        }
    });
    }
    private void clearForm() {
        testTitleEditText.getText().clear();
        universityGroupSpinner.setSelection(0);
        timerEditText.getText().clear();
        questionList.clear();
        questionContainer.removeAllViews();
    }
}
