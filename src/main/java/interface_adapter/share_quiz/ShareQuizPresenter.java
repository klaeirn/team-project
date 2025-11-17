package interface_adapter.share_quiz;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_username.ChangeUsernameState;
import interface_adapter.change_username.ChangeUsernameViewModel;
import use_cases.change_username.ChangeUsernameOutputBoundary;
import use_cases.change_username.ChangeUsernameOutputData;
import use_cases.share_quiz.ShareQuizOutputBoundary;
import use_cases.share_quiz.ShareQuizOutputData;

public class ShareQuizPresenter implements ShareQuizOutputBoundary {
    private final ShareQuizViewModel shareQuizViewModel;
    private final ViewManagerModel viewManagerModel;


    public ShareQuizPresenter(ShareQuizViewModel shareQuizViewModel, ViewManagerModel viewManagerModel) {
        this.shareQuizViewModel = shareQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ShareQuizOutputData outputData) {

    }

    @Override
    public void prepareFailView(String error) {

    }
}
