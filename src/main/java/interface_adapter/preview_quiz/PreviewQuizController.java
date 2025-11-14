package interface_adapter.preview_quiz;

import use_cases.preview_quiz.PreviewQuizInputBoundary;
import use_cases.preview_quiz.PreviewQuizInputData;

public class PreviewQuizController {

    private final PreviewQuizInputBoundary previewQuizInteractor;

    public PreviewQuizController(PreviewQuizInputBoundary previewQuizInteractor) {
        this.previewQuizInteractor = previewQuizInteractor;
    }

    /**
     * Executes the Preview Quiz Use Case.
     * @param quizName        name of the quiz for preview
     * @param creatorUsername username of the quiz creator
     */
    public void execute(String quizName, String creatorUsername) {
        PreviewQuizInputData inputData = new PreviewQuizInputData(quizName, creatorUsername);
        previewQuizInteractor.execute(inputData);
    }
}
