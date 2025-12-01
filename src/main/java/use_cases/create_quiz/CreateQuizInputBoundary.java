package use_cases.create_quiz;

public interface CreateQuizInputBoundary {
    /**
     * Prepares the success view with the validated question output data.
     *
     * @param createQuizInputData : the output data containing create quiz question info
     */
    void execute(CreateQuizInputData createQuizInputData);

    /**
     * Method for switching to the create quiz view.
     *
     * @param username : the user's username
     */
    void switchToCreateQuizView(String username);

    /**
     * Method for switching to the login view.
     *
     */
    void switchToLoggedInView();
}

