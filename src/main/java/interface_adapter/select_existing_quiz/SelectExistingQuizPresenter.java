package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.select_existing_quiz.SelectExistingQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizOutputData;
import entities.Quiz;

import java.util.List;

public class SelectExistingQuizPresenter implements SelectExistingQuizOutputBoundary {
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public SelectExistingQuizPresenter(SelectExistingQuizViewModel selectExistingQuizViewModel,
                                       ViewManagerModel viewManagerModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setSelectedQuiz(outputData.getQuiz());
        state.setUsername(outputData.getUsername());
        selectExistingQuizViewModel.firePropertyChange();

        viewManagerModel.setState("take quiz");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setErrorMessage(error);
        selectExistingQuizViewModel.firePropertyChange();
    }

    public void prepareQuizzesView(List<Quiz> quizzes) {
        final SelectExistingQuizState state = selectExistingQuizViewModel.getState();
        state.setAvailableQuizzes(quizzes);
        state.setErrorMessage(null);
        selectExistingQuizViewModel.firePropertyChange();
    }
}

