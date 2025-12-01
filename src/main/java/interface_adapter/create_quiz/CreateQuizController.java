package interface_adapter.create_quiz;

import java.util.List;

import use_cases.create_quiz.CreateQuizInputBoundary;
import use_cases.create_quiz.CreateQuizInputData;

public class CreateQuizController {
    private final CreateQuizInputBoundary createQuizUseCaseInteractor;

    public CreateQuizController(CreateQuizInputBoundary createQuizUseCaseInteractor) {
        this.createQuizUseCaseInteractor = createQuizUseCaseInteractor;
    }

    /**
     * Executes the create quiz use case with the provided quiz data.
     *
     * @param quizName : the name of the quiz
     * @param category : the category of the quiz
     * @param questionsDetails : the list of question details, where each question is a list of strings
     * @param correctAnswers : the list of correct answers for each question
     * @param username : the username of the quiz creator
     */
    public void execute(String quizName, String category, 
                       List<List<String>> questionsDetails, List<String> correctAnswers, String username) {
        final CreateQuizInputData createQuizInputData = new CreateQuizInputData(
                quizName, category, questionsDetails, correctAnswers, username);

        createQuizUseCaseInteractor.execute(createQuizInputData);
    }

    /**
     * Method for switching to the create quiz view for the given user.
     *
     * @param username : the username of the user
     */
    public void switchToCreateQuizView(String username) {
        createQuizUseCaseInteractor.switchToCreateQuizView(username);
    }

    /**
     * Method for switching to the logged in view.
     */
    public void switchToLoggedInView() {
        createQuizUseCaseInteractor.switchToLoggedInView();
    }
}

