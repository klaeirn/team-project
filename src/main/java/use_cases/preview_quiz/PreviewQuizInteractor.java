package use_cases.preview_quiz;

import entities.Quiz;
import entities.Question;

import java.util.ArrayList;
import java.util.List;

public class PreviewQuizInteractor implements PreviewQuizInputBoundary {
    private final PreviewQuizDataAccessInterface quizDataAccessObject;
    private final PreviewQuizOutputBoundary previewQuizPresenter;

    public PreviewQuizInteractor(PreviewQuizDataAccessInterface quizDataAccess,
                                 PreviewQuizOutputBoundary previewQuizPresenter) {
        this.quizDataAccessObject = quizDataAccess;
        this.previewQuizPresenter = previewQuizPresenter;
    }

    @Override
    public void execute(PreviewQuizInputData previewQuizInputData) {

        final String quizName = previewQuizInputData.getQuizName();
        final String creatorUsername = previewQuizInputData.getCreatorUsername();

        Quiz quiz = quizDataAccess.getQuiz(quizName, creatorUsername);

        if (quiz == null) {
            previewQuizPresenter.prepareFailView("Quiz \"" + quizName + "\" for user \""
                    + creatorUsername + "\" is not found.");
            return;
        }

        // A list of QuestionData objects, each representing one question.
        // This is for simplicity, to keep title, options and answer together.
        List<PreviewQuizOutputData.QuestionData> questionDataList = new ArrayList<>();

        for (Question q: quiz.previewQuestions()) {
            questionDataList.add(new PreviewQuizOutputData.QuestionData(
                    q.getTitle(),
                    q.getOptions(),
                    q.getAnswer()
                    )
            );
        }

        PreviewQuizOutputData previewQuizOutputData = new PreviewQuizOutputData(questionDataList);

        previewQuizPresenter.prepareSuccessView(previewQuizOutputData);
    }
}
