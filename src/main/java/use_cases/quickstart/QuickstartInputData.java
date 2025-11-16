package use_cases.quickstart;

public class QuickstartInputData {
    private final String category;
    private final String difficulty;
    private final String type;

    public QuickstartInputData(String category, String difficulty, String type) {
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }
}

