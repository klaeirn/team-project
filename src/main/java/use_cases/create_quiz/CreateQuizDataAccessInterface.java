package use_cases.create_quiz;

import entities.Quiz;

public interface CreateQuizDataAccessInterface {
    void save(Quiz quiz);
}

