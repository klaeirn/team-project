package use_cases.view_results;

import entities.Question;
import entities.Quiz;
import entities.QuizResult;

import java.util.List;
import java.util.Map;

public class ViewResultsInteractor implements ViewResultsInputBoundary {
    private final ViewResultsOutputBoundary presenter;
    private final ViewResultsDataAccessInterface dataAccessObject;

    public ViewResultsInteractor(ViewResultsOutputBoundary presenter,
                                 ViewResultsDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(ViewResultsInputData inputData) {
        String quizName = inputData.getQuizName();
        String creatorUsername = inputData.getCreatorUsername();
        String username = inputData.getUsername();
        Map<Integer, String> userAnswers = inputData.getUserAnswers();

        // Fetch quiz from data access layer
        Quiz quiz = dataAccessObject.getQuiz(quizName, creatorUsername);

        if (quiz == null) {
            presenter.prepareFailView("Quiz not found.");
            return;
        }

        List<Question> questions = quiz.getQuestions();
        if (questions == null || questions.isEmpty()) {
            presenter.prepareFailView("Quiz has no questions.");
            return;
        }

        if (userAnswers == null) {
            userAnswers = new java.util.HashMap<>();
        }

        // Calc score
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers.get(i);

            if (userAnswer != null && question.validate_answer(userAnswer)) {
                score++;
            }
        }

        // Create QuizResult
        QuizResult quizResult = new QuizResult(username, score, questions.size());

        // Create output data
        ViewResultsOutputData outputData = new ViewResultsOutputData(
                quizResult,
                quiz.getName(),
                null
        );

        presenter.prepareSuccessView(outputData);
    }
}

