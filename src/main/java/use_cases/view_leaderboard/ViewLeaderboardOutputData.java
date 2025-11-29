package use_cases.view_leaderboard;

import entities.Leaderboard;

public class ViewLeaderboardOutputData {
    private final Leaderboard leaderboard;
    private final String quizName;
    private final String errorMessage;

    public ViewLeaderboardOutputData(Leaderboard leaderboard, String quizName, String errorMessage) {
        this.leaderboard = leaderboard;
        this.quizName = quizName;
        this.errorMessage = errorMessage;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

