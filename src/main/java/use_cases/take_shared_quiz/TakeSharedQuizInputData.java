package use_cases.take_shared_quiz;

public class TakeSharedQuizInputData {

    private final String hash;
    private final String username;

    public TakeSharedQuizInputData(String hash, String username) {
        this.hash = hash;
        this.username = username;
    }

    public String getHash() {
        return hash;
    }

    public String getUsername() {
        return username;
    }
}
