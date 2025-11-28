package interface_adapter.take_shared_quiz;

import interface_adapter.ViewModel;

public class TakeSharedQuizViewModel extends ViewModel<TakeSharedQuizState> {

    public TakeSharedQuizViewModel() {
        super("take shared quiz");
        setState(new TakeSharedQuizState());
    }
}

