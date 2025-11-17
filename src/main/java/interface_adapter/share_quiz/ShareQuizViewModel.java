package interface_adapter.share_quiz;
import interface_adapter.ViewModel;

import interface_adapter.share_quiz.ShareQuizState;

public class ShareQuizViewModel extends ViewModel<ShareQuizState> {
    public ShareQuizViewModel() {
        super("share quiz");
        setState(new ShareQuizState());
    }
}
