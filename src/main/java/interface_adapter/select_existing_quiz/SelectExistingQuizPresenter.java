package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.select_existing_quiz.SelectExistingQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizOutputData;

public class SelectExistingQuizPresenter implements SelectExistingQuizOutputBoundary {
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;

    public SelectExistingQuizPresenter(SelectExistingQuizViewModel selectExistingQuizViewModel,
                                       ViewManagerModel viewManagerModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
    }

    @Override
    public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
        // Navigation to take quiz view is now handled by TakeQuizPresenter
        // This presenter doesn't need to update any view model state for success
        // The quiz selection is passed directly to TakeQuiz use case
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

