package data_access;

import entities.Quiz;
import org.json.JSONObject;
import use_cases.share_quiz.ShareQuizDataAccessInterface;
import use_cases.take_shared_quiz.TakeSharedQuizDataAccessInterface;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HashtoQuizDataAccessObject implements ShareQuizDataAccessInterface, TakeSharedQuizDataAccessInterface {

    private final FileQuizDataAccessObject fileQuizDataAccessObject =  new FileQuizDataAccessObject("quizzes.json");
    private final Path hashPath = Path.of("hashtoquiz.json");

    public String makeHash(Quiz quiz) {

        String hashBase = quiz.getCreatorUsername() + quiz.getCategory() + quiz.getName() +
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        MessageDigest digest;
        try {
            MessageDigest localDigest = MessageDigest.getInstance("SHA-256");
            digest = localDigest;
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException("Something has gone horribly, horribly wrong. Please check if your computer is okay");
        }
        byte[] hashBytes = digest.digest(hashBase.getBytes(StandardCharsets.UTF_8));

        StringBuilder hex = new StringBuilder();
        for (byte b : hashBytes) {
            hex.append(String.format("%02x", b));
        }

        String hash = hex.toString();

        // Save mapping to file
        try {
            JSONObject root;

            // Create file if empty / missing
            if (!Files.exists(hashPath) || Files.readString(hashPath).isBlank()) {
                root = new JSONObject();
            } else {
                root = new JSONObject(Files.readString(hashPath));
            }

            // Add mapping
            JSONObject map = new JSONObject();
            map.put("username", quiz.getCreatorUsername());
            map.put("quizName", quiz.getName());

            root.put(hash, map);

            // Write back to file
            Files.writeString(hashPath, root.toString(4)); // pretty print

        } catch (Exception e) {
            throw new RuntimeException("Failed to save hash mapping", e);
        }

        return hash;

    }

    public Quiz getFromHash(String hash) {

        try {
            if (!Files.exists(hashPath)) {
                return null;
            }

            String jsonStr = Files.readString(hashPath);
            if (jsonStr.isBlank()) {
                return null;
            }

            JSONObject root = new JSONObject(jsonStr);

            if (!root.has(hash)) {
                return null;
            }

            JSONObject map = root.getJSONObject(hash);

            String username = map.getString("username");
            String quizName = map.getString("quizName");

            return this.fileQuizDataAccessObject.getQuiz(quizName, username);

        } catch (Exception e) {
            throw new RuntimeException("Error reading quiz from hash", e);
        }

    }
}
