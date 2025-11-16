package use_cases.take_quiz;

public interface TakeQuizOutputBoundary {
    void prepareQuestionView(TakeQuizOutputData outputData);
    void prepareFailView(String error);
}

