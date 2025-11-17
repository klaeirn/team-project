package use_cases.quickstart;

import entities.Quiz;
import java.io.IOException;

public interface QuickstartDataAccessInterface {
    /**
     * Fetches quiz questions from the Open Trivia DB API and creates a Quiz entity.
     * @param url the API URL to fetch questions from
     * @return a Quiz entity containing the fetched questions
     * @throws IOException if there's an error fetching data from the API
     */
    Quiz fetchQuizFromUrl(String url) throws IOException;
}

