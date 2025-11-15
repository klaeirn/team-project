package interface_adapter.quickstart;

import interface_adapter.ViewManagerModel;
import use_cases.quickstart.QuickstartOutputBoundary;

public class QuickstartPresenter implements QuickstartOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private static final String QUIZ_MENU_VIEW = "quiz menu";

    public QuickstartPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void showQuizMenu() {
        viewManagerModel.setState(QUIZ_MENU_VIEW);
        viewManagerModel.firePropertyChange();
    }
}
