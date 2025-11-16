package interface_adapter.take_quiz;

import entities.Quiz;
import use_cases.take_quiz.TakeQuizInputBoundary;
import use_cases.take_quiz.TakeQuizInputData;

public class TakeQuizController {
    private final TakeQuizInputBoundary takeQuizUseCaseInteractor;

    public TakeQuizController(TakeQuizInputBoundary takeQuizUseCaseInteractor) {
        this.takeQuizUseCaseInteractor = takeQuizUseCaseInteractor;
    }

    public void execute(Quiz quiz, String username) {
        TakeQuizInputData inputData = new TakeQuizInputData(quiz, username);
        takeQuizUseCaseInteractor.execute(inputData);
    }

    public void nextQuestion() {
        takeQuizUseCaseInteractor.nextQuestion();
    }

    public void previousQuestion() {
        takeQuizUseCaseInteractor.previousQuestion();
    }

    public void setAnswer(int questionIndex, String answer) {
        takeQuizUseCaseInteractor.setAnswer(questionIndex, answer);
    }
}

