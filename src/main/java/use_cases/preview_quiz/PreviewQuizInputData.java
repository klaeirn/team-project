package use_cases.preview_quiz;

public class PreviewQuizInputData {

    private final String quizName;
    private final String creatorUsername;

    public PreviewQuizInputData(String quizName, String creatorUsername) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
    }

    public String getQuizName(){ return quizName;}
    public String getCreatorUsername(){ return creatorUsername;}
}
