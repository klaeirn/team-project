package use_cases.quickstart;

public interface QuickstartOutputBoundary {
    void showQuizMenu();
    void prepareSuccessView(QuickstartOutputData outputData);
    void prepareFailView(String error);
}
