package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                //.addCreateQuizView()
                //.addCreateQuizUseCase()
                .addLoginView()
                .addLoggedInView()
                .addQuizMenuView()
                .addQuickstartView()
                .addSelectExistingQuizView()
                .addTakeQuizView()
                .addChangeUsernameView()
                .addLoginUseCase()
                .addChangeUsernameUseCase()
                .addQuickstartUseCase()
                .addTakeQuizUseCase()
                .addQuizMenuController()
                .addSelectExistingQuizController()
                .wireControllers()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}