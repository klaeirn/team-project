package interface_adapter.take_shared_quiz;

public class TakeSharedQuizState {

    private String hash = "";
    private String errormessage = "";

    public String getHash() {
        return hash;
    }

    public String getErrorMessage() {
        return errormessage;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setErrorMessage(String errorMessage) {
        this.errormessage = errorMessage;
    }
}
