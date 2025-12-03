package use_cases.take_shared_quiz;

import entities.Quiz;

public class TakeSharedQuizOutputData {

    private final Quiz quiz;
    private final String username;

    public TakeSharedQuizOutputData(Quiz quiz, String username) {
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
