package interface_adapter.change_username;

public class ChangeUsernameState {

    private String newUsername = "";
    private String changeError;

    public String getNewUsername() {
        return newUsername;
    }

    public String getLoginError() {
        return changeError;
    }

    public void setNewUsername(String username) {
        this.newUsername = username;
    }

    public void setChangeError(String changeError) {
        this.changeError = changeError;
    }
}
