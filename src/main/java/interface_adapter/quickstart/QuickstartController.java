package interface_adapter.quickstart;

import use_cases.quickstart.QuickstartInputBoundary;

public class QuickstartController {
    private final QuickstartInputBoundary inputBoundary;

    public QuickstartController(QuickstartInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void backToQuizMenu() {
        inputBoundary.backToQuizMenu();
    }
}
