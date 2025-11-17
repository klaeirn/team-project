package interface_adapter.share_quiz;

import entities.Quiz;

public class ShareQuizState {
    Quiz sharedQuiz;
    public Quiz getSharedQuiz() {
        return sharedQuiz;
    }
    public void setSharedQuiz(Quiz sharedQuiz) {
        this.sharedQuiz = sharedQuiz;
    }
}
