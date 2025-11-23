package data_access;

import entities.Question;
import entities.Quiz;
import entities.QuizFactory;
import org.json.JSONException;
import use_cases.create_quiz.CreateQuizDataAccessInterface;
import use_cases.create_quiz.UserDataAccessInterface;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONString;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileQuizDataAccessObject implements CreateQuizDataAccessInterface {

    private final Path quizPath;
    private final QuizFactory quizFactory;

    public FileQuizDataAccessObject(String quizPath) {
        this.quizPath = Path.of(quizPath);
        this.quizFactory = new QuizFactory();

    }


    @Override
    public void saveUserQuiz(Quiz quiz) {
        try {
            JSONObject quizJson = new JSONObject();
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

            // read existing quizzes from file (or create empty array if file doesn't exist)
            JSONArray quizArray;
            if (Files.exists(quizPath) && Files.size(quizPath) > 0) {
                String jsonStr = Files.readString(quizPath);
                if (jsonStr.trim().isEmpty()) {
                    quizArray = new JSONArray();
                } else {
                    quizArray = new JSONArray(jsonStr);
                }
            } else {
                quizArray = new JSONArray();
            }
            quizArray.put(quizJson);
            Files.writeString(quizPath, quizArray.toString(2));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Quiz getQuiz(String quizName, String username) {

        try {
            String jsonStr = Files.readString(quizPath);
            JSONArray quizArray = new JSONArray(jsonStr);

            for (int i = 0; i < quizArray.length(); i++) {
                JSONObject quizJson = quizArray.getJSONObject(i);

                String name = quizJson.getString("quizName");
                String creator = quizJson.getString("quizCreator");

                if (name.equals(quizName) && creator.equals(username)) {

                    String category = quizJson.getString("category");

                    JSONArray questionsJson = quizJson.getJSONArray("questions");
                    List<Question> questionList = new ArrayList<>();

                    for (int j = 0; j < questionsJson.length(); j++) {
                        JSONObject qJson = questionsJson.getJSONObject(j);

                        String questionName = qJson.getString("questionName");
                        String answer = qJson.getString("answer");

                        JSONArray optionsJson = qJson.getJSONArray("options");
                        List<String> options = new ArrayList<>();

                        for (int k = 0; k < optionsJson.length(); k++) {
                            options.add(optionsJson.getString(k));
                        }

                        questionList.add(new Question(questionName, options, answer));
                    }

                    return quizFactory.createQuiz(name, creator, category, questionList);
                }
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

