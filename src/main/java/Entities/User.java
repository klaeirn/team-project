package Entities;

import java.util.List;

public class User {

    private int user_id;
    private String user_name;
    private String password;
    private List<QuizResult> quiz_history;
    private List<Quiz> custom_quizzes;

    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    public void createQuiz(){

    }

    public void takeQuiz(){
    }

    public void viewQuizHistory(){

    }

}

