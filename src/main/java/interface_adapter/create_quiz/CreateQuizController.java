package interface_adapter.create_quiz;

import use_cases.create_quiz.CreateQuizInputBoundary;
import use_cases.create_quiz.CreateQuizInputData;
import interface_adapter.ViewManagerModel;

import java.util.List;

public class CreateQuizController {
    private final CreateQuizInputBoundary createQuizUseCaseInteractor;
    private final CreateQuizViewModel createQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateQuizController(CreateQuizInputBoundary createQuizUseCaseInteractor,
                                CreateQuizViewModel createQuizViewModel,
                                ViewManagerModel viewManagerModel) {
        this.createQuizUseCaseInteractor = createQuizUseCaseInteractor;
        this.createQuizViewModel = createQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void execute(String quizName, String category, 
                       List<List<String>> questionsDetails, List<String> correctAnswers, String username) {
        final CreateQuizInputData createQuizInputData = new CreateQuizInputData(
                quizName, category, questionsDetails, correctAnswers, username);

        createQuizUseCaseInteractor.execute(createQuizInputData);
    }

    /**
     * Switches to the Create Quiz view and passes the current user's name.
     * @param username The username of the logged-in user.
     */
    public void switchToCreateQuizView(String username) {
        CreateQuizState state = createQuizViewModel.getState();
        state.setUsername(username);
        state.setQuizName("");
        state.setCategory("");
        createQuizViewModel.setState(state);
        createQuizViewModel.firePropertyChange();
        viewManagerModel.setState(createQuizViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    public void switchToLoggedInView() {
        createQuizUseCaseInteractor.switchToLoggedInView();
    }
}

