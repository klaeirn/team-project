package use_cases.change_username;

public class ChangeUsernameOutputData {

    private final String new_username;

    public ChangeUsernameOutputData(String username) {
        this.new_username = username;
    }

    public String getUsername() {
        return new_username;
    }

}
