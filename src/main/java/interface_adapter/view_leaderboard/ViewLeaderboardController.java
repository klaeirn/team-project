package interface_adapter.view_leaderboard;

import use_cases.view_leaderboard.ViewLeaderboardInputBoundary;
import use_cases.view_leaderboard.ViewLeaderboardInputData;

public class ViewLeaderboardController {
    private final ViewLeaderboardInputBoundary viewLeaderboardUseCaseInteractor;

    public ViewLeaderboardController(ViewLeaderboardInputBoundary viewLeaderboardUseCaseInteractor) {
        this.viewLeaderboardUseCaseInteractor = viewLeaderboardUseCaseInteractor;
    }

    public void execute(String quizName, String creatorUsername) {
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData(quizName, creatorUsername);
        viewLeaderboardUseCaseInteractor.execute(inputData);
    }
}

