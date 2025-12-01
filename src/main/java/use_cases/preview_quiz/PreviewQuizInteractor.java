package use_cases.preview_quiz;

import entities.Quiz;
import entities.Question;
import entities.QuizFactory;

import java.util.ArrayList;
import java.util.List;

public class PreviewQuizInteractor implements PreviewQuizInputBoundary {
    private final PreviewQuizDataAccessInterface quizDataAccessObject;
    private final PreviewQuizOutputBoundary previewQuizPresenter;
    private final QuizFactory quizFactory;

    public PreviewQuizInteractor(PreviewQuizDataAccessInterface quizDataAccess,
                                 PreviewQuizOutputBoundary previewQuizPresenter, QuizFactory quizFactory) {
        this.quizDataAccessObject = quizDataAccess;
        this.previewQuizPresenter = previewQuizPresenter;
        this.quizFactory = quizFactory;
    }

    @Override
    public void execute(PreviewQuizInputData previewQuizInputData) {
        Quiz quiz;

        // 1st Scenario: Preview unsaved quiz
        if (previewQuizInputData.isPreviewingNewQuiz()) {
            List<Question> questions = new ArrayList<>();
            List<List<String>> rawQs = previewQuizInputData.getRawQuestions();
            List<String> answers = previewQuizInputData.getRawAnswers();

            // Empty Quiz
            if (rawQs.isEmpty()) {
                previewQuizPresenter.prepareFailView("No questions to preview.");
                return;
            }

            try {
                for (int i = 0; i < rawQs.size(); i++) {
                    List<String> details = rawQs.get(i);
                    // details[0] is Title, details[1-4] are Options
                    String title = details.get(0);

                    List<String> options = new ArrayList<>();
                    for (int j = 1; j < details.size(); j++) {
                        options.add(details.get(j));
                    }

                    String correctAnswer = answers.get(i);

                    questions.add(new Question(title, options, correctAnswer));
                }

                // Create a temporary quiz object (Not saved to DAO)
                quiz = quizFactory.createQuiz(
                        previewQuizInputData.getQuizName(),
                        previewQuizInputData.getCreatorUsername(),
                        previewQuizInputData.getCategory(),
                        questions
                );

            } catch (Exception e) {
                previewQuizPresenter.prepareFailView("Error building preview: " + e.getMessage());
                return;
            }
        } else {
            // 2nd scenario: Preview existing quiz
            quiz = quizDataAccessObject.getQuiz(previewQuizInputData.getQuizName(),
                    previewQuizInputData.getCreatorUsername());
            if (quiz == null) {
                previewQuizPresenter.prepareFailView("Quiz not found.");
                return;
            }
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
