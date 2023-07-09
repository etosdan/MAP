package hr.vsite.ulaznitestovi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hr.vsite.ulaznitestovi.adapter.TestDelete;
import hr.vsite.ulaznitestovi.adapter.TestSave;
import hr.vsite.ulaznitestovi.models.Question;
import hr.vsite.ulaznitestovi.adapter.QuestionAdapter;
import hr.vsite.ulaznitestovi.models.Test;
import hr.vsite.ulaznitestovi.repository.TestRepository;

public class TestEditActivity extends AppCompatActivity {

    private ImageView editTestImageView;
    private EditText testNameEditText;
    private EditText timerEditText;
    private RecyclerView questionsRecyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;
    private TestRepository testRepository;

    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_edit);

        // Get the test data from intent
        test = getIntent().getParcelableExtra("test");

        // Initialize views
        editTestImageView = findViewById(R.id.editTestImageView);
        testNameEditText = findViewById(R.id.testNameEditText);
        timerEditText = findViewById(R.id.timerEditText);
        questionsRecyclerView = findViewById(R.id.questionsRecyclerView);

        // Set the test data to the views
        testNameEditText.setText(test.getTestName());
        timerEditText.setText(String.valueOf(test.getTestDuration()));

        // Set up RecyclerView for questions
        questionList = test.getQuestions();
        questionAdapter = new QuestionAdapter(questionList);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionsRecyclerView.setAdapter(questionAdapter);

        // Button to add a new question
        Button addQuestionButton = findViewById(R.id.addQuestionButton);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
            }
        });

        // Button to save changes
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        // ImageView to edit the test
        editTestImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTest();
            }
        });
    }

    private void addQuestion() {
        // Open the question edit activity to add a new question
        Intent intent = new Intent(TestEditActivity.this, QuestionEditActivity.class);
        startActivity(intent);
    }

    private void saveChanges() {
        // Get the updated test data from the views
        String testName = testNameEditText.getText().toString();
        int timer = Integer.parseInt(timerEditText.getText().toString());

        // Update the test data
        test.setTestName(testName);
        test.setTestDuration(timer);
        test.setQuestions(questionList);

        // Save the updated test data to the database
        testRepository.saveTest(test,new TestSave() {
            @Override
            public void onTestSaved(String testId) {
                // Test saved successfully
                Toast.makeText(TestEditActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                // Handle additional actions or navigation if needed
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Failed to save the test
                Toast.makeText(TestEditActivity.this, "Failed to save changes: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTest() {
        // Delete the test from the database using TestRepository
        testRepository.deleteTest(test.getTestId(), new TestDelete() {
            @Override
            public void onTestDeleted() {
                // Test deleted successfully
                Toast.makeText(TestEditActivity.this, "Test deleted successfully", Toast.LENGTH_SHORT).show();
                // Finish the activity and return to the previous screen
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Failed to delete the test
                Toast.makeText(TestEditActivity.this, "Failed to delete test: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

