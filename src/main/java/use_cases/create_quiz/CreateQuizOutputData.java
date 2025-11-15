package use_cases.create_quiz;

public class CreateQuizOutputData {
    private final String quizName;
    private final String category;
    private final String creatorUsername;

    public CreateQuizOutputData(String quizName, String category, String creatorUsername) {
        this.quizName = quizName;
        this.category = category;
        this.creatorUsername = creatorUsername;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCategory() {
        return category;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }
}

