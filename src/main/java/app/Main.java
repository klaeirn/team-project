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
                .addResultsView()
                .addChangeUsernameView()
                .addShareQuizView()
                .addCreateQuizView()
                .addValidateQuestionView()
                .addLoginUseCase()
                .addChangeUsernameUseCase()
                .addQuickstartUseCase()
                .addTakeQuizUseCase()
                .addViewResultsUseCase()
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