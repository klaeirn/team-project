package use_cases.quickstart;

import entities.Quiz;
import use_cases.take_quiz.TakeQuizInputBoundary;
import use_cases.take_quiz.TakeQuizInputData;

import java.io.IOException;

public class QuickstartInteractor implements QuickstartInputBoundary {

    private final QuickstartOutputBoundary presenter;
    private final QuickstartDataAccessInterface dataAccessObject;
    private final QuickstartUserDataAccessInterface currentUserProvider;
    private final TakeQuizInputBoundary takeQuizInteractor;

    public QuickstartInteractor(QuickstartOutputBoundary presenter,
                                QuickstartDataAccessInterface dataAccessObject,
                                QuickstartUserDataAccessInterface currentUserProvider,
                                TakeQuizInputBoundary takeQuizInteractor) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
        this.currentUserProvider = currentUserProvider;
        this.takeQuizInteractor = takeQuizInteractor;
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

            if (takeQuizInteractor != null) {
                TakeQuizInputData takeQuizInputData = new TakeQuizInputData(quiz, username);
                takeQuizInteractor.execute(takeQuizInputData);
            }
        } catch (IOException e) {
            presenter.prepareFailView("Failed to fetch quiz: " + e.getMessage());
        } catch (Exception e) {
            presenter.prepareFailView("An error occurred: " + e.getMessage());
        }
    }
}
