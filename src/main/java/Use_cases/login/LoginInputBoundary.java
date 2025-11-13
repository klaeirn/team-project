package use_cases.login;

public interface LoginInputBoundary {
    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);
}
