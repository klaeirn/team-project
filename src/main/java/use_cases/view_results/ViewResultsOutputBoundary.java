package use_cases.view_results;

public interface ViewResultsOutputBoundary {
    void prepareSuccessView(ViewResultsOutputData outputData);
    void prepareFailView(String error);
}


