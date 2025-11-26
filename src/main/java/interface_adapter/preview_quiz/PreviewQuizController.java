package interface_adapter.preview_quiz;

import use_cases.preview_quiz.PreviewQuizInputBoundary;
import use_cases.preview_quiz.PreviewQuizInputData;

import java.util.List;

public class PreviewQuizController {

    private final PreviewQuizInputBoundary previewQuizInteractor;

    public PreviewQuizController(PreviewQuizInputBoundary previewQuizInteractor) {
        this.previewQuizInteractor = previewQuizInteractor;
    }

    /**
     * Executes the Preview Quiz Use Case.
     *
     * @param quizName        name of the quiz for preview
     * @param creatorUsername username of the quiz creator
     */
    // Scenario 1: For existing quizzes
    public void execute(String quizName, String creatorUsername) {
        PreviewQuizInputData inputData = new PreviewQuizInputData(quizName, creatorUsername);
        previewQuizInteractor.execute(inputData);
    }

    // Scenario 2: For new quizzes
    public void execute(String quizName, String creatorUsername, String category,
                        List<List<String>> rawQuestions, List<String> rawAnswers) {
        PreviewQuizInputData inputData = new PreviewQuizInputData(quizName, creatorUsername,
                category, rawQuestions, rawAnswers);
        previewQuizInteractor.execute(inputData);
    }
}
