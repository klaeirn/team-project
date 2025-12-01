package use_cases.view_leaderboard;

public class ViewLeaderboardInputData {
    private final String quizName;
    private final String creatorUsername;

    public ViewLeaderboardInputData(String quizName, String creatorUsername) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }
}

