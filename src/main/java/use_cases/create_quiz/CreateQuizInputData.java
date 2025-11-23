package use_cases.create_quiz;

import java.util.List;

public class CreateQuizInputData {
    private final String quizName;
    private final String category;
    private final List<List<String>> questionsDetails;
    private final List<String> correctAnswers;
    private final String username;

    public CreateQuizInputData(String quizName, String category, 
    List<List<String>> questionsDetails, List<String> correctAnswers, String username) {
        this.quizName = quizName;
        this.category = category;
        this.questionsDetails = questionsDetails;
        this.correctAnswers = correctAnswers;
        this.username = username;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCategory() {
        return category;
    }

    public List<List<String>> getQuestionsDetails() {
        return questionsDetails;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public String getUsername() { return username; }
}
