package use_cases.view_results;

import entities.Quiz;
import java.util.Map;

public class ViewResultsInputData {
    private final Quiz quiz;
    private final String username;
    private final Map<Integer, String> userAnswers;

    public ViewResultsInputData(Quiz quiz, String username, Map<Integer, String> userAnswers) {
        this.quiz = quiz;
        this.username = username;
        this.userAnswers = userAnswers;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getUsername() {
        return username;
    }

    public Map<Integer, String> getUserAnswers() {
        return userAnswers;
    }
}

