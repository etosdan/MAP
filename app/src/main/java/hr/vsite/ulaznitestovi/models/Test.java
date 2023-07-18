package hr.vsite.ulaznitestovi.models;

import java.util.List;

public class Test {

    private String testId;
    private String testName;
    private int testDuration;
    private List<Question> questions;
    private boolean isPassed;
    private String authorId;

    public Test() {
        // Required empty constructor for Firebase Firestore deserialization
    }

    public Test(String testId, String testName, int testDuration, List<Question> questions, String authorId) {
        this.testId = testId;
        this.testName = testName;
        this.testDuration = testDuration;
        this.questions = questions;
        this.authorId = authorId;
    }

    public Test(String testName) {
        this.testName = testName;
    }

    public Test(String testName, int testDuration, List<Question> questions, String authorId) {
        this.testName = testName;
        this.testDuration = testDuration;
        this.questions = questions;
        this.authorId = authorId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public long getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(int testDuration) {
        this.testDuration = testDuration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public double getPercentageCorrectAnswers(List<String> userAnswers) {
        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers.get(i);

            if (question.isAnswerCorrect(userAnswer)) {
                correctAnswers++;
            }
        }

        return (double) correctAnswers / totalQuestions * 100;
    }

    // Add any additional methods as needed
}
