package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;

public class SelectExistingQuizPresenter {
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public SelectExistingQuizPresenter(SelectExistingQuizViewModel selectExistingQuizViewModel,
                                       ViewManagerModel viewManagerModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView() {
        // TODO: When use case is created, handle success case
        // This might switch to a quiz taking view or show success message
    }

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

