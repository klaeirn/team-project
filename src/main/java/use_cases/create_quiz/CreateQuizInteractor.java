package use_cases.create_quiz;

import entities.Quiz;
import entities.QuizFactory;
import entities.Question;
import java.util.List;

public class CreateQuizInteractor implements CreateQuizInputBoundary {
    private final CreateQuizDataAccessInterface quizDataAccessObject;
    private final UserDataAccessInterface userDataAccessObject;
    private final QuizFactory quizFactory;

    public CreateQuizInteractor(CreateQuizDataAccessInterface quizDataAccessObject, QuizFactory quizFactory,
                                UserDataAccessInterface userDataAccessObject) {
        this.quizDataAccessObject = quizDataAccessObject;
        this.quizFactory = quizFactory;
        this.userDataAccessObject = userDataAccessObject;

    }

    @Override
    public void execute(CreateQuizInputData createQuizInputData) {
        String quizName = createQuizInputData.getQuizName();
        String category = createQuizInputData.getCategory();
        String creatorUsername = userDataAccessObject.getCurrentUsername();
        List<Question> questions = createQuizInputData.getQuestions();

        Quiz quiz = quizFactory.createQuiz(quizName, creatorUsername, category, questions);
        quizDataAccessObject.saveUserQuiz(quiz);
    }
}

