package interface_adapter.take_shared_quiz;

public class TakeSharedQuizState {

    private String hash = "";
    private String errormessage = "";
    private String username = "";

    public String getHash() {
        return hash;
    }

    public String getUsername() {
        return username;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public String getErrorMessage() {
        return errormessage;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setErrorMessage(String errorMessage) {
        this.errormessage = errorMessage;
    }
}
