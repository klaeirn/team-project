package interface_adapter.change_username;

import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.logged_in.LoggedInViewModel;
import use_cases.change_username.ChangeUsernameInputBoundary;
import use_cases.change_username.ChangeUsernameOutputBoundary;
import interface_adapter.ViewManagerModel;
import use_cases.change_username.ChangeUsernameOutputData;
import view.ChangeUsernameView;
import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.change_username.ChangeUsernameState;

public class ChangeUsernamePresenter implements ChangeUsernameOutputBoundary{

    private final ChangeUsernameViewModel changeUsernameViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;


    public ChangeUsernamePresenter(ChangeUsernameViewModel changeUsernameViewModel, ViewManagerModel viewManagerModel,
                                   LoggedInViewModel loggedInViewModel) {
        this.changeUsernameViewModel = changeUsernameViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void prepareSuccessView(ChangeUsernameOutputData outputData) {

        final ChangeUsernameState state = this.changeUsernameViewModel.getState();
        state.setNewUsername(outputData.getUsername());
        this.changeUsernameViewModel.setState(state);
        this.changeUsernameViewModel.firePropertyChange();

//        changeUsernameViewModel.setState(state);
        this.viewManagerModel.setState(changeUsernameViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();

    }

    @Override
    public void prepareFailView(String error) {

    }

    @Override
    public void switchToChangeUsernameView() {
        viewManagerModel.setState(changeUsernameViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void switchToLoggedInView() {
        this.changeUsernameViewModel.setState(new ChangeUsernameState());
        this.changeUsernameViewModel.firePropertyChange();

        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
