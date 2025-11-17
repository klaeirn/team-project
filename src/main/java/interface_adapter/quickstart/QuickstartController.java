package interface_adapter.quickstart;

import use_cases.quickstart.QuickstartInputBoundary;
import use_cases.quickstart.QuickstartInputData;

public class QuickstartController {
    private final QuickstartInputBoundary inputBoundary;

    public QuickstartController(QuickstartInputBoundary inputBoundary) {
        this.inputBoundary = inputBoundary;
    }

    public void backToQuizMenu() {
        inputBoundary.backToQuizMenu();
    }

    public void execute(String category, String difficulty, String type) {
        QuickstartInputData inputData = new QuickstartInputData(category, difficulty, type);
        inputBoundary.execute(inputData);
    }

    public QuickstartInputBoundary getInputBoundary() {
        return inputBoundary;
    }
}
