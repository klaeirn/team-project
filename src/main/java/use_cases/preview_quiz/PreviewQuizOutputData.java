package use_cases.preview_quiz;

import java.util.List;

public class PreviewQuizOutputData {

    private final String quizName;
    private final String creatorUsername;
    private final String category;

    private final List<QuestionData> questions;

    public PreviewQuizOutputData(String quizName,
                                 String creatorUsername,
                                 String category,
                                 List<QuestionData> questions) {
        this.quizName = quizName;
        this.creatorUsername = creatorUsername;
        this.category = category;
        this.questions = questions;
    }

    public String getQuizName() { return quizName; }
    public String getCreatorUsername() { return creatorUsername; }
    public String getCategory() { return category; }
    public List<QuestionData> getQuestions() { return questions; }


    /// Information of a single question's information.
    /// Keep title, options and answer together.
    public static class QuestionData {
        private final String title;
        private final List<String> options;
        private final String answer;

        public QuestionData(String title, List<String> options, String answer) {
            this.title = title;
            this.options = options;
            this.answer = answer;
        }

        public String getTitle() { return title; }
        public List<String> getOptions() { return options; }
        public String getAnswer() { return answer; }
    }
}
