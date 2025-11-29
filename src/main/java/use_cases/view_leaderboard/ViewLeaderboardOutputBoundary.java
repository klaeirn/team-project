package use_cases.view_leaderboard;

public interface ViewLeaderboardOutputBoundary {
    void prepareSuccessView(ViewLeaderboardOutputData outputData);
    void prepareFailView(String error);
}

