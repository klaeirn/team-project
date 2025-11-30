package use_cases.validate_question;

import java.util.List;

public class ValidateQuestionOutputData {
    private final List<String> questionDetails;
    private final String answer;

    ValidateQuestionOutputData(List<String> questionDetails, String answer) {
        this.questionDetails = questionDetails;
        this.answer = answer;
    }

    public List<String> getQuestionDetails() {
        return questionDetails;
    }

    public String getAnswer() {
        return answer;
    }
}
