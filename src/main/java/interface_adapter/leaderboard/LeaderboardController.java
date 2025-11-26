package interface_adapter.leaderboard;

import interface_adapter.ViewManagerModel;

public class LeaderboardController {
    private final ViewManagerModel viewManagerModel;

    public LeaderboardController(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void navigateBackToResults() {
        viewManagerModel.setState("view results");
        viewManagerModel.firePropertyChange();
    }
}

