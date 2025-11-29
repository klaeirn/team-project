package use_cases.view_results;

import entities.QuizResult;

public class ViewResultsOutputData {
    private final QuizResult quizResult;
    private final String quizName;
    private final String creatorUsername;
    private final String errorMessage;

    public ViewResultsOutputData(QuizResult quizResult, String quizName, String creatorUsername, String errorMessage) {
        this.quizResult = quizResult;
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
        this.errorMessage = errorMessage;
    }

    public QuizResult getQuizResult() {
        return quizResult;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

