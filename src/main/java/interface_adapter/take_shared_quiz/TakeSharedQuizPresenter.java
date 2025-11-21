package interface_adapter.take_shared_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.take_shared_quiz.TakeSharedQuizOutputBoundary;
import use_cases.take_shared_quiz.TakeSharedQuizOutputData;

public class TakeSharedQuizPresenter implements TakeSharedQuizOutputBoundary {
    private final TakeSharedQuizViewModel takeSharedQuizViewModel;
    private final ViewManagerModel viewManagerModel;
}
