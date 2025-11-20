package use_cases.take_shared_quiz;

public interface TakeSharedQuizOutputBoundary {
    void prepareSuccessView(TakeSharedQuizOutputData outputData);
    void prepareFailureView(String message);
}
