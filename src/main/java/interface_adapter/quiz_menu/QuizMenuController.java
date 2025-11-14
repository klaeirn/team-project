package interface_adapter.quiz_menu;

import use_cases.quiz_menu.QuizMenuInputBoundary;

/**
 * Controller delegating quiz menu navigation actions to the use case interactor.
 */
public class QuizMenuController {
    private final QuizMenuInputBoundary inputBoundary;

    public QuizMenuController(QuizMenuInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void switchToQuizMenu() {
        inputBoundary.switchToQuizMenu();
    }

    public void switchToLoggedIn() {
        inputBoundary.switchToLoggedIn();
    }
}
