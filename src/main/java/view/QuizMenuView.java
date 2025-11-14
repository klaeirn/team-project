package view;

import interface_adapter.quiz_menu.QuizMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for the Quiz Menu after pressing the take quiz button.
 */
public class QuizMenuView extends JPanel implements ActionListener {
    private final String viewName = "quiz menu";
    private QuizMenuController quizMenuController;

    private final JButton quickstartButton;
    private final JButton backButton;

    public QuizMenuView() {
        final JLabel title = new JLabel("Quiz Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        quickstartButton = new JButton("Quickstart");
        backButton = new JButton("Back");
        buttons.add(quickstartButton);
        buttons.add(backButton);

        quickstartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizMenuController != null) {
                    quizMenuController.switchToQuickstart();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizMenuController != null) {
                    quizMenuController.switchToLoggedIn();
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(buttons);
    }

    public String getViewName() { return viewName; }

    public void setQuizMenuController(QuizMenuController quizMenuController) {
        this.quizMenuController = quizMenuController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }
}
