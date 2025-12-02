package use_cases.take_shared_quiz;

import entities.Quiz;

public class TakeSharedQuizInteractor implements TakeSharedQuizInputBoundary {

    private final TakeSharedQuizDataAccessInterface takeSharedQuizDataAccess;
    private final TakeSharedQuizOutputBoundary takeSharedQuizOutputBoundary;

    public  TakeSharedQuizInteractor(TakeSharedQuizDataAccessInterface
                                             takeSharedQuizDataAccess,
                                     TakeSharedQuizOutputBoundary
                                             takeSharedQuizOutputBoundary) {
        this.takeSharedQuizDataAccess = takeSharedQuizDataAccess;
        this.takeSharedQuizOutputBoundary = takeSharedQuizOutputBoundary;
    }

    @Override
    public void execute(TakeSharedQuizInputData InputData) {
        String hash = InputData.getHash();

        if (hash == null || hash.isBlank()) {
            takeSharedQuizOutputBoundary.prepareFailureView(
                    "Please enter hash");
            return;
        }

        Quiz quiz = takeSharedQuizDataAccess.getFromHash(hash);

        if (quiz == null) {
            takeSharedQuizOutputBoundary.prepareFailureView("Quiz not found");
            return;
        }

        takeSharedQuizOutputBoundary.prepareSuccessView(
                new TakeSharedQuizOutputData(quiz));
    }
}
