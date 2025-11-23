package use_cases.view_results;

import entities.Quiz;

public interface ViewResultsDataAccessInterface {
    Quiz getQuiz(String quizName, String creatorUsername);
}


