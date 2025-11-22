package use_cases.validate_question;

public interface ValidateQuestionOutputBoundary {
    void prepareSuccessView(ValidateQuestionOutputData outputData);
    void prepareFailView(String errorMessage);
}
