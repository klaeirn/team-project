package interface_adapter.login;

import use_cases.login.LoginInputBoundry;
import use_cases.login.LoginInputData;

public class LoginController {

    private final LoginInputBoundry loginUseCaseInteractor;

    public LoginController(LoginInputBoundry loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(
                username, password);

        loginUseCaseInteractor.execute(loginInputData);
    }

    
}
