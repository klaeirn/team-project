package interface_adapter.quickstart;

import interface_adapter.ViewManagerModel;
import interface_adapter.take_quiz.TakeQuizController;
import use_cases.quickstart.QuickstartOutputBoundary;
import use_cases.quickstart.QuickstartOutputData;

public class QuickstartPresenter implements QuickstartOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final QuickstartViewModel quickstartViewModel;
    private static final String QUIZ_MENU_VIEW = "quiz menu";
    private TakeQuizController takeQuizController;

    public QuickstartPresenter(ViewManagerModel viewManagerModel, QuickstartViewModel quickstartViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.quickstartViewModel = quickstartViewModel;
    }

    @Override
    public void showQuizMenu() {
        viewManagerModel.setState(QUIZ_MENU_VIEW);
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareSuccessView(QuickstartOutputData outputData) {
        QuickstartState state = quickstartViewModel.getState();
        state.setQuiz(outputData.getQuiz());
        state.setUsername(outputData.getUsername());
        state.setErrorMessage(null);
        quickstartViewModel.setState(state);
        quickstartViewModel.firePropertyChange();

        // Initialize the Take Quiz flow immediately with the fetched quiz
        if (takeQuizController != null) {
            takeQuizController.execute(outputData.getQuiz(), outputData.getUsername());
        }

        viewManagerModel.setState("take quiz");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        QuickstartState state = quickstartViewModel.getState();
        state.setErrorMessage(error);
        state.setQuiz(null);
        quickstartViewModel.setState(state);
        quickstartViewModel.firePropertyChange();
    }

    /**
     * Injects the TakeQuizController so Quickstart can start the quiz immediately after fetching.
     */
    public void setTakeQuizController(TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
    }
}
