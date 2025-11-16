package interface_adapter.quickstart;

import interface_adapter.ViewManagerModel;
import interface_adapter.take_quiz.TakeQuizController;
import use_cases.quickstart.QuickstartOutputBoundary;
import use_cases.quickstart.QuickstartOutputData;

public class QuickstartPresenter implements QuickstartOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final QuickstartViewModel quickstartViewModel;
    private TakeQuizController takeQuizController;
    private static final String QUIZ_MENU_VIEW = "quiz menu";

    public QuickstartPresenter(ViewManagerModel viewManagerModel, QuickstartViewModel quickstartViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.quickstartViewModel = quickstartViewModel;
    }

    public void setTakeQuizController(TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
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
        state.setErrorMessage(null);
        quickstartViewModel.setState(state);
        quickstartViewModel.firePropertyChange();

        if (takeQuizController != null) {
            String username = outputData.getUsername();
            takeQuizController.execute(outputData.getQuiz(), username);
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
}
