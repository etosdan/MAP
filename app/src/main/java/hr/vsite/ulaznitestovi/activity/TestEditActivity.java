package hr.vsite.ulaznitestovi.activity;

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

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.adapter.QuestionAdapter;
import hr.vsite.ulaznitestovi.models.TestDelete;
import hr.vsite.ulaznitestovi.models.TestFetch;
import hr.vsite.ulaznitestovi.models.TestSave;
import hr.vsite.ulaznitestovi.models.Question;
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
    private String testId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_edit);

        // Get the test data from intent
        testId = getIntent().getStringExtra("testId");
        fetchTest();

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
        // Initialize the test repository
        testRepository = new TestRepository();

        // Fetch the test data from the database based on the test ID
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

        // ImageView to delete the test
        editTestImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTest();
            }
        });

        // Initialize the TestRepository
        testRepository = new TestRepository();
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
        testRepository.saveTest(test, new TestSave() {
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

    private void fetchTest() {
        // Fetch the test data from the database based on the test ID
        testRepository.getTestById(testId, new TestFetch() {
            @Override
            public void onTestFetched(Test fetchedTest) {
                test = fetchedTest;
                if (test != null) {
                    // Set the test data to the views
                    testNameEditText.setText(test.getTestName());
                    timerEditText.setText(String.valueOf(test.getTestDuration()));

                    // Update the question list and notify the adapter
                    questionList.clear();
                    questionList.addAll(test.getQuestions());
                    questionAdapter.notifyDataSetChanged();
                } else {
                    // Handle the case when the test is not found
                    Toast.makeText(TestEditActivity.this, "Test not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle the failure to fetch the test
                Toast.makeText(TestEditActivity.this, "Failed to fetch test: " + errorMessage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
