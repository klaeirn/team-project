package use_cases.create_quiz;

public interface CreateQuizOutputBoundary {
    
    void prepareSuccessView(CreateQuizOutputData outputData);

    void prepareFailView(String errorMessage);

    void switchToCreateQuizView();

    void switchToLoggedInView();
}

