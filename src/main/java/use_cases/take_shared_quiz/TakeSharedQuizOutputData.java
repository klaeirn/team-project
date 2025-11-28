package use_cases.take_shared_quiz;

import entities.Quiz;

public class TakeSharedQuizOutputData {
    private final Quiz quiz;

    public TakeSharedQuizOutputData(Quiz quiz) {
        this.quiz = quiz;
    }
    public Quiz getQuiz() {
        return quiz;
    }
}
