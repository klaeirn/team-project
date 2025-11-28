package interface_adapter.take_shared_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.take_quiz.TakeQuizController;
import use_cases.take_shared_quiz.TakeSharedQuizOutputBoundary;
import use_cases.take_shared_quiz.TakeSharedQuizOutputData;

public class TakeSharedQuizPresenter implements TakeSharedQuizOutputBoundary {

    private final TakeSharedQuizViewModel takeSharedQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private TakeQuizController takeQuizController;

    public TakeSharedQuizPresenter(TakeSharedQuizViewModel
                                           takeSharedQuizViewModel,
                                   ViewManagerModel viewManagerModel) {

        this.takeSharedQuizViewModel = takeSharedQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void setTakeQuizController(TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
    }

    @Override
    public void prepareSuccessView(TakeSharedQuizOutputData outputData) {

        TakeSharedQuizState state = takeSharedQuizViewModel.getState();
        state.setErrorMessage("");
        takeSharedQuizViewModel.setState(state);

        viewManagerModel.setState("take quiz");
        viewManagerModel.firePropertyChange();

        if (takeQuizController != null) {
            takeQuizController.execute(outputData.getQuiz(), "");
        }
    }
    @Override
    public void prepareFailureView(String errorMessage) {

        TakeSharedQuizState state = takeSharedQuizViewModel.getState();
        state.setErrorMessage(errorMessage);
        takeSharedQuizViewModel.setState(state);
        takeSharedQuizViewModel.firePropertyChange();
    }
}
