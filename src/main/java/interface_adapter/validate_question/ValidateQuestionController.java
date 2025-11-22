package interface_adapter.validate_question;

import use_cases.validate_question.ValidateQuestionInputBoundary;
import use_cases.validate_question.ValidateQuestionInputData;

import java.util.List;

public class ValidateQuestionController {

    private final ValidateQuestionInputBoundary validateQuestionInputBoundary;

    public ValidateQuestionController(ValidateQuestionInputBoundary validateQuestionInputBoundary) {
        this.validateQuestionInputBoundary = validateQuestionInputBoundary;
    }

    public void execute(String title, List<String> options, String answer) {
        final ValidateQuestionInputData validateQuestionInputData = new ValidateQuestionInputData(
                title, options, answer);

        validateQuestionInputBoundary.execute(validateQuestionInputData);
    }


}