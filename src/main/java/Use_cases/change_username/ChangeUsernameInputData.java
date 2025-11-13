package use_cases.change_username;

public class ChangeUsernameInputData {

    private String new_username;


    public ChangeUsernameInputData(String new_username) {
        this.new_username = new_username;
    }

    public String getNewUsername() {
        return new_username;
    }

    public void setNewUsername(String new_username) {
        this.new_username = new_username;
    }
}
