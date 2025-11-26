package interface_adapter.share_quiz;

import entities.Quiz;

public class ShareQuizState {
    Quiz sharedQuiz;
    String hash;
    public Quiz getSharedQuiz() {
        return sharedQuiz;
    }
    public void setSharedQuiz(Quiz sharedQuiz) {
        this.sharedQuiz = sharedQuiz;
    }
    public void setHash(String hash) {this.hash = hash;}
    public String getHash() {return this.hash;}
}
