package entities;

import java.util.List;

public class QuestionFactory {

    /**
     * Creates a new Question with the given title, options, and answer.
     *
     * @param title : the text of the question, must not be null or empty.
     * @param options : a list of possible answer options, must not be null or empty.
     * @param answer : the correct answer based on the provided options
     * @return a new instance of Question
     */
    public Question createQuestion(String title, List<String> options, String answer) {
        return new Question(title, options, answer);
    }
}
