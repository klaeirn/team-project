package data_access;

import entities.Question;
import entities.Quiz;
import org.json.JSONException;
import use_cases.create_quiz.CreateQuizDataAccessInterface;
import use_cases.create_quiz.UserDataAccessInterface;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONString;



import java.nio.file.Path;

public class FileQuizDataAccessObject implements CreateQuizDataAccessInterface {

    private final Path quizPath = Path.of("quizzes.json");

    @Override
    public void saveUserQuiz(Quiz quiz) {
        JSONObject quizJson = new JSONObject();
        try {
            quizJson.put("quizName", quiz.getName());
            quizJson.put("quizCreator", quiz.getCreatorUsername());
            quizJson.put("category", quiz.getCategory());
            JSONArray questions = new JSONArray();
            for (Question q: quiz.getQuestions()) {
                JSONObject questionJson = new JSONObject();
                questionJson.put("questionName", q.getTitle());
                JSONArray options = new  JSONArray();
                for (String option: q.getOptions()) {
                    options.put(option);
                }
                questionJson.put("options", options);
                questionJson.put("answer", q.getAnswer());
                questions.put(questionJson);
            }

            quizJson.put("questions", questions);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}
