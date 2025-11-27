package data_access;

import entities.Leaderboard;
import entities.QuizResult;
import org.json.JSONArray;
import org.json.JSONObject;
import use_cases.view_leaderboard.LeaderboardDataAccessInterface;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * File-based data access object for leaderboard storage.
 * Stores leaderboards in a JSON file, with results sorted by score (highest first).
 */
public class FileLeaderboardDataAccessObject implements LeaderboardDataAccessInterface {
    private final Path leaderboardPath;

    public FileLeaderboardDataAccessObject(String leaderboardPath) {
        this.leaderboardPath = Path.of(leaderboardPath);
    }

    private String getQuizKey(String quizName, String creatorUsername) {
        return quizName + "|" + creatorUsername;
    }

    @Override
    public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
        try {
            // Read existing leaderboards
            JSONObject leaderboardsJson;
            if (Files.exists(leaderboardPath) && Files.size(leaderboardPath) > 0) {
                String jsonStr = Files.readString(leaderboardPath);
                if (jsonStr.trim().isEmpty()) {
                    leaderboardsJson = new JSONObject();
                } else {
                    leaderboardsJson = new JSONObject(jsonStr);
                }
            } else {
                leaderboardsJson = new JSONObject();
            }

            String quizKey = getQuizKey(quizName, creatorUsername);
            JSONArray resultsArray;

            // Get or create results array for this quiz
            if (leaderboardsJson.has(quizKey)) {
                resultsArray = leaderboardsJson.getJSONArray(quizKey);
            } else {
                resultsArray = new JSONArray();
            }

            // Add new result
            JSONObject resultJson = new JSONObject();
            resultJson.put("username", quizResult.getUsername());
            resultJson.put("score", quizResult.getScore());
            resultJson.put("totalQuestions", quizResult.getTotalQuestions());
            resultsArray.put(resultJson);

            // Update leaderboards
            leaderboardsJson.put(quizKey, resultsArray);

            // Write back to file
            Files.writeString(leaderboardPath, leaderboardsJson.toString(2));

        } catch (Exception e) {
            throw new RuntimeException("Failed to save quiz result to leaderboard", e);
        }
    }

    @Override
    public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
        try {
            if (!Files.exists(leaderboardPath) || Files.size(leaderboardPath) == 0) {
                return new Leaderboard(0, new ArrayList<>());
            }

            String jsonStr = Files.readString(leaderboardPath);
            if (jsonStr.trim().isEmpty()) {
                return new Leaderboard(0, new ArrayList<>());
            }

            JSONObject leaderboardsJson = new JSONObject(jsonStr);
            String quizKey = getQuizKey(quizName, creatorUsername);

            if (!leaderboardsJson.has(quizKey)) {
                return new Leaderboard(0, new ArrayList<>());
            }

            JSONArray resultsArray = leaderboardsJson.getJSONArray(quizKey);
            List<QuizResult> results = new ArrayList<>();

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultJson = resultsArray.getJSONObject(i);
                String username = resultJson.getString("username");
                int score = resultJson.getInt("score");
                int totalQuestions = resultJson.getInt("totalQuestions");
                results.add(new QuizResult(username, score, totalQuestions));
            }

            // Sort by score (highest first), then by username for ties
            results.sort(Comparator.comparing(QuizResult::getScore).reversed()
                    .thenComparing(QuizResult::getUsername));

            // Use a simple hash of quiz key as quizId
            int quizId = quizKey.hashCode();
            return new Leaderboard(quizId, results);

        } catch (Exception e) {
            throw new RuntimeException("Failed to get leaderboard", e);
        }
    }
}

