package use_cases.preview_quiz;

public interface PreviewQuizOutputBoundary {

    /**
     * Called when the quiz preview data is successfully created.
     */
    void prepareSuccessView(PreviewQuizOutputData outputData);

    /**
     * Called when the quiz cannot be found or some other error occurs.
     */
    void prepareFailView(String errorMessage);
}
