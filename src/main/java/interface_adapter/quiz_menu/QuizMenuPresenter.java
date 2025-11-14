package interface_adapter.quiz_menu;

import interface_adapter.ViewManagerModel;
import use_cases.quiz_menu.QuizMenuOutputBoundary;


public class QuizMenuPresenter implements QuizMenuOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final String QUIZ_MENU_VIEW_NAME = "quiz menu";
    private final String LOGGED_IN_VIEW_NAME = "logged in";
    private final String QUICKSTART_VIEW_NAME = "quickstart";

    public QuizMenuPresenter(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void showQuizMenu() {
        viewManagerModel.setState(QUIZ_MENU_VIEW_NAME);
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void showLoggedIn() {
        viewManagerModel.setState(LOGGED_IN_VIEW_NAME);
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void showQuickstart() {
        viewManagerModel.setState(QUICKSTART_VIEW_NAME);
        viewManagerModel.firePropertyChange();
    }
}
