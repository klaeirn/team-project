package use_cases.select_existing_quiz;

import entities.Quiz;

public class SelectExistingQuizInteractor implements SelectExistingQuizInputBoundary {

    private final SelectExistingQuizDataAccessInterface currentUserProvider;
    private final SelectExistingQuizOutputBoundary presenter;

    public SelectExistingQuizInteractor(SelectExistingQuizDataAccessInterface currentUserProvider,
                                        SelectExistingQuizOutputBoundary presenter) {
        this.currentUserProvider = currentUserProvider;
        this.presenter = presenter;
    }

    @Override
    public void execute(SelectExistingQuizInputData inputData) {
        if (inputData == null || inputData.getQuiz() == null) {
            presenter.prepareFailView("Please select a quiz.");
            return;
        }

        Quiz quiz = inputData.getQuiz();

        String username = inputData.getUsername();
        if (username == null) {
            username = currentUserProvider.getCurrentUsername();
        }

        presenter.prepareSuccessView(new SelectExistingQuizOutputData(quiz, username));
    }
}
