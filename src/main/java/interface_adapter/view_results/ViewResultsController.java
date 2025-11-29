package interface_adapter.view_results;

import entities.Quiz;
import use_cases.view_results.ViewResultsInputBoundary;
import use_cases.view_results.ViewResultsInputData;

import java.util.Map;

public class ViewResultsController {
    private final ViewResultsInputBoundary viewResultsUseCaseInteractor;

    public ViewResultsController(ViewResultsInputBoundary viewResultsUseCaseInteractor) {
        this.viewResultsUseCaseInteractor = viewResultsUseCaseInteractor;
    }

    public void execute(Quiz quiz, String username, Map<Integer, String> userAnswers) {
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, username, userAnswers);
        viewResultsUseCaseInteractor.execute(inputData);
    }
}

