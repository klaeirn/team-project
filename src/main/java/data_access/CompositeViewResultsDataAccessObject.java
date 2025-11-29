package data_access;

import entities.Quiz;
import use_cases.view_results.ViewResultsDataAccessInterface;

/**
 * Composite data access object that checks multiple sources for quizzes.
 * First checks QuizApiDataAccessObject (for quickstart quizzes),
 * then checks FileQuizDataAccessObject (for custom/shared quizzes).
 */
public class CompositeViewResultsDataAccessObject implements ViewResultsDataAccessInterface {
    private final QuizApiDataAccessObject apiDataAccess;
    private final FileQuizDataAccessObject fileDataAccess;

    public CompositeViewResultsDataAccessObject(QuizApiDataAccessObject apiDataAccess,
                                               FileQuizDataAccessObject fileDataAccess) {
        this.apiDataAccess = apiDataAccess;
        this.fileDataAccess = fileDataAccess;
    }

    @Override
    public Quiz getQuiz(String quizName, String creatorUsername) {
        // First check API data access (for quickstart quizzes)
        Quiz quiz = apiDataAccess.getQuiz(quizName, creatorUsername);
        if (quiz != null) {
            return quiz;
        }

        // Then check file data access (for custom/shared quizzes)
        return fileDataAccess.getQuiz(quizName, creatorUsername);
    }
}

