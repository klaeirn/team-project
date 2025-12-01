package view;

import interface_adapter.quiz_menu.QuizMenuController;
import interface_adapter.quiz_menu.QuizMenuViewModel;
import interface_adapter.select_existing_quiz.SelectExistingQuizController;
import interface_adapter.take_shared_quiz.TakeSharedQuizController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for the Quiz Menu after pressing the take quiz button.
 */
public class QuizMenuView extends JPanel implements ActionListener,
        PropertyChangeListener {
    private final String viewName = "quiz menu";
    private QuizMenuController quizMenuController;
    private SelectExistingQuizController selectExistingQuizController;
    private TakeSharedQuizController takeSharedQuizController;

    private final JButton selectExistingQuizButton;
    private final JButton quickstartButton;
    private final JButton backButton;
    private final JButton takeSharedQuizButton;


    public QuizMenuView(QuizMenuViewModel quizMenuViewModel) {
        final JLabel title = new JLabel("Quiz Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        selectExistingQuizButton = new JButton("Select existing quiz");
        quickstartButton = new JButton("Quickstart");
        backButton = new JButton("Back");
        takeSharedQuizButton = new JButton("Take Shared Quiz");
        buttons.add(selectExistingQuizButton);
        buttons.add(takeSharedQuizButton);
        buttons.add(quickstartButton);
        buttons.add(backButton);
//        buttons.add(takeSharedQuizButton);

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

        takeSharedQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (takeSharedQuizController != null) {
                    takeSharedQuizController.switchToTakeSharedQuizView();
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

    public void setTakeSharedQuizController(TakeSharedQuizController takeSharedQuizController) {
        this.takeSharedQuizController = takeSharedQuizController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
