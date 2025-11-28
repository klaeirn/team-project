package data_access;

import entities.Question;
import entities.Quiz;
import org.json.JSONObject;
import use_cases.take_shared_quiz.TakeSharedQuizDataAccessInterface;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadHashToQuizDataAccessObject implements
        TakeSharedQuizDataAccessInterface {

    private final FileQuizDataAccessObject fileQuizDataAccessObject =
            new FileQuizDataAccessObject("quizzes.json");
    private final Path hashPath = Path.of("hashtoquiz.json");

    @Override
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
