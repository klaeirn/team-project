package use_cases.change_username;


public interface ChangeUsernameOutputBoundary {

    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChangeUsernameOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param error the explanation of the failure
     */
    void prepareFailView(String error);

    void switchToChangeUsernameView();
}
