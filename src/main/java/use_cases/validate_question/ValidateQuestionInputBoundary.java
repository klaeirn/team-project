package use_cases.validate_question;

public interface ValidateQuestionInputBoundary {
    /**
     * Method for the validate question input boundary.
     *
     * @param validateQuestionInputData : the input data for validate question ie question titles, options ect.
     */
    void execute(ValidateQuestionInputData validateQuestionInputData);
}

