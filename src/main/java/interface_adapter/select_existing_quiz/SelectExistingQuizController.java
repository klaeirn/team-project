package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import entities.Quiz;

public class SelectExistingQuizController {
    private final ViewManagerModel viewManagerModel;

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
        // TODO: When use case is created, call the use case interactor here
        // This will start the selected quiz
        // For now, this is a placeholder that can be extended
    }
}

