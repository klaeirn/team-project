package use_cases.take_quiz;

import entities.Question;
import java.util.Map;

public class TakeQuizOutputData {
    private final Question currentQuestion;
    private final int currentQuestionIndex;
    private final int totalQuestions;
    private final Map<Integer, String> userAnswers;
    private final boolean isLastQuestion;
    private final Integer score;
    private final String errorMessage;

    public TakeQuizOutputData(Question currentQuestion, int currentQuestionIndex, 
                             int totalQuestions, Map<Integer, String> userAnswers, 
                             boolean isLastQuestion, Integer score, String errorMessage) {
        this.currentQuestion = currentQuestion;
        this.currentQuestionIndex = currentQuestionIndex;
        this.totalQuestions = totalQuestions;
        this.userAnswers = userAnswers;
        this.isLastQuestion = isLastQuestion;
        this.score = score;
        this.errorMessage = errorMessage;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public Map<Integer, String> getUserAnswers() {
        return userAnswers;
    }

    public boolean isLastQuestion() {
        return isLastQuestion;
    }

    public Integer getScore() {
        return score;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

