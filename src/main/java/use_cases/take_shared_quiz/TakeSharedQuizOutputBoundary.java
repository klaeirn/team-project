package use_cases.take_shared_quiz;

public interface TakeSharedQuizOutputBoundary {

    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */

    void prepareSuccessView(TakeSharedQuizOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param message the explanation of the failure
     */

    void prepareFailureView(String message);

}
