package Entities;
import java.util.List;

public class Question {

    private String title;
    private List<String> options;
    private String answer;

    public Question(String title,  List<String> options, String answer) {
        this.title = title;
        this.options = options;
        this.answer = answer;
    }

    public Boolean validate_answer(String answer){

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

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
