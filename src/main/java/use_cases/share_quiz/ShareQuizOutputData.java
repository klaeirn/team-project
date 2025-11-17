package use_cases.share_quiz;

public class ShareQuizOutputData {

    private final String hash;

    public ShareQuizOutputData(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
