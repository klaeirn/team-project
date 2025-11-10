package Entities;

import java.util.List;

public class QuestionFactory {

    public Question createQuestion(String title, List<String> options, String answer)
    {
        return new Question(title, options, answer);
    }
}
