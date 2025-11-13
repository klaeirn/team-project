package interface_adapter.change_username;

import interface_adapter.ViewModel;

public class ChangeUsernameViewModel extends ViewModel<ChangeUsernameState> {

    public ChangeUsernameViewModel() {
        super("change username");
        setState(new ChangeUsernameState());
    }

}
