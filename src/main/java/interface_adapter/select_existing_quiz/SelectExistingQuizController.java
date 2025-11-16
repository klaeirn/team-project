package interface_adapter.select_existing_quiz;

import interface_adapter.ViewManagerModel;
import entities.Quiz;
import use_cases.select_existing_quiz.SelectExistingQuizInputBoundary;
import use_cases.select_existing_quiz.SelectExistingQuizInputData;

public class SelectExistingQuizController {
    private final ViewManagerModel viewManagerModel;
    private final SelectExistingQuizInputBoundary inputBoundary;

    public SelectExistingQuizController(ViewManagerModel viewManagerModel,
                                        SelectExistingQuizInputBoundary inputBoundary) {
        this.viewManagerModel = viewManagerModel;
        this.inputBoundary = inputBoundary;
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
        // Delegate to the Select Existing Quiz use case; username resolved in interactor
        inputBoundary.execute(new SelectExistingQuizInputData(quiz, null));
    }
}

