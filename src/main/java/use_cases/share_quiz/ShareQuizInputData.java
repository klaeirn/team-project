package use_cases.share_quiz;

import entities.Quiz;

public class ShareQuizInputData {

    private final Quiz quiz;

    public ShareQuizInputData(Quiz quiz) {
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}
