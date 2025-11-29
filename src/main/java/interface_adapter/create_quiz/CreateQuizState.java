package interface_adapter.create_quiz;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizState {
    private String quizName = "";
    private String category = "";
    private String username = "";
    private List<List<String>> questionsDetails = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private String createError;
    private String successMessage;
    private Integer editingQuestionIndex = null;
    private boolean reorderMode = false;

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public List<List<String>> getQuestionsDetails() {
        return questionsDetails;
    }

    public void setQuestionsDetails(List<List<String>> questionsDetails) {
        this.questionsDetails = questionsDetails;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getCreateError() {
        return createError;
    }

    public void setCreateError(String createError) {
        this.createError = createError;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Integer getEditingQuestionIndex() {return editingQuestionIndex;}

    public void setEditingQuestionIndex(Integer editingQuestionIndex){this.editingQuestionIndex = editingQuestionIndex;}

    public boolean isReorderMode() {
        return reorderMode;
    }

    public void setReorderMode(boolean reorderMode) {
        this.reorderMode = reorderMode;
    }

    public boolean moveQuestionUp(int index) {
        if (index <= 0 || index >= questionsDetails.size()) {
            return false;
        }
        
        List<String> tempQuestion = questionsDetails.get(index);
        questionsDetails.set(index, questionsDetails.get(index - 1));
        questionsDetails.set(index - 1, tempQuestion);
        
        String tempAnswer = correctAnswers.get(index);
        correctAnswers.set(index, correctAnswers.get(index - 1));
        correctAnswers.set(index - 1, tempAnswer);
        
        return true;
    }

    public boolean moveQuestionDown(int index) {
        if (index < 0 || index >= questionsDetails.size() - 1) {
            return false;
        }

        List<String> tempQuestion = questionsDetails.get(index);
        questionsDetails.set(index, questionsDetails.get(index + 1));
        questionsDetails.set(index + 1, tempQuestion);

        String tempAnswer = correctAnswers.get(index);
        correctAnswers.set(index, correctAnswers.get(index + 1));
        correctAnswers.set(index + 1, tempAnswer);
        
        return true;
    }
}

