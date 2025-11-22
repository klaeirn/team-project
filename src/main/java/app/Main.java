package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addLoggedInView()
                .addQuizMenuView()
                .addQuickstartView()
                .addSelectExistingQuizView()
                .addTakeQuizView()
                .addChangeUsernameView()
                .addShareQuizView()
                .addCreateQuizView()
                .addValidateQuestionView()
                .addLoginUseCase()
                .addChangeUsernameUseCase()
                .addQuickstartUseCase()
                .addTakeQuizUseCase()
                .addCreateQuizUseCase()
                .addValidateQuestionUseCase()
                .addQuizMenuController()
                .addSelectExistingQuizController()
                .wireControllers()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}