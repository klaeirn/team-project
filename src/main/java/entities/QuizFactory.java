package entities;

import java.util.List;

public class QuizFactory {

    public Quiz createQuiz(String name, String creatorUsername, String category, List<Question> questions)
    {
        return new Quiz(name, creatorUsername, category, questions);
    }
}
