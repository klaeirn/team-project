package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                //.addCreateQuizView() uncomment to view create quiz view
                //.addCreateQuizUseCase() uncomment to view create quiz use case
                .addLoginView()
                .addLoggedInView()
                .addChangeUsernameView()
                .addLoginUseCase()
                .addChangeUsernameUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}