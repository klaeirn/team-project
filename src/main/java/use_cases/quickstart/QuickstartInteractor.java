package use_cases.quickstart;

import entities.Quiz;

import java.io.IOException;

public class QuickstartInteractor implements QuickstartInputBoundary {

    private final QuickstartOutputBoundary presenter;
    private final QuickstartDataAccessInterface dataAccessObject;
    private final QuickstartUserDataAccessInterface currentUserProvider;

    public QuickstartInteractor(QuickstartOutputBoundary presenter,
                                QuickstartDataAccessInterface dataAccessObject,
                                QuickstartUserDataAccessInterface currentUserProvider) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public void backToQuizMenu() {
        presenter.showQuizMenu();
    }

    @Override
    public void execute(QuickstartInputData inputData) {
        try {
            Quiz quiz = dataAccessObject.fetchQuiz(
                    inputData.getCategory(),
                    inputData.getDifficulty(),
                    inputData.getType()
            );

            String username = currentUserProvider.getCurrentUsername();

            QuickstartOutputData outputData = new QuickstartOutputData(quiz, username);
            presenter.prepareSuccessView(outputData);
        } catch (IOException e) {
            presenter.prepareFailView("Failed to fetch quiz: " + e.getMessage());
        } catch (Exception e) {
            presenter.prepareFailView("An error occurred: " + e.getMessage());
        }
    }
}
