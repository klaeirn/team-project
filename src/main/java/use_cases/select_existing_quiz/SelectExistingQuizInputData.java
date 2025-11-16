package use_cases.select_existing_quiz;

import entities.Quiz;

public class SelectExistingQuizInputData {
    private final Quiz quiz;
    private final String username;

    public SelectExistingQuizInputData(Quiz quiz, String username) {
        this.quiz = quiz;
        this.username = username;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getUsername() {
        return username;
    }
}
