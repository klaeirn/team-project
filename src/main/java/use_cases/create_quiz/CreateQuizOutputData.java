package use_cases.create_quiz;

public class CreateQuizOutputData {
    private final String quizName;
    private final String creatorUsername;
    private final String category;
    private final int questionCount;

    public CreateQuizOutputData(String quizName, String creatorUsername, String category, int questionCount) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
        this.category = category;
        this.questionCount = questionCount;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getCategory() {
        return category;
    }

    public int getQuestionCount() {
        return questionCount;
    }
}

