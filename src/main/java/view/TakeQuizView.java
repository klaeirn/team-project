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
    private interface_adapter.view_results.ViewResultsController viewResultsController;

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
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Take Quiz");
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(10));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.PLAIN, 14));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        questionLabel.setVerticalTextPosition(SwingConstants.TOP);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        answerGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
                    if (state.isLastQuestion()) {
                        // Submit quiz and navigate to results view
                        takeQuizController.submitQuiz();
                        // Call ViewResultsController to show results
                        if (viewResultsController != null && state.getQuiz() != null && state.getUsername() != null) {
                            viewResultsController.execute(state.getQuiz(), state.getUsername(), state.getUserAnswers());
                        }
                    } else {
                        takeQuizController.nextQuestion();
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        TakeQuizView.this,
                        "Are you sure you want to go back? Your progress will be lost.",
                        "Confirm Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (option == JOptionPane.YES_OPTION) {
                    if (viewManagerModel != null) {
                        viewManagerModel.setState("quiz menu");
                        viewManagerModel.firePropertyChange();
                    }
                }
            }
        });

        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(previousButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(backButton);


        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(8, 20, 4, 20));
        questionPanel.add(questionLabel, BorderLayout.CENTER);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 2, 20));

        contentPanel.add(questionPanel);
        contentPanel.add(Box.createVerticalStrut(2));
        contentPanel.add(optionsPanel);
        contentPanel.add(Box.createVerticalStrut(6));
        contentPanel.add(errorLabel);
        contentPanel.add(Box.createVerticalStrut(8));
        contentPanel.add(buttonsPanel);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(contentPanel, BorderLayout.NORTH);

        // Scroll pane for long content
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void saveCurrentAnswer() {
        String selectedAnswer = null;

        for (JRadioButton button : optionButtons) {
            if (button != null && button.isSelected()) {
                selectedAnswer = button.getText();
                break;
            }
        }

        if (selectedAnswer != null) {
            String answerText = selectedAnswer;
            if (selectedAnswer.matches("^[A-D]\\.\\s.*")) {
                answerText = selectedAnswer.substring(3);
            } else if (selectedAnswer.matches("^True|False$")) {
                answerText = selectedAnswer;
            }

            if (takeQuizController != null) {
                takeQuizController.setAnswer(currentQuestionIndex - 1, answerText);
            }
        }
    }

    private void updateQuestionDisplay(TakeQuizState state) {
        Question question = state.getCurrentQuestion();
        if (question == null) {
            return;
        }

        questionLabel.setText(String.format("Question %d: %s",
                state.getCurrentQuestionIndex(), question.getTitle()));

        optionsPanel.removeAll();
        answerGroup.clearSelection();
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i] != null) {
                answerGroup.remove(optionButtons[i]);
                optionButtons[i] = null;
            }
        }

        List<String> options = question.getOptions();
        if (options != null && !options.isEmpty()) {
            char label = 'A';
            for (int i = 0; i < options.size() && i < 4; i++) {
                String optionText = options.get(i);
                String displayText;

                if (options.size() == 2 && (optionText.equalsIgnoreCase("True") ||
                        optionText.equalsIgnoreCase("False"))) {
                    displayText = optionText;
                } else {
                    displayText = label + ". " + optionText;
                }

                JRadioButton optionButton = new JRadioButton(displayText);
                optionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                optionButton.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

                String savedAnswer = state.getAnswer(state.getCurrentQuestionIndex() - 1);
                if (savedAnswer != null && optionText.equals(savedAnswer)) {
                    optionButton.setSelected(true);
                }

                final int questionIndex = state.getCurrentQuestionIndex() - 1;
                final String answerText = optionText;
                optionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (optionButton.isSelected()) {
                            if (takeQuizController != null) {
                                takeQuizController.setAnswer(questionIndex, answerText);
                            }
                        }
                    }
                });

                answerGroup.add(optionButton);
                optionsPanel.add(optionButton);
                optionButtons[i] = optionButton;
                label++;
            }
        }

        previousButton.setEnabled(state.getCurrentQuestionIndex() > 1);

        if (state.isLastQuestion()) {
            nextButton.setEnabled(true);
            nextButton.setText("Submit");
        } else {
            nextButton.setEnabled(true);
            nextButton.setText("Next");
        }

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

    public void setViewResultsController(interface_adapter.view_results.ViewResultsController viewResultsController) {
        this.viewResultsController = viewResultsController;
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
                updateQuestionDisplay(state);
            }

            String error = state.getErrorMessage();
            if (error != null && !error.isEmpty()) {
                errorLabel.setText(error);
            } else {
                errorLabel.setText("");
            }
        }
    }
}

