package use_cases.take_quiz;

import entities.Question;
import entities.Quiz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakeQuizInteractor implements TakeQuizInputBoundary {
    private final TakeQuizOutputBoundary presenter;
    private Quiz quiz;
    private int currentQuestionIndex;
    private Map<Integer, String> userAnswers;

    public TakeQuizInteractor(TakeQuizOutputBoundary presenter) {
        this.presenter = presenter;
        this.userAnswers = new HashMap<>();
        this.currentQuestionIndex = 0;
    }

    @Override
    public void execute(TakeQuizInputData inputData) {
        this.quiz = inputData.getQuiz();
        this.currentQuestionIndex = 0;
        this.userAnswers = new HashMap<>();

        if (quiz == null || quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            presenter.prepareFailView("No quiz available to take.");
            return;
        }

        showCurrentQuestion();
    }

    @Override
    public void nextQuestion() {
        if (quiz == null || quiz.getQuestions() == null) {
            return;
        }

        if (currentQuestionIndex < quiz.getQuestions().size() - 1) {
            currentQuestionIndex++;
            showCurrentQuestion();
        }
    }

    @Override
    public void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            showCurrentQuestion();
        }
    }

    public void setAnswer(int questionIndex, String answer) {
        if (quiz == null || quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            return;
        }

        if (questionIndex < 0 || questionIndex >= quiz.getQuestions().size()) {
            return;
        }

        Question q = quiz.getQuestions().get(questionIndex);
        List<String> options = q.getOptions();
        if (options == null || !options.contains(answer)) {
            return;
        }

        userAnswers.put(questionIndex, answer);

        if (questionIndex == currentQuestionIndex) {
            showCurrentQuestion();
        }
    }

    private void showCurrentQuestion() {
        List<Question> questions = quiz.getQuestions();
        Question currentQuestion = questions.get(currentQuestionIndex);
        boolean isLastQuestion = (currentQuestionIndex == questions.size() - 1);

        TakeQuizOutputData outputData = new TakeQuizOutputData(
                currentQuestion, currentQuestionIndex + 1, questions.size(),
                userAnswers, isLastQuestion, null, null
        );
        presenter.prepareQuestionView(outputData);
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public Map<Integer, String> getUserAnswers() {
        return new HashMap<>(userAnswers);
    }
}

