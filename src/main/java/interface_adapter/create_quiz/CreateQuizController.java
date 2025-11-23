package interface_adapter.create_quiz;

import use_cases.create_quiz.CreateQuizInputBoundary;
import use_cases.create_quiz.CreateQuizInputData;

import java.util.List;

public class CreateQuizController {
    private final CreateQuizInputBoundary createQuizUseCaseInteractor;

    public CreateQuizController(CreateQuizInputBoundary createQuizUseCaseInteractor) {
        this.createQuizUseCaseInteractor = createQuizUseCaseInteractor;
    }

    public void execute(String quizName, String category, 
                       List<List<String>> questionsDetails, List<String> correctAnswers, String username) {
        final CreateQuizInputData createQuizInputData = new CreateQuizInputData(
                quizName, category, questionsDetails, correctAnswers, username);

        createQuizUseCaseInteractor.execute(createQuizInputData);
    }

    public void switchToCreateQuizView(String username) {
        createQuizUseCaseInteractor.switchToCreateQuizView(username);
    }

    public void switchToLoggedInView() {
        createQuizUseCaseInteractor.switchToLoggedInView();
    }
}

