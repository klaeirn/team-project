package use_cases.login;

import entities.User;

public class LogInInteractor implements LoginInputBoundary {

    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LogInInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }


    @Override
    public void execute(LoginInputData loginInputData) {

        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView("An account with username " + username + " does not exist.");
        } else {
            final String pwd = userDataAccessObject.get(username).getPassword();

            if (!pwd.equals(password)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            } else {

                final User user = userDataAccessObject.get(loginInputData.getUsername());

                userDataAccessObject.setCurrentUsername(username);

                final LoginOutputData loginOutputData = new LoginOutputData(user.getUserName());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}