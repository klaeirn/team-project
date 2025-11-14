package entities;

/**
 * Represents a single quiz result for a user.
 * Contains the user's score, percentage, and username for leaderboard ranking.
 */
public class QuizResult {
    private String username;
    private int score;
    private int totalQuestions;
    private double percentage;

    /**
     * Creates a new QuizResult.
     * @param username the username of the quiz taker
     * @param score the number of correct answers
     * @param totalQuestions the total number of questions in the quiz
     */
    public QuizResult(String username, int score, int totalQuestions) {
        this.username = username;
        this.score = score;
        this.totalQuestions = totalQuestions;
        if (totalQuestions > 0) {
            this.percentage = (double) score / totalQuestions * 100;
        } else {
            this.percentage = 0.0;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        // Recalculate percentage when score changes
        if (totalQuestions > 0) {
            this.percentage = (double) this.score / totalQuestions * 100;
        } else {
            this.percentage = 0.0;
        }
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        // Recalculate percentage when total questions changes
        if (totalQuestions > 0) {
            this.percentage = (double) score / this.totalQuestions * 100;
        } else {
            this.percentage = 0.0;
        }
    }

    public double getPercentage() {
        return percentage;
    }
}

