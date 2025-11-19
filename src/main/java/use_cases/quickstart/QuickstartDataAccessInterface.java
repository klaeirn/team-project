package use_cases.quickstart;

import entities.Quiz;
import java.io.IOException;

public interface QuickstartDataAccessInterface {
    /**
     * Fetches quiz questions from the Open Trivia DB API and creates a Quiz entity.
     * @param categoryDisplay the category display name (e.g., "Science & Nature")
     * @param difficultyDisplay the difficulty display name (e.g., "Easy", "Medium", "Hard")
     * @param typeDisplay the question type display name (e.g., "Multiple Choice", "True/False")
     * @return a Quiz entity containing the fetched questions
     * @throws IOException if there's an error fetching data from the API
     */
    Quiz fetchQuiz(String categoryDisplay, String difficultyDisplay, String typeDisplay) throws IOException;
}

