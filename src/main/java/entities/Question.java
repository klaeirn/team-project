package entities;

import java.util.List;

public class Question {

    private String title;
    private List<String> options;
    private String answer;

    public Question(String title, List<String> options, String answer) {
        this.title = title;
        this.options = options;
        this.answer = answer;
    }

    /**
     * Validates the correct anwser for a question.
     *
     * @param answer : the correct choice for the question.
     * @return returns if the answer is valid
     */
    public Boolean validateAnswer(String answer) {
        return this.answer.equals(answer);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
