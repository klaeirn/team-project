package use_cases.create_quiz;

public interface CreateQuizOutputBoundary {
    /**
     * Method for preparing a success view for Create Quiz.
     *
     * @param outputData : the object that includes Quiz titles, the list of question details ect.
     */
    void prepareSuccessView(CreateQuizOutputData outputData);

    /**
     * Prepares the failure view with an error message.
     *
     * @param errorMessage : the error message to display
     */
    void prepareFailView(String errorMessage);

    /**
     * Method for switching to the create quiz view.
     *
     * @param username : the user's username
     */
    void switchToCreateQuizView(String username);

    /**
     * Method for switching to the logged in view.
     *
     */
    void switchToLoggedInView();
}

