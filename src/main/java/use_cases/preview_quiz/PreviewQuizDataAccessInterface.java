package use_cases.preview_quiz;
import entities.Quiz;

public interface PreviewQuizDataAccessInterface {
    /**
     * Returns the quiz created by the specific user with the given quiz name.
     *
     * @param quizName name of the quiz
     * @param creatorUsername username of the creator
     * @return the Quiz object, or null if not found
     */
    Quiz getQuiz(String quizName, String creatorUsername);
}
