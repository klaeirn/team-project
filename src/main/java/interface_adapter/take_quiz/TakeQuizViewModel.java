package interface_adapter.take_quiz;

import interface_adapter.ViewModel;

public class TakeQuizViewModel extends ViewModel<TakeQuizState> {

    public TakeQuizViewModel() {
        super("take quiz");
        setState(new TakeQuizState());
    }
}

