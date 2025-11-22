package interface_adapter.validate_question;

import interface_adapter.ViewModel;

public class ValidateQuestionViewModel extends ViewModel<ValidateQuestionState> {
    public ValidateQuestionViewModel() {
        super("validate question");
        setState(new ValidateQuestionState());
    }
}
