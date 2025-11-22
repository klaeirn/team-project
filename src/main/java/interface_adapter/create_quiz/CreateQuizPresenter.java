package interface_adapter.create_quiz;

import use_cases.create_quiz.CreateQuizOutputBoundary;
import use_cases.create_quiz.CreateQuizOutputData;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;

public class CreateQuizPresenter implements CreateQuizOutputBoundary {

    private final CreateQuizViewModel createQuizViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public CreateQuizPresenter(CreateQuizViewModel createQuizViewModel, ViewManagerModel viewManagerModel) {
        this.createQuizViewModel = createQuizViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = new LoggedInViewModel();
    }

    @Override
    public void prepareSuccessView(CreateQuizOutputData outputData) {
        this.viewManagerModel.setState(loggedInViewModel.getViewName());
        this.viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final CreateQuizState state = createQuizViewModel.getState();
        state.setCreateError(error);
        createQuizViewModel.firePropertyChange();
    }

    @Override
    public void switchToCreateQuizView() {
        viewManagerModel.setState(createQuizViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void switchToLoggedInView() {
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}

