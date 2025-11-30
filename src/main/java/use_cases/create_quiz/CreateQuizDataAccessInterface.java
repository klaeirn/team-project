package use_cases.create_quiz;

import entities.Quiz;

public interface CreateQuizDataAccessInterface {

    /**
     * Method for the saving the user's quiz.
     *
     * @param quiz : is the Quiz object that includes all the details of the Quiz
     */
    void saveUserQuiz(Quiz quiz);
}

