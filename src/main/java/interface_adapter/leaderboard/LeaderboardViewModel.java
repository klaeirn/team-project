package interface_adapter.leaderboard;

import interface_adapter.ViewModel;

public class LeaderboardViewModel extends ViewModel<LeaderboardState> {

    public LeaderboardViewModel() {
        super("leaderboard");
        setState(new LeaderboardState());
    }
}

