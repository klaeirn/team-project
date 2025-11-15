package interface_adapter.create_quiz;

import interface_adapter.ViewModel;

public class CreateQuizViewModel extends ViewModel<CreateQuizState> {

    public CreateQuizViewModel() {
        super("create quiz");
        setState(new CreateQuizState());
    }

}

