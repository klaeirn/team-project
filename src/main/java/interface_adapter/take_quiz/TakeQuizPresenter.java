package interface_adapter.take_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.take_quiz.TakeQuizOutputBoundary;
import use_cases.take_quiz.TakeQuizOutputData;

public class TakeQuizPresenter implements TakeQuizOutputBoundary {
    private final TakeQuizViewModel takeQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public TakeQuizPresenter(TakeQuizViewModel takeQuizViewModel, ViewManagerModel viewManagerModel) {
        this.takeQuizViewModel = takeQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareQuestionView(TakeQuizOutputData outputData) {
        TakeQuizState state = takeQuizViewModel.getState();
        state.setCurrentQuestion(outputData.getCurrentQuestion());
        state.setCurrentQuestionIndex(outputData.getCurrentQuestionIndex());
        state.setTotalQuestions(outputData.getTotalQuestions());
        state.setUserAnswers(outputData.getUserAnswers());
        state.setLastQuestion(outputData.isLastQuestion());
        state.setScore(null);
        state.setErrorMessage(null);

        // Store quiz and username if not already set (first question)
        if (state.getQuiz() == null && outputData.getQuiz() != null) {
            state.setQuiz(outputData.getQuiz());
        }
        if (state.getUsername() == null && outputData.getUsername() != null) {
            state.setUsername(outputData.getUsername());
        }

        String currentAnswer = outputData.getUserAnswers().get(outputData.getCurrentQuestionIndex() - 1);
        state.setSelectedAnswer(currentAnswer);

        takeQuizViewModel.setState(state);
        takeQuizViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        TakeQuizState state = takeQuizViewModel.getState();
        state.setErrorMessage(error);
        takeQuizViewModel.setState(state);
        takeQuizViewModel.firePropertyChange();
    }
}

