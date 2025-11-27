package use_cases.view_leaderboard;

import entities.Leaderboard;
import entities.QuizResult;

/**
 * Data access interface for leaderboard operations.
 * Handles saving and retrieving quiz results for leaderboards.
 */
public interface LeaderboardDataAccessInterface {
    /**
     * Saves a quiz result to the leaderboard for a specific quiz.
     * @param quizName the name of the quiz
     * @param creatorUsername the username of the quiz creator
     * @param quizResult the quiz result to save
     */
    void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult);

    /**
     * Gets the leaderboard for a specific quiz.
     * @param quizName the name of the quiz
     * @param creatorUsername the username of the quiz creator
     * @return the leaderboard with ranked results, or null if no leaderboard exists
     */
    Leaderboard getLeaderboard(String quizName, String creatorUsername);
}

