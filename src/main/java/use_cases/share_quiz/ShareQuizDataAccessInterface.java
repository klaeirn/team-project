package use_cases.share_quiz;

import entities.Quiz;

public interface ShareQuizDataAccessInterface {

    public String makeHash(Quiz quiz);
}
