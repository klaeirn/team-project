package interface_adapter.validate_question;

import java.util.List;

import use_cases.validate_question.ValidateQuestionInputBoundary;
import use_cases.validate_question.ValidateQuestionInputData;

public class ValidateQuestionController {

    private final ValidateQuestionInputBoundary validateQuestionInputBoundary;

    public ValidateQuestionController(ValidateQuestionInputBoundary validateQuestionInputBoundary) {
        this.validateQuestionInputBoundary = validateQuestionInputBoundary;
    }

    /**
     * Executes the validate question case with the provided question data.
     *
     * @param title : the name of the question
     * @param options : the options of the question
     * @param answer : the correct answers for a question
     */
    public void execute(String title, List<String> options, String answer) {
        final ValidateQuestionInputData validateQuestionInputData = new ValidateQuestionInputData(
                title, options, answer);

        validateQuestionInputBoundary.execute(validateQuestionInputData);
    }

}
