package interface_adapter.preview_quiz;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

public class PreviewQuizViewModel extends ViewModel<PreviewQuizState> {

    public PreviewQuizViewModel() {
        super("preview quiz");
        setState(new PreviewQuizState());
    }
}
