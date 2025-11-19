package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.take_quiz.TakeQuizController;
import use_cases.select_existing_quiz.SelectExistingQuizOutputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizOutputData;
import entities.Quiz;

import java.util.List;

public class SelectExistingQuizPresenter implements SelectExistingQuizOutputBoundary {
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private TakeQuizController takeQuizController;

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

    public void setTakeQuizController(TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
    }
}

