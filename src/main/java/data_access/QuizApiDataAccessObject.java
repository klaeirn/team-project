package data_access;

import entities.Question;
import entities.QuestionFactory;
import entities.Quiz;
import entities.QuizFactory;
import use_cases.quickstart.QuickstartDataAccessInterface;
import use_cases.view_results.ViewResultsDataAccessInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizApiDataAccessObject implements QuickstartDataAccessInterface, ViewResultsDataAccessInterface {
    private final QuestionFactory questionFactory;
    private final QuizFactory quizFactory;
    private final Map<String, Quiz> quizCache = new HashMap<>();

    public QuizApiDataAccessObject(QuestionFactory questionFactory, QuizFactory quizFactory) {
        this.questionFactory = questionFactory;
        this.quizFactory = quizFactory;
    }

    private String getCacheKey(String quizName, String creatorUsername) {
        return quizName + "|" + creatorUsername;
    }

    @Override
    public Quiz fetchQuiz(String category, String difficulty, String type) throws IOException {
        String url = QuizApiDatabase.buildUrl(category, difficulty, type);
        return fetchQuizFromUrl(url);
    }

    private Quiz fetchQuizFromUrl(String urlString) throws IOException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Request interrupted", e);
        }

        int status = response.statusCode();
        if (status != 200) {
            throw new IOException("Failed to fetch quiz: HTTP " + status);
        }

        String jsonString = response.body();

        JSONObject root = new JSONObject(jsonString);
        int apiCode = root.optInt("response_code", -1);
        if (apiCode != 0) {
            throw new IOException("API returned error code: " + apiCode);
        }

        JSONArray results = root.optJSONArray("results");
        List<Question> questions = new ArrayList<>();
        if (results != null) {
            for (int i = 0; i < results.length(); i++) {
                JSONObject q = results.optJSONObject(i);
                if (q == null) continue;

                String questionText = decodeHtmlEntities(q.optString("question", ""));
                String correctAnswer = decodeHtmlEntities(q.optString("correct_answer", ""));
                if (questionText.isEmpty() || correctAnswer.isEmpty()) {
                    continue;
                }

                List<String> options = new ArrayList<>();
                JSONArray incorrect = q.optJSONArray("incorrect_answers");
                if (incorrect != null) {
                    for (int j = 0; j < incorrect.length(); j++) {
                        options.add(decodeHtmlEntities(incorrect.optString(j, "")));
                    }
                }
                options.add(correctAnswer);
                Collections.shuffle(options);

                questions.add(questionFactory.createQuestion(questionText, options, correctAnswer));
            }
        }
        if (questions.isEmpty()) {
            throw new IOException("No questions returned from API");
        }

        Quiz quiz = quizFactory.createQuiz("Quickstart Quiz", "System", "Quickstart", questions);

        String cacheKey = getCacheKey(quiz.getName(), quiz.getCreatorUsername());
        quizCache.put(cacheKey, quiz);

        return quiz;
    }

    @Override
    public Quiz getQuiz(String quizName, String creatorUsername) {
        String cacheKey = getCacheKey(quizName, creatorUsername);
        return quizCache.get(cacheKey);
    }

    /**
     * Decodes HTML entities in the text (e.g., &quot; -> ", &#039; -> ')
     */
    private String decodeHtmlEntities(String text) {
        return text
                .replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&nbsp;", " ")
                .replace("&apos;", "'");
    }
}

