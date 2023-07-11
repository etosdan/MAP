package hr.vsite.ulaznitestovi.repository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.vsite.ulaznitestovi.adapter.TestDelete;
import hr.vsite.ulaznitestovi.adapter.TestSave;
import hr.vsite.ulaznitestovi.models.Question;
import hr.vsite.ulaznitestovi.models.Test;

public class TestRepository {

    private final FirebaseFirestore firestore;

    public TestRepository() {
        firestore = FirebaseFirestore.getInstance();
    }

    public void saveTest(Test test, TestSave callback) {
        // Create a document reference for the test
        DocumentReference testRef = firestore.collection("tests").document();

        // Create a map of test data
        Map<String, Object> testData = new HashMap<>();
        testData.put("testName", test.getTestName());
        testData.put("testDuration", test.getTestDuration());
        testData.put("questions", test.getQuestions()); // Save the question list

        // Set the test data in the document reference
        testRef.set(testData)
                .addOnSuccessListener(aVoid -> {
                    // Test data saved successfully
                    callback.onTestSaved(testRef.getId());
                })
                .addOnFailureListener(e -> {
                    // Failed to save the test data
                    callback.onFailure(e.getMessage());
                });
    }

    private void saveQuestionsForTest(String testId, List<Question> questions, TestSave callback) {
        // Create a batch write to save the questions and associate them with the test
        WriteBatch batch = firestore.batch();

        // Iterate over the questions and save them
        for (Question question : questions) {
            // Create a document reference for the question
            DocumentReference questionRef = firestore.collection("questions").document();

            // Create a map of question data
            Map<String, Object> questionData = new HashMap<>();
            questionData.put("questionText", question.getQuestionText());
            questionData.put("options", question.getOptions());
            questionData.put("correctAnswerIndices", question.getCorrectAnswerIndices());
            // Add any other question data you want to save

            // Set the question data in the document reference
            batch.set(questionRef, questionData);

            // Associate the question with the test by adding its ID to the test's question list
            batch.update(firestore.collection("tests").document(testId), "questions", FieldValue.arrayUnion(questionRef.getId()));
        }

        // Commit the batch write to save the questions and update the test
        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    // Questions and test association saved successfully
                    callback.onTestSaved(testId);
                })
                .addOnFailureListener(e -> {
                    // Failed to save the questions or update the test
                    callback.onFailure(e.getMessage());
                });
    }

    public void deleteTest(String testId, TestDelete callback) {
        // Get the document reference for the test
        DocumentReference testRef = firestore.collection("tests").document(testId);

        // Delete the test document from the database
        testRef.delete()
                .addOnSuccessListener(aVoid -> {
                    // Test deleted successfully
                    callback.onTestDeleted();
                })
                .addOnFailureListener(e -> {
                    // Failed to delete the test
                    callback.onFailure(e.getMessage());
                });
    }
}
