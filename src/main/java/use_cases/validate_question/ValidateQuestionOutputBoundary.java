package use_cases.validate_question;

public interface ValidateQuestionOutputBoundary {
    /**
     * Prepares the success view with the validated question output data.
     *
     * @param outputData : the output data containing validated question information
     */
    void prepareSuccessView(ValidateQuestionOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param errorMessage : the error message to display
     */
    void prepareFailView(String errorMessage);
}
