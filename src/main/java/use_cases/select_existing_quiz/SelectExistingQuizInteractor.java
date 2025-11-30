package use_cases.select_existing_quiz;

import entities.Question;
import entities.Quiz;
import use_cases.take_quiz.TakeQuizInputBoundary;
import use_cases.take_quiz.TakeQuizInputData;

import java.util.List;

public class SelectExistingQuizInteractor implements SelectExistingQuizInputBoundary {

    private final SelectExistingQuizDataAccessInterface currentUserProvider;
    private final SelectExistingQuizOutputBoundary presenter;
    private final TakeQuizInputBoundary takeQuizInteractor;

    public SelectExistingQuizInteractor(SelectExistingQuizDataAccessInterface currentUserProvider,
                                        SelectExistingQuizOutputBoundary presenter,
                                        TakeQuizInputBoundary takeQuizInteractor) {
        this.currentUserProvider = currentUserProvider;
        this.presenter = presenter;
        this.takeQuizInteractor = takeQuizInteractor;
    }

    @Override
    public void execute(SelectExistingQuizInputData inputData) {
        if (inputData == null || inputData.getQuiz() == null) {
            presenter.prepareFailView("Please select a quiz.");
            return;
        }

        Quiz quiz = inputData.getQuiz();

        if (!isValidQuiz(quiz)) {
            presenter.prepareFailView("Selected quiz is invalid or empty.");
            return;
        }

        String username = inputData.getUsername();
        if (username == null) {
            username = currentUserProvider.getCurrentUsername();
        }

        presenter.prepareSuccessView(new SelectExistingQuizOutputData(quiz, username));

        // Start the take quiz flow by calling the TakeQuiz use case
        if (takeQuizInteractor != null) {
            TakeQuizInputData takeQuizInputData = new TakeQuizInputData(quiz, username);
            takeQuizInteractor.execute(takeQuizInputData);
        }
    }

    private boolean isValidQuiz(Quiz quiz) {
        if (quiz == null) return false;
        List<Question> qs = quiz.getQuestions();
        if (qs == null || qs.isEmpty()) return false;
        for (int i = 0; i < qs.size(); i++) {
            Question q = qs.get(i);
            if (q == null) return false;
            if (q.getTitle() == null || q.getTitle().isEmpty()) return false;
            if (q.getAnswer() == null || q.getAnswer().isEmpty()) return false;
            List<String> options = q.getOptions();
            if (options == null || options.isEmpty()) return false;
            if (!options.contains(q.getAnswer())) return false;
        }
        return true;
    }
}
