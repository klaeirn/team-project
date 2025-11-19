package interface_adapter.select_existing_quiz;

import entities.Quiz;
import java.util.List;
import java.util.ArrayList;

public class SelectExistingQuizState {
    private List<Quiz> availableQuizzes = new ArrayList<>();
    private Quiz selectedQuiz;
    private String username;
    private String errorMessage;

    public List<Quiz> getAvailableQuizzes() {
        return new ArrayList<>(availableQuizzes);
    }

    public void setAvailableQuizzes(List<Quiz> quizzes) {
        this.availableQuizzes = quizzes != null ? new ArrayList<>(quizzes) : new ArrayList<>();
    }

    public Quiz getSelectedQuiz() {
        return selectedQuiz;
    }

    public void setSelectedQuiz(Quiz quiz) {
        this.selectedQuiz = quiz;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

