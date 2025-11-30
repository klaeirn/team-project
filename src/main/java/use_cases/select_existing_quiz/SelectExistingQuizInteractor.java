package use_cases.select_existing_quiz;

import entities.Quiz;
import use_cases.take_quiz.TakeQuizInputBoundary;
import use_cases.take_quiz.TakeQuizInputData;

public class SelectExistingQuizInteractor implements SelectExistingQuizInputBoundary {

    private final SelectExistingQuizDataAccessInterface currentUserProvider;
    private final SelectExistingQuizOutputBoundary presenter;
    private final TakeQuizInputBoundary takeQuizInteractor;

    public SelectExistingQuizInteractor(SelectExistingQuizDataAccessInterface currentUserProvider,
                                        SelectExistingQuizOutputBoundary presenter,
                                        TakeQuizInputBoundary takeQuizInteractor) {
        this.currentUserProvider = currentUserProvider;
        this.presenter = presenter;
        this.takeQuizInteractor = takeQuizInteractor;
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

        if (takeQuizInteractor != null) {
            TakeQuizInputData takeQuizInputData = new TakeQuizInputData(quiz, username);
            takeQuizInteractor.execute(takeQuizInputData);
        }
    }
}
