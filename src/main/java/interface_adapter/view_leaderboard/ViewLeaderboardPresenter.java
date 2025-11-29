package interface_adapter.view_leaderboard;

import interface_adapter.ViewManagerModel;
import use_cases.view_leaderboard.ViewLeaderboardOutputBoundary;
import use_cases.view_leaderboard.ViewLeaderboardOutputData;

public class ViewLeaderboardPresenter implements ViewLeaderboardOutputBoundary {
    private final ViewLeaderboardViewModel viewLeaderboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewLeaderboardPresenter(ViewLeaderboardViewModel viewLeaderboardViewModel, ViewManagerModel viewManagerModel) {
        this.viewLeaderboardViewModel = viewLeaderboardViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
        ViewLeaderboardState state = viewLeaderboardViewModel.getState();
        state.setLeaderboard(outputData.getLeaderboard());
        state.setQuizName(outputData.getQuizName());
        state.setErrorMessage(null);

        viewLeaderboardViewModel.setState(state);
        viewLeaderboardViewModel.firePropertyChange();

        // Navigate to the leaderboard view
        viewManagerModel.setState("leaderboard");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        ViewLeaderboardState state = viewLeaderboardViewModel.getState();
        state.setErrorMessage(error);
        viewLeaderboardViewModel.setState(state);
        viewLeaderboardViewModel.firePropertyChange();
    }
}

