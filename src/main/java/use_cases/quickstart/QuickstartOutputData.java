package use_cases.quickstart;

import entities.Quiz;

/**
 * Output data for Quickstart flow: carries the generated Quiz and the username of the taker.
 * Errors are reported via the OutputBoundary's prepareFailView(String), so no error fields here.
 */
public class QuickstartOutputData {
    private final Quiz quiz;
    private final String username;

    public QuickstartOutputData(Quiz quiz, String username) {
        this.quiz = quiz;
        this.username = username;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getUsername() {
        return username;
    }
}

