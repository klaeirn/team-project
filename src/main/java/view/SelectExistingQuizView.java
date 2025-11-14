package view;

import interface_adapter.select_existing_quiz.SelectExistingQuizController;
import interface_adapter.select_existing_quiz.SelectExistingQuizState;
import interface_adapter.select_existing_quiz.SelectExistingQuizViewModel;
import interface_adapter.quiz_menu.QuizMenuController;
import entities.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for selecting an existing quiz from a list.
 */
public class SelectExistingQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "select existing quiz";
    private SelectExistingQuizController selectExistingQuizController;
    private QuizMenuController quizMenuController;
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;

    private final JList<String> quizList;
    private final JScrollPane quizScrollPane;
    private final JButton beginButton;
    private final JButton backButton;
    private final JLabel errorLabel;
    private List<Quiz> quizzes;

    public SelectExistingQuizView(SelectExistingQuizViewModel selectExistingQuizViewModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
        this.selectExistingQuizViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Select Existing Quiz");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Initialize quiz list with JScrollPane
        quizList = new JList<>(new String[]{"No quizzes available"});
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        quizList.setVisibleRowCount(10);
        quizScrollPane = new JScrollPane(quizList);
        quizScrollPane.setBorder(BorderFactory.createTitledBorder("Available Quizzes"));
        quizScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        quizScrollPane.setPreferredSize(new Dimension(500, 300));

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        beginButton = new JButton("Begin");
        backButton = new JButton("Back");
        buttons.add(beginButton);
        buttons.add(backButton);

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = quizList.getSelectedIndex();
                if (selectedIndex >= 0 && quizzes != null && selectedIndex < quizzes.size()) {
                    Quiz selectedQuiz = quizzes.get(selectedIndex);
                    if (selectExistingQuizController != null) {
                        selectExistingQuizController.beginQuiz(selectedQuiz);
                    }
                } else {
                    JOptionPane.showMessageDialog(SelectExistingQuizView.this,
                            "Please select a quiz from the list.",
                            "No Quiz Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizMenuController != null) {
                    quizMenuController.switchToQuizMenu();
                } else if (selectExistingQuizController != null) {
                    selectExistingQuizController.switchToQuizMenu();
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(10));
        this.add(quizScrollPane);
        this.add(Box.createVerticalStrut(10));
        this.add(errorLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttons);
    }

    public String getViewName() {
        return viewName;
    }

    public void setSelectExistingQuizController(SelectExistingQuizController controller) {
        this.selectExistingQuizController = controller;
    }

    public void setQuizMenuController(QuizMenuController controller) {
        this.quizMenuController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SelectExistingQuizState state = (SelectExistingQuizState) evt.getNewValue();
        if (state != null) {
            // Update quiz list
            quizzes = state.getAvailableQuizzes();
            if (quizzes != null && !quizzes.isEmpty()) {
                String[] quizNames = new String[quizzes.size()];
                for (int i = 0; i < quizzes.size(); i++) {
                    Quiz quiz = quizzes.get(i);
                    if (quiz != null) {
                        String name = quiz.getName() != null ? quiz.getName() : "Unnamed Quiz";
                        String category = quiz.getCategory() != null ? quiz.getCategory() : "No Category";
                        String creator = quiz.getCreatorUsername() != null ? quiz.getCreatorUsername() : "Unknown";
                        int questionCount = quiz.getQuestions() != null ? quiz.getQuestions().size() : 0;
                        quizNames[i] = String.format("%s (%s) - Created by: %s - %d questions",
                                name, category, creator, questionCount);
                    } else {
                        quizNames[i] = "Unknown Quiz";
                    }
                }
                quizList.setListData(quizNames);
            } else {
                quizList.setListData(new String[]{"No quizzes available"});
            }

            // Update error message
            String error = state.getErrorMessage();
            if (error != null && !error.isEmpty()) {
                errorLabel.setText(error);
            } else {
                errorLabel.setText("");
            }
        }
    }
}

