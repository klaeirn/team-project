package interface_adapter.quickstart;

public class QuickstartState {

    private String selectedCategory;
    private String selectedDifficulty;
    private String selectedType;

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
}
