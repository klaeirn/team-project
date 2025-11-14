package interface_adapter.quiz_menu;

import interface_adapter.ViewModel;

public class QuizMenuViewModel extends ViewModel<QuizMenuState> {

    public QuizMenuViewModel() {
        super("quiz menu");
        setState(new QuizMenuState());
    }
}
