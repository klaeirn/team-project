package view;

import interface_adapter.quickstart.QuickstartViewModel;
import interface_adapter.quickstart.QuickstartState;
import interface_adapter.quiz_menu.QuizMenuController;
import interface_adapter.quickstart.QuickstartController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The Quickstart configuration view. Lets the user choose category, difficulty, and type,
 * then begin or go back to the Quiz Menu.
 */
public class QuickstartView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "quickstart";
    private QuickstartController quickstartController;
    private QuizMenuController quizMenuController;
    private interface_adapter.take_quiz.TakeQuizController takeQuizController;

    private final JList<String> categoryList;
    private final JList<String> difficultyList;
    private final JList<String> typeList;

    private final JButton beginButton;
    private final JButton backButton;

    public QuickstartView(QuickstartViewModel quickstartViewModel) {
        final JLabel title = new JLabel("Quickstart");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] categories = {
                "Any Category",
                "General Knowledge",
                "Entertainment: Books",
                "Entertainment: Film",
                "Entertainment: Music",
                "Entertainment: Musicals & Theatres",
                "Entertainment: Television",
                "Entertainment: Video Games",
                "Entertainment: Board Games",
                "Science & Nature",
                "Science: Computers",
                "Science: Mathematics",
                "Mythology",
                "Sports",
                "Geography",
                "History",
                "Politics",
                "Art",
                "Celebrities",
                "Animals",
                "Vehicles",
                "Entertainment: Comics",
                "Science: Gadgets",
                "Entertainment: Japanese Anime & Manga",
                "Entertainment: Cartoon & Animations"
        };
        String[] difficulties = {
                "Any Difficulty",
                "Easy",
                "Medium",
                "Hard"
        };
        String[] types = {
                "Any Type",
                "Multiple Choice",
                "True/False"
        };

        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setSelectedIndex(0);
        difficultyList = new JList<>(difficulties);
        difficultyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        difficultyList.setSelectedIndex(0);
        typeList = new JList<>(types);
        typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        typeList.setSelectedIndex(0);

        categoryList.setVisibleRowCount(1);
        difficultyList.setVisibleRowCount(1);
        typeList.setVisibleRowCount(1);

        final JPanel listsPanel = new JPanel();
        listsPanel.setLayout(new BoxLayout(listsPanel, BoxLayout.Y_AXIS));

        listsPanel.add(wrapWithTitledScroll(categoryList, "Category"));
        listsPanel.add(Box.createVerticalStrut(8));
        listsPanel.add(wrapWithTitledScroll(difficultyList, "Difficulty"));
        listsPanel.add(Box.createVerticalStrut(8));
        listsPanel.add(wrapWithTitledScroll(typeList, "Type"));

        final JPanel buttons = new JPanel();
        beginButton = new JButton("Begin");
        backButton = new JButton("Back");
        buttons.add(beginButton);
        buttons.add(backButton);

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categoryList.getSelectedValue();
                String difficulty = difficultyList.getSelectedValue();
                String type = typeList.getSelectedValue();

                if (quickstartController != null) {
                    quickstartController.execute(category, difficulty, type);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quickstartController != null) {
                    quickstartController.backToQuizMenu();
                } else if (quizMenuController != null) {
                    quizMenuController.switchToQuizMenu();
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(10));
        this.add(listsPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttons);
    }

    private JScrollPane wrapWithTitledScroll(JList<String> list, String title) {
        JScrollPane scroll = new JScrollPane(list);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(BorderFactory.createTitledBorder(title));
        return scroll;
    }

    public String getViewName() { return viewName; }

    public void setQuickstartController(QuickstartController quickstartController) {
        this.quickstartController = quickstartController;
    }

    public QuickstartController getQuickstartController() {
        return quickstartController;
    }

    public void setQuizMenuController(QuizMenuController quizMenuController) {
        this.quizMenuController = quizMenuController;
    }

    public void setTakeQuizController(interface_adapter.take_quiz.TakeQuizController takeQuizController) {
        this.takeQuizController = takeQuizController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        QuickstartState state = (QuickstartState) evt.getNewValue();
        if (state != null) {
            if (state.getErrorMessage() != null && !state.getErrorMessage().isEmpty()) {
                JOptionPane.showMessageDialog(QuickstartView.this,
                        state.getErrorMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (state.getQuiz() != null) {
                // Quiz loaded successfully, trigger TakeQuiz
                if (takeQuizController != null) {
                    String username = state.getUsername();
                    if (username == null) {
                        username = "Guest";
                    }
                    takeQuizController.execute(state.getQuiz(), username);
                }
            }
        }
    }
}
