package use_cases.create_quiz;

import entities.Question;
import java.util.List;

public class CreateQuizInputData {
    private final String quizName;
    private final String category;
    private final List<Question> questions;

    public CreateQuizInputData(String quizName, String category, List<Question> questions) {
        this.quizName = quizName;
        this.category = category;
        this.questions = questions;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCategory() {
        return category;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

// in contrroller