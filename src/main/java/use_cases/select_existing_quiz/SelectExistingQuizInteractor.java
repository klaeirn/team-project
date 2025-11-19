package use_cases.select_existing_quiz;

import entities.Question;
import entities.Quiz;

import java.util.List;

public class SelectExistingQuizInteractor implements SelectExistingQuizInputBoundary {

    private final SelectExistingQuizOutputBoundary presenter;

    public SelectExistingQuizInteractor(SelectExistingQuizOutputBoundary presenter) {
        this.presenter = presenter;
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
        if (username == null || username.isEmpty()) {
            presenter.prepareFailView("No username available. Please log in again.");
            return;
        }

        presenter.prepareSuccessView(new SelectExistingQuizOutputData(quiz, username));
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
