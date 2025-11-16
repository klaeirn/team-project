package use_cases.take_quiz;

import entities.Quiz;

public class TakeQuizInputData {
    private final Quiz quiz;
    private final String username;

    public TakeQuizInputData(Quiz quiz, String username) {
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

