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
        CreateQuizState state = new CreateQuizState();
        String successMessage = String.format(
                "Quiz '%s' (%s) with %d questions saved successfully!",
                outputData.getQuizName(),
                outputData.getCategory(),
                outputData.getQuestionCount()
        );
        state.setSuccessMessage(successMessage);
        this.createQuizViewModel.setState(state);
        this.createQuizViewModel.firePropertyChange();

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
    public void switchToCreateQuizView(String username) {
        CreateQuizState state = createQuizViewModel.getState();
        state.setUsername(username);
        state.setQuizName("");
        state.setCategory("");

        createQuizViewModel.setState(state);
        createQuizViewModel.firePropertyChange();

        viewManagerModel.setState(createQuizViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void switchToLoggedInView() {
        this.createQuizViewModel.setState(new CreateQuizState());
        this.createQuizViewModel.firePropertyChange();

        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}

