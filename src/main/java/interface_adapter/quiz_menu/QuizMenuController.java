package interface_adapter.quiz_menu;

import use_cases.quiz_menu.QuizMenuInputBoundary;


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

    public void switchToQuickstart() {
        inputBoundary.switchToQuickstart();
    }
}
