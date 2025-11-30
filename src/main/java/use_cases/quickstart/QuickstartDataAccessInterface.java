package use_cases.quickstart;

import entities.Quiz;
import java.io.IOException;

public interface QuickstartDataAccessInterface {
    /**
     * Fetches quiz questions from the Open Trivia DB API and creates a Quiz entity.
     * @param category the quiz category (e.g., "Science & Nature", "History")
     * @param difficulty the difficulty level (e.g., "Easy", "Medium", "Hard")
     * @param type the question type (e.g., "Multiple Choice", "True/False")
     * @return a Quiz entity containing the fetched questions
     * @throws IOException if there's an error fetching data from the API
     */
    Quiz fetchQuiz(String category, String difficulty, String type) throws IOException;
}

