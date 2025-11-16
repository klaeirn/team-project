package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import entities.Quiz;
import interface_adapter.take_quiz.TakeQuizController;
import data_access.FileUserDataAccessObject;

public class SelectExistingQuizController {
    private final ViewManagerModel viewManagerModel;
    private TakeQuizController takeQuizController;
    private FileUserDataAccessObject userDataAccessObject;

    public SelectExistingQuizController(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void switchToSelectExistingQuiz() {
        viewManagerModel.setState("select existing quiz");
        viewManagerModel.firePropertyChange();
    }

    public void switchToQuizMenu() {
        viewManagerModel.setState("quiz menu");
        viewManagerModel.firePropertyChange();
    }

    public void beginQuiz(Quiz quiz) {
        // If wired, kick off the take-quiz flow; otherwise just navigate
        if (takeQuizController != null) {
            String username = null;
            if (userDataAccessObject != null) {
                username = userDataAccessObject.getCurrentUsername();
            }
            if (username == null || username.isEmpty()) {
                username = "Guest";
            }
            takeQuizController.execute(quiz, username);
        }
        viewManagerModel.setState("take quiz");
        viewManagerModel.firePropertyChange();
    }

    // Wiring methods used by AppBuilder
    public void setTakeQuizController(TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
    }

    public void setUserDataAccessObject(FileUserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }
}

