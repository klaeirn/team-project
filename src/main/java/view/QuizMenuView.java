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
    private final JButton beginButton;
    private final JButton backButton;
    private final DefaultListModel<String> quizListModel = new DefaultListModel<>();
    private final JList<String> quizList = new JList<>(quizListModel);

    public QuizMenuView() {
        final JLabel title = new JLabel("Quiz Menu");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Quiz list in a scroll panel (currently empty)
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane quizScroll = new JScrollPane(quizList);
        quizScroll.setBorder(BorderFactory.createTitledBorder("Existing Quizzes"));
        quizScroll.setPreferredSize(new Dimension(350, 150));

        final JPanel buttons = new JPanel();
        quickstartButton = new JButton("Quickstart");
        beginButton = new JButton("Begin");
        backButton = new JButton("Back");
        buttons.add(quickstartButton);
        buttons.add(beginButton);
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

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizListModel.isEmpty()) {
                    JOptionPane.showMessageDialog(QuizMenuView.this,
                            "No quizzes available yet.",
                            "Begin Quiz",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String selected = quizList.getSelectedValue();
                if (selected == null) {
                    JOptionPane.showMessageDialog(QuizMenuView.this,
                            "Please select a quiz from the list.",
                            "Begin Quiz",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(8));
        this.add(quizScroll);
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
