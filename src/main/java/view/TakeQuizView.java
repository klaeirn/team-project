package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.take_quiz.TakeQuizController;
import interface_adapter.take_quiz.TakeQuizState;
import interface_adapter.take_quiz.TakeQuizViewModel;
import entities.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for taking a quiz. Displays questions with answer options
 * and navigation buttons.
 */
public class TakeQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "take quiz";
    private TakeQuizController takeQuizController;
    private final TakeQuizViewModel takeQuizViewModel;
    private ViewManagerModel viewManagerModel;

    private final JLabel questionLabel;
    private final JPanel optionsPanel;
    private final ButtonGroup answerGroup;
    private final JRadioButton[] optionButtons;
    private final JButton previousButton;
    private final JButton nextButton;
    private final JButton backButton;
    private final JLabel errorLabel;
    private int currentQuestionIndex;

    public TakeQuizView(TakeQuizViewModel takeQuizViewModel) {
        this.takeQuizViewModel = takeQuizViewModel;
        this.takeQuizViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Take Quiz");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(10));

        // Question label with text wrapping and scrolling
        questionLabel = new JLabel();
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.PLAIN, 14));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Enable HTML for text wrapping
        questionLabel.setVerticalTextPosition(SwingConstants.TOP);

        // Options panel with scrolling
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        answerGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4]; // Maximum 4 options for multiple choice

        // Error label
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buttons panel - aligned to the left
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        backButton = new JButton("Back");

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (takeQuizController != null) {
                    saveCurrentAnswer();
                    takeQuizController.previousQuestion();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (takeQuizController != null) {
                    saveCurrentAnswer();
                    TakeQuizState state = takeQuizViewModel.getState();
                    // Submit functionality will be implemented later
                    // For now, just navigate to next question if available
                    if (!state.isLastQuestion()) {
                        takeQuizController.nextQuestion();
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to quiz menu
                int option = JOptionPane.showConfirmDialog(
                        TakeQuizView.this,
                        "Are you sure you want to go back? Your progress will be lost.",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (option == JOptionPane.YES_OPTION) {
                    // Navigate back to quiz menu
                    if (viewManagerModel != null) {
                        viewManagerModel.setState("quiz menu");
                        viewManagerModel.firePropertyChange();
                    }
                }
            }
        });

        buttonsPanel.add(previousButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(backButton);

        // Main content panel with scrolling for long questions
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Question panel with wrapping
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionPanel.add(questionLabel, BorderLayout.WEST);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        contentPanel.add(questionPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(optionsPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(errorLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(buttonsPanel);

        // Scroll pane for long content
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void saveCurrentAnswer() {
        TakeQuizState state = takeQuizViewModel.getState();
        String selectedAnswer = null;

        for (JRadioButton button : optionButtons) {
            if (button != null && button.isSelected()) {
                selectedAnswer = button.getText();
                break;
            }
        }

        if (selectedAnswer != null) {
            // Extract just the answer text (remove "A. ", "B. ", etc.)
            String answerText = selectedAnswer;
            if (selectedAnswer.matches("^[A-D]\\.\\s.*")) {
                answerText = selectedAnswer.substring(3);
            } else if (selectedAnswer.matches("^True|False$")) {
                answerText = selectedAnswer;
            }

            // Update the interactor's answer
            if (takeQuizController != null) {
                takeQuizController.setAnswer(currentQuestionIndex - 1, answerText);
            }
            state.setAnswer(currentQuestionIndex - 1, answerText);
        }
    }

    private void updateQuestionDisplay(TakeQuizState state) {
        Question question = state.getCurrentQuestion();
        if (question == null) {
            return;
        }

        // Update question label
        questionLabel.setText(String.format("Question %d: %s",
                state.getCurrentQuestionIndex(), question.getTitle()));

        // Clear previous options
        optionsPanel.removeAll();
        answerGroup.clearSelection();
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i] != null) {
                answerGroup.remove(optionButtons[i]);
                optionButtons[i] = null;
            }
        }

        // Add new options
        List<String> options = question.getOptions();
        if (options != null && !options.isEmpty()) {
            char label = 'A';
            for (int i = 0; i < options.size() && i < 4; i++) {
                String optionText = options.get(i);
                String displayText;

                // Format based on question type
                if (options.size() == 2 && (optionText.equalsIgnoreCase("True") ||
                        optionText.equalsIgnoreCase("False"))) {
                    displayText = optionText;
                } else {
                    displayText = label + ". " + optionText;
                }

                JRadioButton optionButton = new JRadioButton(displayText);
                optionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                optionButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                // Check if this is the previously selected answer
                String savedAnswer = state.getAnswer(state.getCurrentQuestionIndex() - 1);
                if (savedAnswer != null && optionText.equals(savedAnswer)) {
                    optionButton.setSelected(true);
                }

                // Add listener to save answer when selected
                final int questionIndex = state.getCurrentQuestionIndex() - 1;
                final String answerText = optionText;
                optionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (optionButton.isSelected()) {
                            if (takeQuizController != null) {
                                takeQuizController.setAnswer(questionIndex, answerText);
                            }
                            TakeQuizState currentState = takeQuizViewModel.getState();
                            currentState.setAnswer(questionIndex, answerText);
                        }
                    }
                });

                answerGroup.add(optionButton);
                optionsPanel.add(optionButton);
                optionButtons[i] = optionButton;
                label++;
            }
        }

        // Update button states
        previousButton.setEnabled(state.getCurrentQuestionIndex() > 1);
        nextButton.setEnabled(!state.isLastQuestion());
        nextButton.setText("Next");

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public void setTakeQuizController(TakeQuizController controller) {
        this.takeQuizController = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        TakeQuizState state = (TakeQuizState) evt.getNewValue();
        if (state != null) {
            currentQuestionIndex = state.getCurrentQuestionIndex();

            if (state.getCurrentQuestion() != null) {
                // Update question display
                updateQuestionDisplay(state);
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

