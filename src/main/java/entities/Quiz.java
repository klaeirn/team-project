package entities;
import java.util.List;

import entities.Question;

public class Quiz {
    
    private String name;
    private String creatorUsername;
    private String category;
    private List<Question> questions;


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCreatorUsername() { return creatorUsername; }

    public String getCategory() { return category; }

    public List<Question> previewQuestions(){
        return this.questions;
    }

    public void addQuestion(Question q) {
        this.questions.add(q);
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

    public List<Question> getQuestions() {
        return questions;
    }

}
