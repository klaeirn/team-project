package interface_adapter.take_shared_quiz;

import use_cases.take_shared_quiz.TakeSharedQuizInputBoundary;
import use_cases.take_shared_quiz.TakeSharedQuizInputData;

public class TakeSharedQuizController {
    private final TakeSharedQuizInputBoundary takeSharedQuizInputBoundary;

    public TakeSharedQuizController(TakeSharedQuizInputBoundary takeSharedQuizInputBoundary){
        this.takeSharedQuizInputBoundary = takeSharedQuizInputBoundary;
    }

    public void execute(String hash){
        TakeSharedQuizInputData inputData = new TakeSharedQuizInputData(hash);
        takeSharedQuizInputBoundary.execute(inputData);
    }
}
