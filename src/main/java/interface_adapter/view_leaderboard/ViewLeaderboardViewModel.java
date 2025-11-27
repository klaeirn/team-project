package interface_adapter.view_leaderboard;

import interface_adapter.ViewModel;

public class ViewLeaderboardViewModel extends ViewModel<ViewLeaderboardState> {

    public ViewLeaderboardViewModel() {
        super("view leaderboard");
        setState(new ViewLeaderboardState());
    }
}

