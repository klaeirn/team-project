package use_cases.view_leaderboard;

import entities.Leaderboard;

public class ViewLeaderboardInteractor implements ViewLeaderboardInputBoundary {
    private final ViewLeaderboardOutputBoundary presenter;
    private final LeaderboardDataAccessInterface dataAccessObject;

    public ViewLeaderboardInteractor(ViewLeaderboardOutputBoundary presenter,
                                     LeaderboardDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(ViewLeaderboardInputData inputData) {
        String quizName = inputData.getQuizName();
        String creatorUsername = inputData.getCreatorUsername();

        if (quizName == null || quizName.isBlank()) {
            presenter.prepareFailView("Quiz name is required.");
            return;
        }

        if (creatorUsername == null || creatorUsername.isBlank()) {
            presenter.prepareFailView("Creator username is required.");
            return;
        }

        // Get leaderboard from data access
        Leaderboard leaderboard = dataAccessObject.getLeaderboard(quizName, creatorUsername);

        if (leaderboard == null || leaderboard.isEmpty()) {
            // No leaderboard found - show success view with message
            ViewLeaderboardOutputData outputData = new ViewLeaderboardOutputData(
                    null,
                    quizName,
                    "No results found for this quiz."
            );
            presenter.prepareSuccessView(outputData);
            return;
        }

        // Create output data
        ViewLeaderboardOutputData outputData = new ViewLeaderboardOutputData(
                leaderboard,
                quizName,
                null
        );

        presenter.prepareSuccessView(outputData);
    }
}

