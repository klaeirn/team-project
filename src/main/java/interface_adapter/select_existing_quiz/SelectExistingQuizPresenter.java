package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.take_quiz.TakeQuizController;
import use_cases.select_existing_quiz.SelectExistingQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizOutputData;

public class SelectExistingQuizPresenter implements SelectExistingQuizOutputBoundary {
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private TakeQuizController takeQuizController; // wired in AppBuilder

    public SelectExistingQuizPresenter(SelectExistingQuizViewModel selectExistingQuizViewModel,
                                       ViewManagerModel viewManagerModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void setTakeQuizController(TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
    }

    @Override
    public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
        // Navigate to Take Quiz view
        viewManagerModel.setState("take quiz");
        viewManagerModel.firePropertyChange();

        // Start the take-quiz flow
        if (takeQuizController != null) {
            takeQuizController.execute(outputData.getQuiz(), outputData.getUsername());
        }
    }

    @Override
    public void prepareFailView(String error) {
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setErrorMessage(error);
        selectExistingQuizViewModel.firePropertyChange();
    }

    public void prepareQuizzesView(java.util.List<entities.Quiz> quizzes) {
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setAvailableQuizzes(quizzes);
        state.setErrorMessage(null);
        selectExistingQuizViewModel.firePropertyChange();
    }
}

