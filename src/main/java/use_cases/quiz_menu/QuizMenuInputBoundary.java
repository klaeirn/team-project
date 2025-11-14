package use_cases.quiz_menu;

/**
 * Input boundary for the Quiz Menu use case. Provides navigation actions
 * related to the quiz menu stage.
 */
public interface QuizMenuInputBoundary {
    void switchToQuizMenu();
    void switchToLoggedIn();
}
