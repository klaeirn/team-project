package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.select_existing_quiz.SelectExistingQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizOutputData;

public class SelectExistingQuizPresenter implements SelectExistingQuizOutputBoundary {
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private static final String TAKE_QUIZ_VIEW = "take quiz";

    public SelectExistingQuizPresenter(SelectExistingQuizViewModel selectExistingQuizViewModel,
                                       ViewManagerModel viewManagerModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
        // Navigate to Take Quiz view
        // The SelectExistingQuizInteractor will handle calling the TakeQuiz use case
        viewManagerModel.setState(TAKE_QUIZ_VIEW);
        viewManagerModel.firePropertyChange();
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

