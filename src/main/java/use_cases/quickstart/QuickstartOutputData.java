package use_cases.quickstart;

import entities.Quiz;

public class QuickstartOutputData {
    private final Quiz quiz;
    private final boolean success;
    private final String errorMessage;

    public QuickstartOutputData(Quiz quiz, boolean success, String errorMessage) {
        this.quiz = quiz;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

