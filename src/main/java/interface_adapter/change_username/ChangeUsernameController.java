package interface_adapter.change_username;
import interface_adapter.ViewManagerModel;
import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameInputData;

public class ChangeUsernameController {
    private final ChangeUsernameInputBoundary changeUsernameUsecaseInteractor;

    public ChangeUsernameController(ChangeUsernameInputBoundary changeUsernameInputBoundary) {
        this.changeUsernameUsecaseInteractor = changeUsernameInputBoundary;
    }

    public void execute(String newUsername){
        final ChangeUsernameInputData changeUsernameInputData = new ChangeUsernameInputData(newUsername);

        changeUsernameUsecaseInteractor.execute(changeUsernameInputData);
    }

    public void switchToChangeUsernameView() {
        changeUsernameUsecaseInteractor.switchToChangeUsernameView();
    }

    public void switchToLoggedInView() {
        changeUsernameUsecaseInteractor.switchToLoggedInView();
    }
}
