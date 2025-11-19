package use_cases.view_results;

import java.util.Map;

public class ViewResultsInputData {
    private final String quizName;
    private final String creatorUsername;
    private final String username;
    private final Map<Integer, String> userAnswers;

    public ViewResultsInputData(String quizName, String creatorUsername, String username, Map<Integer, String> userAnswers) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
        this.username = username;
        this.userAnswers = userAnswers;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getUsername() {
        return username;
    }

    public Map<Integer, String> getUserAnswers() {
        return userAnswers;
    }
}

