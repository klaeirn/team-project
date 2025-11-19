package use_cases.take_quiz;

import entities.Question;
import entities.Quiz;
import java.util.Map;

public class TakeQuizOutputData {
    private final Question currentQuestion;
    private final int currentQuestionIndex;
    private final int totalQuestions;
    private final Map<Integer, String> userAnswers;
    private final boolean isLastQuestion;
    private final Integer score;
    private final String errorMessage;
    private final Quiz quiz;
    private final String username;

    public TakeQuizOutputData(Question currentQuestion, int currentQuestionIndex, 
                             int totalQuestions, Map<Integer, String> userAnswers,
                              boolean isLastQuestion, Integer score, String errorMessage,
                              Quiz quiz, String username) {
        this.currentQuestion = currentQuestion;
        this.currentQuestionIndex = currentQuestionIndex;
        this.totalQuestions = totalQuestions;
        this.userAnswers = userAnswers;
        this.isLastQuestion = isLastQuestion;
        this.score = score;
        this.errorMessage = errorMessage;
        this.quiz = quiz;
        this.username = username;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public String getUsername() {
        return username;
    }
}

