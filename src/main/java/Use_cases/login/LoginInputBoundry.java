package use_cases.login;

public interface LoginInputBoundry {
    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);
}
