package Entities;

import java.util.ArrayList;

public class Leaderboard {
    private int quiz_id;
    private List<QuizResult> ranked_results;

    public Leaderboard(int quiz_id, List<QuizResult> ranked_results) {
        this.quiz_id = quiz_id;
        this.ranked_results = ranked_results;
    }
}
