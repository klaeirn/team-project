package interface_adapter.quiz_menu;

import interface_adapter.ViewManagerModel;

public class QuizMenuController {
    private final ViewManagerModel viewManagerModel;

    public QuizMenuController(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void switchToQuizMenu() {
        viewManagerModel.setState("quiz menu");
        viewManagerModel.firePropertyChange();
    }

    public void switchToLoggedIn() {
        viewManagerModel.setState("logged in");
        viewManagerModel.firePropertyChange();
    }

    public void switchToQuickstart() {
        viewManagerModel.setState("quickstart");
        viewManagerModel.firePropertyChange();
    }
}
