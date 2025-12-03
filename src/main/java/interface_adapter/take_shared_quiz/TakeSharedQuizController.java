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

    /**
     * This method handles the user request to take a shared quiz
     * Creates an input data object from the given hash and username
     * and passes it to the use case interactor through the input boundary.
     * @param hash     the shared quiz code entered by the user
     * @param username the username of the current user
     */
    public void execute(String hash, String username) {
        final TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData(hash, username);
        takeSharedQuizInputBoundary.execute(inputData);
    }

    /**
     * Switches the UI to the Take Shared Quiz view
     * Updates the view manager state and fires a property change so
     * that it correctly switches to take shared quiz view.
     */

    public void switchToTakeSharedQuizView() {
        viewManagerModel.setState("take shared quiz");
        viewManagerModel.firePropertyChange();
    }
}
