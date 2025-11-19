package use_cases.quickstart;

public class QuickstartInputData {
    private final String category;
    private final String difficulty;
    private final String type;
    private final String username;

    public QuickstartInputData(String category, String difficulty, String type, String username) {
        this.category = category;
        this.difficulty = difficulty;
        this.type = type;
        this.username = username;
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

    public String getUsername() {
        return username;
    }
}

