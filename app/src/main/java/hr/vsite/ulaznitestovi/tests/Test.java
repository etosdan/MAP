package hr.vsite.ulaznitestovi.tests;

import java.util.List;

public class Test {
    private String testTitle;
    private int testDuration;
    private List<Question> questions;
    private boolean isPassed;

    public Test(String testTitle, int testDuration, List<Question> questions) {
        this.testTitle = testTitle;
        this.testDuration = testDuration;
        this.questions = questions;
        // Initialize any additional properties
    }

    public String getTestTitle() {
        return testTitle;
    }

    public int getTestDuration() {
        return testDuration;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
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
