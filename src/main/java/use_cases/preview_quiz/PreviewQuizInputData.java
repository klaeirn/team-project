package use_cases.preview_quiz;

import java.util.List;

public class PreviewQuizInputData {

    private final String quizName;
    private final String creatorUsername;

    private final List<List<String>> rawQuestions;
    private final List<String> rawAnswers;
    private final String category;
    private final boolean isPreviewingNewQuiz;

    // Constructor 1: For existing quizzes
    public PreviewQuizInputData(String quizName, String creatorUsername) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
        this.rawQuestions = null;
        this.rawAnswers = null;
        this.category = null;
        this.isPreviewingNewQuiz = false;
    }

    // Constructor 2: For new quizzes
    public PreviewQuizInputData(String quizName, String creatorUsername, String category,
                                List<List<String>> rawQuestions, List<String> rawAnswers) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
        this.category = category;
        this.rawQuestions = rawQuestions;
        this.rawAnswers = rawAnswers;
        this.isPreviewingNewQuiz = true;
    }

    public String getQuizName(){ return quizName;}
    public String getCreatorUsername(){ return creatorUsername;}
    public boolean isPreviewingNewQuiz() { return isPreviewingNewQuiz; }
    public List<List<String>> getRawQuestions() { return rawQuestions; }
    public List<String> getRawAnswers() { return rawAnswers; }
    public String getCategory() { return category; }
}
