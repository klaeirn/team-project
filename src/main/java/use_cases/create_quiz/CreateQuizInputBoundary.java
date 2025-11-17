package use_cases.create_quiz;

public interface CreateQuizInputBoundary {
    void execute(CreateQuizInputData createQuizInputData);

    void switchToCreateQuizView();

    void switchToLoggedInView();
}

