package view;

import interface_adapter.quiz_menu.QuizMenuController;
import interface_adapter.select_existing_quiz.SelectExistingQuizController;

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
    private SelectExistingQuizController selectExistingQuizController;

    private final JButton selectExistingQuizButton;
    private final JButton quickstartButton;
    private final JButton backButton;

    public QuizMenuView() {
        final JLabel title = new JLabel("Quiz Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        selectExistingQuizButton = new JButton("Select existing quiz");
        quickstartButton = new JButton("Quickstart");
        backButton = new JButton("Back");
        buttons.add(selectExistingQuizButton);
        buttons.add(quickstartButton);
        buttons.add(backButton);

        selectExistingQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectExistingQuizController != null) {
                    selectExistingQuizController.switchToSelectExistingQuiz();
                }
            }
        });

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
        this.add(Box.createVerticalStrut(8));
        this.add(buttons);
    }

    public String getViewName() { return viewName; }

    public void setQuizMenuController(QuizMenuController quizMenuController) {
        this.quizMenuController = quizMenuController;
    }

    public void setSelectExistingQuizController(SelectExistingQuizController selectExistingQuizController) {
        this.selectExistingQuizController = selectExistingQuizController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }
}
