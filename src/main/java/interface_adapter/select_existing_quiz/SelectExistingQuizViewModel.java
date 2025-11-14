package interface_adapter.select_existing_quiz;

import interface_adapter.ViewModel;

public class SelectExistingQuizViewModel extends ViewModel<SelectExistingQuizState> {

    public SelectExistingQuizViewModel() {
        super("select existing quiz");
        setState(new SelectExistingQuizState());
    }
}

