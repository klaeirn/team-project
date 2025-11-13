package entities;

import java.util.List;

import entities.*;

public class User {

    private int user_id;
    private String user_name;
    private String password;
    // private List<QuizResult> quiz_history; we don't have this entity made yet
    private List<Quiz> custom_quizzes;

    public User(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }

    public String getUserName() {
        return this.user_name;
    }

    public void setUserName(String user_name) {
         this.user_name = user_name;
    }

    public String getPassword() {
        return this.password;
    }
    public void createQuiz(){

    }

    public void takeQuiz(){
    }

    public void viewQuizHistory(){

    }

}

