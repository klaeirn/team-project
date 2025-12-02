package entities;

import java.util.List;

public class Quiz {
    
    private String name;
    private String creatorUsername;
    private String category;
    private List<Question> questions;

    /**
     * Constructs a new Quiz with the given name, creator, category, and questions.
     *
     * @param name : the name of the quiz
     * @param creatorUsername : the username of the quiz creator
     * @param category : the category of the quiz
     * @param questions : the list of questions in the quiz
     */
    public Quiz(String name, String creatorUsername, String category, List<Question> questions) {
        this.name = name;
        this.creatorUsername = creatorUsername;
        this.category = category;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Returns a preview of all questions in this quiz.
     *
     * @return the list of questions in this quiz
     */
    public List<Question> previewQuestions() {
        return this.questions;
    }

    /**
     * Adds a question to this quiz.
     *
     * @param question : the question to add
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    /**
     * Removes a question from this quiz by matching the question title.
     *
     * @param questionTitle : the title of the question to remove
     */
    public void removeQuestion(String questionTitle) {
        
        for (Question question: this.questions) {

            if (questionTitle.equals(question.getTitle())) {
                this.questions.remove(question);
                return;
            }

        }
    }

    /**
     * Removes a question from this quiz.
     *
     * @param question : the question to remove
     */
    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

}
