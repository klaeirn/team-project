package Entities;
import java.util.List;

public class Quiz {

    private final int quizId;
    private String name;
    private String creatorUsername;
    private String category;
    private final List<Question> questionList;
    private final String sharedLink;

    Quiz(int quizId, String name, String creatorUsername, String category,
         List<Question> questionList) {
        this.quizId = quizId;
        this.name = name;
        this.creatorUsername = creatorUsername;
        this.category = category;
        this.questionList = questionList;
    }

    public int getQuizid(){return quizId;}
    public String getName() { return name; }
    public String getCreatorUsername() { return creatorUsername; }
    public String getCategory() { return category; }
    public List<Question> getQuestionList() { return questionList; }
    public String getSharedLink() { return sharedLink; }

    public void addQuestion(Question question){
        questionList.add(question);
    }

    public void deleteQuestion(Question question){
        questionList.remove(question);
    }

    public void saveQuiz(){
        /// generate a JSON file and store in MongoDB
    }

    public void previewQuiz(){
        /// enter the preview mode (preview use case)?
    }

    public String shareQuiz(){
        /// get the hash from MongoDB
        return sharedLink;
    }

}
