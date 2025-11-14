package use_cases.quickstart;

public class QuickstartInteractor implements QuickstartInputBoundary {

    private final QuickstartOutputBoundary presenter;

    public QuickstartInteractor(QuickstartOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void backToQuizMenu() {
        presenter.showQuizMenu();
    }
}
