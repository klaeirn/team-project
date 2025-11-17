package interface_adapter.logged_in;
import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameInputData;

public class LoggedInController {

    public LoggedInController() {
        return;
    }

    public void switchToLoggedInView(){
        System.out.println("Switching to logged in");
    }


}
