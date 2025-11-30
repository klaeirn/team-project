package use_cases.create_quiz;

import java.util.ArrayList;
import java.util.List;

import entities.Question;
import entities.Quiz;
import entities.QuizFactory;

public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private static final int REQUIRED_QUESTION_COUNT = 10;

    private final CreateQuizDataAccessInterface quizDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;
    private final QuizFactory quizFactory;
    private final CreateQuizOutputBoundary createQuizPresenter;

    public CreateQuizInteractor(CreateQuizDataAccessInterface quizDataAccessObject, QuizFactory quizFactory,
                                UserDataAccessInterface userDataAccessObject,
                                CreateQuizOutputBoundary createQuizPresenter) {
        this.quizDataAccessObject = quizDataAccessObject;
        this.quizFactory = quizFactory;
        this.userDataAccessObject = userDataAccessObject;
        this.createQuizPresenter = createQuizPresenter;
    }

    @Override
    public void execute(CreateQuizInputData createQuizInputData) {
        final String quizName = createQuizInputData.getQuizName();
        final String category = createQuizInputData.getCategory();
        final List<List<String>> questionsDetails = createQuizInputData.getQuestionsDetails();
        final List<String> correctAnswers = createQuizInputData.getCorrectAnswers();

        if (quizName == null || quizName.trim().isEmpty()) {
            createQuizPresenter.prepareFailView("Quiz title cannot be empty.");
        }
        else if (category == null || category.trim().isEmpty()) {
            createQuizPresenter.prepareFailView("Category cannot be empty.");
        }
        else if (questionsDetails == null || questionsDetails.size() != REQUIRED_QUESTION_COUNT) {
            final int count;
            if (questionsDetails == null) {
                count = 0;
            }
            else {
                count = questionsDetails.size();
            }
            createQuizPresenter.prepareFailView(
                "Quiz must have exactly " + REQUIRED_QUESTION_COUNT + " questions. Current count: " + count
            );
        }
        else {
            final String creatorUsername = createQuizInputData.getUsername();
            final List<Question> questions = new ArrayList<>();
            for (int i = 0; i < questionsDetails.size(); i++) {
                final List<String> questionDetails = questionsDetails.get(i);
                final String correctAnswer = correctAnswers.get(i);
                final Question question = new Question(
                    questionDetails.get(0),
                    questionDetails.subList(1, questionDetails.size()),
                    correctAnswer);
                questions.add(question);
            }

            final Quiz quiz = quizFactory.createQuiz(quizName, creatorUsername, category, questions);
            quizDataAccessObject.saveUserQuiz(quiz);

            final CreateQuizOutputData outputData = new CreateQuizOutputData(
                    quizName, creatorUsername, category, questions.size());
            createQuizPresenter.prepareSuccessView(outputData);
        }
    }

    /**
     * Method for switching to the create quiz view.
     *
     * @param username : the user's username
     */
    @Override
    public void switchToCreateQuizView(String username) {
        createQuizPresenter.switchToCreateQuizView(username);
    }

    /**
     * Method for switching to the logged in view.
     *
     */
    public void switchToLoggedInView() {
        createQuizPresenter.switchToLoggedInView();
    }
}

