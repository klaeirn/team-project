package use_cases.take_shared_quiz;

import entities.Quiz;

public interface TakeSharedQuizDataAccessInterface {

    Quiz getFromHash(String hash);
}