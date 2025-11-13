package interface_adapter.logged_in;
import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameInputData;

public class LoggedInController {

    private final ChangeUsernameInputBoundary changeUsernameUsecaseInteractor;

    public LoggedInController(ChangeUsernameInputBoundary changeUsernameInputBoundary) {
        this.changeUsernameUsecaseInteractor = changeUsernameInputBoundary;
    }

    public void execute(String username){
        final ChangeUsernameInputData changeUsernameInputData = new ChangeUsernameInputData(username);

        changeUsernameUsecaseInteractor.execute(changeUsernameInputData);
    }

}
