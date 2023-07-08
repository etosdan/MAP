package hr.vsite.ulaznitestovi.tests;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String questionText;
    private List<String> options;
    private List<Integer> correctAnswerIndices;
    // Add any additional properties as needed

    public Question(String questionText, List<String> options, List<Integer> correctAnswerIndices) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndices = correctAnswerIndices;
        // Initialize any additional properties
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getCorrectAnswerIndices() {
        return correctAnswerIndices;
    }

    public boolean isAnswerCorrect(String userAnswer) {
        // Convert user answer to list of indices
        List<Integer> userAnswerIndices = convertUserAnswer(userAnswer);

        // Check if the user answer matches the correct answer indices
        return correctAnswerIndices.containsAll(userAnswerIndices)
                && userAnswerIndices.containsAll(correctAnswerIndices);
    }

    private List<Integer> convertUserAnswer(String userAnswer) {
        // Convert the user answer string to a list of indices
        List<Integer> indices = new ArrayList<>();
        String[] answerArray = userAnswer.split(",");
        for (String answer : answerArray) {
            try {
                int index = Integer.parseInt(answer.trim());
                indices.add(index);
            } catch (NumberFormatException e) {
                // Ignore non-numeric answers
            }
        }
        return indices;
    }

    // Add any additional methods as needed
}

