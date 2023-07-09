package hr.vsite.ulaznitestovi;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import hr.vsite.ulaznitestovi.db.FirestoreDatabase;
import hr.vsite.ulaznitestovi.models.Question;
import hr.vsite.ulaznitestovi.models.Test;

public class UserDashboardActivity extends AppCompatActivity {

    private TextView testHistoryTextView;
    private TextView pendingTestsTextView;
    private TextView percentageCorrectTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        testHistoryTextView = findViewById(R.id.testHistoryTextView);
        pendingTestsTextView = findViewById(R.id.pendingTestsTextView);
        percentageCorrectTextView = findViewById(R.id.percentageCorrectTextView);

        // Retrieve the user's test history and pending tests
        List<Test> testHistory = getTestHistory();
        List<Test> pendingTests = getPendingTests();

        // Display the test history and pending tests
        displayTestHistory(testHistory);
        displayPendingTests(pendingTests);

        // Calculate and display the percentage of correct answers
        double percentageCorrect = getPercentageCorrectAnswers(testHistory);
        percentageCorrectTextView.setText("Percentage of Correct Answers: " + percentageCorrect + "%");
    }

    private void displayPendingTests(List<Test> pendingTests) {
    }

    private List<Test> getTestHistory() {
        // TODO: Retrieve the user's test history from the Firebase database
        // using the current user's ID

        List<Test> testHistory = new ArrayList<>();

        // Replace this with your own implementation to fetch data from Firebase
        // For demonstration purposes, dummy data is used here

        // Assuming the current user's ID is stored in a variable called 'userId'
        String userId = getIntent().getStringExtra("userId");

        // Retrieve the test history for the user with 'userId'
        FirestoreDatabase.getInstance()
                .collection("testHistory")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Test test = documentSnapshot.toObject(Test.class);
                            testHistory.add(test);
                        }

                        // Display the test history
                        displayTestHistory(testHistory);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure case
                    }
                });

        return testHistory;
    }

    private void displayTestHistory(List<Test> testHistory) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Test test : testHistory) {
            stringBuilder.append(test.getTestName())
                    .append(" - ")
                    .append(test.isPassed() ? "Passed" : "Failed")
                    .append("\n");
        }
        testHistoryTextView.setText(stringBuilder.toString());
    }

    private List<Test> getPendingTests() {
        // TODO: Retrieve the user's pending tests from the database or any other data source
        // Return a list of Test objects representing the pending tests
        // Replace this with your own implementation

        // Sample pending tests (dummy data for demonstration)

        List<Test> pendingTests = null;
        return pendingTests;
    }

    private double getPercentageCorrectAnswers(List<Test> testHistory) {
        int totalQuestions = 0;
        int totalCorrectAnswers = 0;

        for (Test test : testHistory) {
            List<Question> questions = test.getQuestions();
            int testQuestions = questions.size();
            totalQuestions += testQuestions;

            int correctAnswers = 0;
            for (Question question : questions) {
                if (question.isAnswerCorrect("")) {
                    correctAnswers++;
                }
            }
            totalCorrectAnswers += correctAnswers;
        }

        return (double) totalCorrectAnswers / totalQuestions * 100;
    }
}
