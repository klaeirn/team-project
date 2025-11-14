package use_cases.quiz_menu;

public class QuizMenuInteractor implements QuizMenuInputBoundary {

    private final QuizMenuOutputBoundary presenter;

    public QuizMenuInteractor(QuizMenuOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void switchToQuizMenu() {
        presenter.showQuizMenu();
    }

    @Override
    public void switchToLoggedIn() {
        presenter.showLoggedIn();
    }
}
