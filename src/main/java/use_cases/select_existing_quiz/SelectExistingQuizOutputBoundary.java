package use_cases.select_existing_quiz;

public interface SelectExistingQuizOutputBoundary {
    void prepareSuccessView(SelectExistingQuizOutputData outputData);
    void prepareFailView(String message);
}
