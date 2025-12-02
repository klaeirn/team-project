package entities;

import java.util.List;

public class QuizFactory {
    /**
     * Creates a new Quiz with the given name, creatorUsername, category and questions.
     *
     * @param name : the name of the quiz, must not be null or empty.
     * @param creatorUsername : the user's username, must not be null or empty.
     * @param category : the quiz category, must not be null or empty.
     * @param questions : the questions for the quiz, must have 10 questions.
     * @return a new instance of Quiz
     */
    public Quiz createQuiz(String name, String creatorUsername, String category, List<Question> questions) {
        return new Quiz(name, creatorUsername, category, questions);
    }
}
