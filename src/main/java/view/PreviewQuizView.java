package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.preview_quiz.PreviewQuizViewModel;
import interface_adapter.preview_quiz.PreviewQuizState;
import interface_adapter.preview_quiz.PreviewQuizController;
import use_cases.preview_quiz.PreviewQuizOutputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Font;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class PreviewQuizView extends JPanel implements PropertyChangeListener{

    private final PreviewQuizViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final PreviewQuizController controller;

    private final JLabel questionTitleLabel = new JLabel();
    private final JPanel optionsPanel = new JPanel();
    private final JLabel errorLabel = new JLabel();

    private final JButton prevButton = new JButton("Previous");
    private final JButton nextButton = new JButton("Next");
    private final JButton backButton = new JButton("Back to Edit");

    public PreviewQuizView(PreviewQuizViewModel viewModel,
                           ViewManagerModel viewManagerModel,
                           PreviewQuizController controller) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Question title and Error message at top
        JPanel topPanel = new JPanel(new BorderLayout());
        errorLabel.setForeground(Color.RED);
        errorLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        questionTitleLabel.setFont(questionTitleLabel.getFont().deriveFont(Font.BOLD, 18f));
        questionTitleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        topPanel.add(errorLabel, BorderLayout.NORTH);
        topPanel.add(questionTitleLabel, BorderLayout.SOUTH);

        // Options and correct answer in the middle
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        optionsScrollPane.setBorder(null);

        // 3 buttons (previous question, next question, back to edit) at bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(prevButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);
        add(optionsScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        addListeners();
    }

    private void addListeners() {
        // Switch to previous question
        prevButton.addActionListener(e -> {
            PreviewQuizState state = viewModel.getState();
            List<PreviewQuizOutputData.QuestionData> questions = state.getQuestions();
            if (questions == null || questions.isEmpty()) {
                return;
            }
            int i = state.getCurrentQuestionIndex();
            if (i > 0) {
                state.setCurrentQuestionIndex(i - 1);
                viewModel.firePropertyChange();
            }
        });

        // Switch to next question
        nextButton.addActionListener(e -> {
            PreviewQuizState state = viewModel.getState();
            List<PreviewQuizOutputData.QuestionData> questions = state.getQuestions();
            if (questions == null || questions.isEmpty()) {
                return;
            }
            int i = state.getCurrentQuestionIndex();
            if (i < questions.size() - 1) {
                state.setCurrentQuestionIndex(i + 1);
                viewModel.firePropertyChange();
            }
        });

        // Back to edit page
        backButton.addActionListener(e -> {
            // Depending on the name of edit quiz view model
            viewManagerModel.setState("edit quiz");
            viewManagerModel.firePropertyChange();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PreviewQuizState state = viewModel.getState();

        // Error in preview
        if (state.getPreviewError() != null) {
            errorLabel.setText(state.getPreviewError());
            questionTitleLabel.setText("");
            optionsPanel.removeAll();
            optionsPanel.revalidate();
            optionsPanel.repaint();
            return;
        } else {
            errorLabel.setText("");
        }

        // No question
        List<PreviewQuizOutputData.QuestionData> questions = state.getQuestions();
        if (questions == null || questions.isEmpty()) {
            questionTitleLabel.setText("No questions to preview.");
            optionsPanel.removeAll();
            optionsPanel.revalidate();
            optionsPanel.repaint();
            return;
        }

        int index = state.getCurrentQuestionIndex();
        if (index < 0 || index >= questions.size()) {
            index = 0;
            state.setCurrentQuestionIndex(0);
        }

        PreviewQuizOutputData.QuestionData q = questions.get(index);

        // automatic newline
        questionTitleLabel.setText("<html>Question " + (index + 1) + ": " + q.getTitle() + "</html>");

        // Create options (A/B/C/D), and the checkbox represents the correct answer
        optionsPanel.removeAll();

        List<String> options = q.getOptions();
        String correct = q.getAnswer();

        char letter = 'A';
        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(letter + ". " + option);
            // Demonstration only. Clicking the checkbox will not change answer.
            checkBox.setEnabled(true);
            checkBox.setFocusable(false);
            checkBox.setRequestFocusEnabled(false);
            checkBox.addActionListener(e -> {
                checkBox.setSelected(option.equals(correct));
            });
            if (option.equals(correct)) {checkBox.setSelected(true);}
            checkBox.setBorder(new EmptyBorder(5, 5, 5, 5));

            if (option.equals(correct)) {
                checkBox.setSelected(true); // checked for the correct answer
            }

            optionsPanel.add(checkBox);
            optionsPanel.add(Box.createVerticalStrut(5));
            letter++;
        }

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }
}
