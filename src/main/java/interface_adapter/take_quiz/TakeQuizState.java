package interface_adapter.take_quiz;

import entities.Question;
import java.util.Map;
import java.util.HashMap;

public class TakeQuizState {
    private Question currentQuestion;
    private int currentQuestionIndex;
    private int totalQuestions;
    private Map<Integer, String> userAnswers = new HashMap<>();
    private boolean isLastQuestion;
    private Integer score;
    private String errorMessage;
    private String selectedAnswer;

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Map<Integer, String> getUserAnswers() {
        return new HashMap<>(userAnswers);
    }

    public void setUserAnswers(Map<Integer, String> userAnswers) {
        this.userAnswers = userAnswers != null ? new HashMap<>(userAnswers) : new HashMap<>();
    }

    public void setAnswer(int questionIndex, String answer) {
        this.userAnswers.put(questionIndex, answer);
    }

    public String getAnswer(int questionIndex) {
        return userAnswers.get(questionIndex);
    }

    public boolean isLastQuestion() {
        return isLastQuestion;
    }

    public void setLastQuestion(boolean lastQuestion) {
        isLastQuestion = lastQuestion;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}

