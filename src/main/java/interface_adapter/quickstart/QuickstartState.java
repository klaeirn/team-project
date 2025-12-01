package interface_adapter.quickstart;

import entities.Quiz;

public class QuickstartState {

    private String selectedCategory;
    private String selectedDifficulty;
    private String selectedType;
    private Quiz quiz;
    private String errorMessage;
    private String username;

    public QuickstartState(QuickstartState copy) {
        if (copy != null) {
            this.selectedCategory = copy.selectedCategory;
            this.selectedDifficulty = copy.selectedDifficulty;
            this.selectedType = copy.selectedType;
        } else {
            setDefaults();
        }
    }

    public QuickstartState() {
        setDefaults();
    }

    private void setDefaults() {
        this.selectedCategory = "Any Category";
        this.selectedDifficulty = "Any Difficulty";
        this.selectedType = "Any Type";
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(String selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
