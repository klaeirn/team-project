package interface_adapter.view_results;

import interface_adapter.ViewManagerModel;
import use_cases.view_results.ViewResultsOutputBoundary;
import use_cases.view_results.ViewResultsOutputData;

public class ViewResultsPresenter implements ViewResultsOutputBoundary {
    private final ViewResultsViewModel viewResultsViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewResultsPresenter(ViewResultsViewModel viewResultsViewModel, ViewManagerModel viewManagerModel) {
        this.viewResultsViewModel = viewResultsViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(ViewResultsOutputData outputData) {
        ViewResultsState state = viewResultsViewModel.getState();
        state.setQuizResult(outputData.getQuizResult());
        state.setQuizName(outputData.getQuizName());
        state.setCreatorUsername(outputData.getCreatorUsername());
        state.setErrorMessage(null);

        viewResultsViewModel.setState(state);
        viewResultsViewModel.firePropertyChange();

        // Navigate to the view results view
        viewManagerModel.setState("view results");
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        ViewResultsState state = viewResultsViewModel.getState();
        state.setErrorMessage(error);
        viewResultsViewModel.setState(state);
        viewResultsViewModel.firePropertyChange();
    }
}

