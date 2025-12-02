package entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a leaderboard for a specific quiz.
 * Contains ranked results sorted by score (highest first).
 */
public class Leaderboard {
    private int quizId;
    private List<QuizResult> rankedResults;

    /**
     * Creates a new Leaderboard for a quiz.
     * @param quizId : the unique identifier for the quiz
     * @param rankedResults : the list of quiz results, already sorted by score (highest first)
     */
    public Leaderboard(int quizId, List<QuizResult> rankedResults) {
        this.quizId = quizId;
        if (rankedResults != null) {
            this.rankedResults = new ArrayList<>(rankedResults);
        }
        else {
            this.rankedResults = new ArrayList<>();
        }
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public List<QuizResult> getRankedResults() {
        return new ArrayList<>(rankedResults);
    }

    /**
     * Sets the rank of a user in the leaderboard.
     * @param rankedResults : the list of results
     */
    public void setRankedResults(List<QuizResult> rankedResults) {
        if (rankedResults != null) {
            this.rankedResults = new ArrayList<>(rankedResults);
        }
        else {
            this.rankedResults = new ArrayList<>();
        }
    }

    /**
     * Gets the rank (1-indexed) of a user in the leaderboard.
     * @param username : the username to find the rank for
     * @return the rank if found, -1 if user not found
     */
    public int getUserRank(String username) {
        for (int i = 0; i < rankedResults.size(); i++) {
            if (rankedResults.get(i).getUsername().equals(username)) {
                return i + 1;
            }
        }
        return -1;
        // User not found
    }

    /**
     * Gets the QuizResult for a specific user.
     * @param username : the username to find
     * @return the QuizResult if found, null otherwise
     */
    public QuizResult getUserResult(String username) {
        for (QuizResult result : rankedResults) {
            if (result.getUsername().equals(username)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Checks if a user is the first participant (rank 1).
     * @param username : the username to check
     * @return true if the user is ranked first, false otherwise
     */
    public boolean isUserFirst(String username) {
        return getUserRank(username) == 1;
    }

    /**
     * Checks if the leaderboard is empty (no participants).
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return rankedResults.isEmpty();
    }

    /**
     * Gets the total number of participants.
     * @return the number of participants
     */
    public int getParticipantCount() {
        return rankedResults.size();
    }

    /**
     * Gets the top N results from the leaderboard.
     * @param number : the number of top results to retrieve
     * @return a list of the top N results
     */
    public List<QuizResult> getTopResults(int number) {
        final int size = Math.min(number, rankedResults.size());
        return new ArrayList<>(rankedResults.subList(0, size));
    }
}
