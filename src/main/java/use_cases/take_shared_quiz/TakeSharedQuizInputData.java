package use_cases.take_shared_quiz;


public class TakeSharedQuizInputData {

    private final String hash;

    public TakeSharedQuizInputData(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}