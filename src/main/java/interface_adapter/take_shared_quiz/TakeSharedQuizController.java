package interface_adapter.take_shared_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.take_shared_quiz.TakeSharedQuizInputBoundary;
import use_cases.take_shared_quiz.TakeSharedQuizInputData;

public class TakeSharedQuizController {

    private final TakeSharedQuizInputBoundary takeSharedQuizInputBoundary;
    private final ViewManagerModel viewManagerModel;

    public TakeSharedQuizController(TakeSharedQuizInputBoundary
                                            takeSharedQuizInputBoundary,
                                    ViewManagerModel viewManagerModel) {
        this.takeSharedQuizInputBoundary = takeSharedQuizInputBoundary;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute(String hash, String username) {
        final TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData(hash, username);
        takeSharedQuizInputBoundary.execute(inputData);
    }

    public void switchToTakeSharedQuizView() {
        viewManagerModel.setState("take shared quiz");
        viewManagerModel.firePropertyChange();
    }
}
