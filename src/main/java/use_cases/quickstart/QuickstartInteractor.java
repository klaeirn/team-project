package use_cases.quickstart;

import data_access.QuizApiDatabase;
import entities.Quiz;

import java.io.IOException;

public class QuickstartInteractor implements QuickstartInputBoundary {

    private final QuickstartOutputBoundary presenter;
    private final QuickstartDataAccessInterface dataAccessObject;

    public QuickstartInteractor(QuickstartOutputBoundary presenter,
                                QuickstartDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void backToQuizMenu() {
        presenter.showQuizMenu();
    }

    @Override
    public void execute(QuickstartInputData inputData) {
        try {
            // Build the URL from the input data
            String url = QuizApiDatabase.buildUrl(
                    inputData.getCategory(),
                    inputData.getDifficulty(),
                    inputData.getType()
            );

            // Fetch quiz from API
            Quiz quiz = dataAccessObject.fetchQuizFromUrl(url);

            // Prepare success view
            QuickstartOutputData outputData = new QuickstartOutputData(quiz, true, null);
            presenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            presenter.prepareFailView("Failed to fetch quiz: " + e.getMessage());
        } catch (Exception e) {
            presenter.prepareFailView("An error occurred: " + e.getMessage());
        }
    }
}
