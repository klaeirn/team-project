package data_access;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class QuizApiDatabase {

    private static final Map<String, Integer> CATEGORY_MAP;
    private static final Map<String, String> DIFFICULTY_MAP;
    private static final Map<String, String> TYPE_MAP;

    static {
        Map<String, Integer> categories = new HashMap<>();
        categories.put("Any Category", null);
        categories.put("General Knowledge", 9);
        categories.put("Entertainment: Books", 10);
        categories.put("Entertainment: Film", 11);
        categories.put("Entertainment: Music", 12);
        categories.put("Entertainment: Musicals & Theatres", 13);
        categories.put("Entertainment: Television", 14);
        categories.put("Entertainment: Video Games", 15);
        categories.put("Entertainment: Board Games", 16);
        categories.put("Science & Nature", 17);
        categories.put("Science: Computers", 18);
        categories.put("Science: Mathematics", 19);
        categories.put("Mythology", 20);
        categories.put("Sports", 21);
        categories.put("Geography", 22);
        categories.put("History", 23);
        categories.put("Politics", 24);
        categories.put("Art", 25);
        categories.put("Celebrities", 26);
        categories.put("Animals", 27);
        categories.put("Vehicles", 28);
        categories.put("Entertainment: Comics", 29);
        categories.put("Science: Gadgets", 30);
        categories.put("Entertainment: Japanese Anime & Manga", 31);
        categories.put("Entertainment: Cartoon & Animations", 32);
        CATEGORY_MAP = Collections.unmodifiableMap(categories);

        Map<String, String> difficulties = new HashMap<>();
        difficulties.put("Any Difficulty", null);
        difficulties.put("Easy", "easy");
        difficulties.put("Medium", "medium");
        difficulties.put("Hard", "hard");
        DIFFICULTY_MAP = Collections.unmodifiableMap(difficulties);

        Map<String, String> types = new HashMap<>();
        types.put("Any Type", null);
        types.put("Multiple Choice", "multiple");
        types.put("True/False", "boolean");
        TYPE_MAP = Collections.unmodifiableMap(types);
    }

    private QuizApiDatabase() {}

    public static Integer getCategoryId(String categoryDisplayName) {
        return CATEGORY_MAP.get(categoryDisplayName);
    }

    public static String getDifficultyParam(String difficultyDisplayName) {
        return DIFFICULTY_MAP.get(difficultyDisplayName);
    }

    public static String getTypeParam(String typeDisplayName) {
        return TYPE_MAP.get(typeDisplayName);
    }

    public static String buildUrl(String categoryDisplay,
                                  String difficultyDisplay,
                                  String typeDisplay) {
        StringBuilder sb = new StringBuilder("https://opentdb.com/api.php?amount=10");

        Integer catId = getCategoryId(categoryDisplay);
        if (catId != null) {
            sb.append("&category=").append(catId);
        }

        String diff = getDifficultyParam(difficultyDisplay);
        if (diff != null && !diff.isEmpty()) {
            sb.append("&difficulty=").append(diff);
        }

        String type = getTypeParam(typeDisplay);
        if (type != null && !type.isEmpty()) {
            sb.append("&type=").append(type);
        }

        return sb.toString();
    }
}
