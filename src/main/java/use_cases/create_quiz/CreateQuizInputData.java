package use_cases.create_quiz;

import java.util.List;

public class CreateQuizInputData {
    private final String quizName;
    private final String category;
    private final List<List<String>> questionsDetails;
    private final List<String> correctAnswers;

    public CreateQuizInputData(String quizName, String category, 
    List<List<String>> questionsDetails, List<String> correctAnswers) {
        this.quizName = quizName;
        this.category = category;
        this.questionsDetails = questionsDetails;
        this.correctAnswers = correctAnswers;
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
}
