package Entities;
import java.util.List;
import Entities.Question;

public class Quiz {
    
    private String name;
    private String creatorUsername;
    private String category;
    private List<Question> questions;

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public List<Question> previewQuestions(){
        return this.questions;
    }

    // @param string of the question to remove
    public void removeQuestion(String q) {
        
        for (Question question: this.questions) {

            if (q.equals(question.getTitle())) {
                this.questions.remove(question);
                return;
            }

        }
    }

    public void removeQuestion(Question q) {
        this.questions.remove(q);
    }

    public Quiz(String name, String creatorUsername, String category, List<Question> questions) {
        this.name = name;
        this.creatorUsername = creatorUsername;
        this.category = category;
        this.questions = questions;
    }   

}
