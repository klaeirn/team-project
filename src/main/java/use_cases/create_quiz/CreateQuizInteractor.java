package use_cases.create_quiz;

import entities.Quiz;
import entities.QuizFactory;
import entities.Question;
import java.util.List;
import java.util.ArrayList;

public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private final CreateQuizDataAccessInterface quizDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;
    private final QuizFactory quizFactory;
    private final CreateQuizOutputBoundary createQuizPresenter;

    public CreateQuizInteractor(CreateQuizDataAccessInterface quizDataAccessObject, QuizFactory quizFactory,
                                UserDataAccessInterface userDataAccessObject, CreateQuizOutputBoundary createQuizPresenter) {
        this.quizDataAccessObject = quizDataAccessObject;
        this.quizFactory = quizFactory;
        this.userDataAccessObject = userDataAccessObject;
        this.createQuizPresenter = createQuizPresenter;
    }

    @Override
    public void execute(CreateQuizInputData createQuizInputData) {
        String quizName = createQuizInputData.getQuizName();
        String category = createQuizInputData.getCategory();
        String creatorUsername = userDataAccessObject.getCurrentUsername();
        List<List<String>> questionsDetails = createQuizInputData.getQuestionsDetails();
        List<String> correctAnswers = createQuizInputData.getCorrectAnswers();
        
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < questionsDetails.size(); i++) {
            List<String> questionDetails = questionsDetails.get(i);
            String correctAnswer = correctAnswers.get(i);
            Question question = new Question(
                questionDetails.get(0), 
                questionDetails.subList(1, questionDetails.size()),
                correctAnswer);
            questions.add(question);
        }

        Quiz quiz = quizFactory.createQuiz(quizName, creatorUsername, category, questions);
        quizDataAccessObject.saveUserQuiz(quiz);

        CreateQuizOutputData outputData = new CreateQuizOutputData(quizName, category, creatorUsername);
        createQuizPresenter.prepareSuccessView(outputData);
    }

    @Override
    public void switchToCreateQuizView() {
        createQuizPresenter.switchToCreateQuizView();
    }

    public void switchToLoggedInView() {
        createQuizPresenter.switchToLoggedInView();
    }
}

