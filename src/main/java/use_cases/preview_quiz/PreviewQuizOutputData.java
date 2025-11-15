package use_cases.preview_quiz;

import java.util.List;

public class PreviewQuizOutputData {

    private final List<QuestionData> questions;

    public PreviewQuizOutputData(List<QuestionData> questions) {
        this.questions = questions;
    }

    public List<QuestionData> getQuestions() { return questions; }


    // Information of a single question.
    // Keep title, options and answer together.
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
