package use_cases.change_username;

public interface ChangeUsernameInputBoundary {

    /**
     * Executes the login use case.
     * @param changeUsernameInputBoundary the input data
     */
    void execute(ChangeUsernameInputData changeUsernameInputBoundary);

    void switchToChangeUsernameView();
}
