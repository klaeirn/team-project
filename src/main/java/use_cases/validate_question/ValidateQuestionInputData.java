package use_cases.validate_question;

import java.util.List;

public class ValidateQuestionInputData {
    private final String title;
    private final List<String> options;
    private final String answer;

    public ValidateQuestionInputData(String title, List<String> questions, String answer) {
        this.title = title;
        this.options = questions;
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }
}
