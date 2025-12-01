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
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setSelectedQuiz(outputData.getQuiz());
        state.setUsername(outputData.getUsername());
        state.setErrorMessage(null);
        selectExistingQuizViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setErrorMessage(error);
        selectExistingQuizViewModel.firePropertyChange();
    }

}

