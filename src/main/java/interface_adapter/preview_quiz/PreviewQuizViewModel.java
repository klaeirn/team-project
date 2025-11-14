package interface_adapter.preview_quiz;

import interface_adapter.ViewModel;

public class PreviewQuizViewModel extends ViewModel<PreviewQuizState> {

    public PreviewQuizViewModel() {
        super("preview quiz");
        setState(new PreviewQuizState());
    }
}
