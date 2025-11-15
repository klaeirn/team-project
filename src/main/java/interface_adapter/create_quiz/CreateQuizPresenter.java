package interface_adapter.create_quiz;

import use_cases.create_quiz.CreateQuizOutputBoundary;
import use_cases.create_quiz.CreateQuizOutputData;
import interface_adapter.ViewManagerModel;

public class CreateQuizPresenter implements CreateQuizOutputBoundary {

    private final CreateQuizViewModel createQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateQuizPresenter(CreateQuizViewModel createQuizViewModel, ViewManagerModel viewManagerModel) {
        this.createQuizViewModel = createQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(CreateQuizOutputData outputData) {
        final CreateQuizState state = createQuizViewModel.getState();
        state.setQuizName(outputData.getQuizName());
        state.setCategory(outputData.getCategory());
        this.createQuizViewModel.setState(state);
        this.createQuizViewModel.firePropertyChange();
        
        // rn, this will make it stay on the same view
        // may be changed later so that it goes to the logged in view
    }

    @Override
    public void prepareFailView(String error) {
        final CreateQuizState state = createQuizViewModel.getState();
        state.setCreateError(error);
        createQuizViewModel.firePropertyChange();
    }
}

