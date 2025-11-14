package use_cases.create_quiz;

import entities.Quiz;

public interface CreateQuizDataAccessInterface {
    void saveUserQuiz(Quiz quiz);
}

