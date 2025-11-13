package interface_adapter.change_username;

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


    public ChangeUsernamePresenter(ChangeUsernameViewModel changeUsernameViewModel, ViewManagerModel viewManagerModel) {
        this.changeUsernameViewModel = changeUsernameViewModel;
        this.viewManagerModel = viewManagerModel;
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
}
