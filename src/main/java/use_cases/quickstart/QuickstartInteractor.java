package use_cases.quickstart;

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
            // Fetch quiz from API using category, difficulty, and type
            Quiz quiz = dataAccessObject.fetchQuiz(
                    inputData.getCategory(),
                    inputData.getDifficulty(),
                    inputData.getType()
            );

            // Username is provided by the input data now
            String username = inputData.getUsername();

            // Prepare success view with quiz and username
            QuickstartOutputData outputData = new QuickstartOutputData(quiz, username);
            presenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            presenter.prepareFailView("Failed to fetch quiz: " + e.getMessage());
        } catch (Exception e) {
            presenter.prepareFailView("An error occurred: " + e.getMessage());
        }
    }
}
