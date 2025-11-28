package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.create_quiz.CreateQuizViewModel;

// importing validate question view model and state bc it needs to know about the question that is being edited
// does not violate clean architecture because its a view and not a use case
import interface_adapter.preview_quiz.PreviewQuizController;
import interface_adapter.validate_question.ValidateQuestionViewModel;
import interface_adapter.validate_question.ValidateQuestionState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class CreateQuizView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "create quiz";
    private final CreateQuizViewModel createQuizViewModel;

    private final JTextField quizTitleField = new JTextField(15);
    private final JTextField categoryField = new JTextField(15);
    private final JLabel errorField = new JLabel();
    
    private final JButton addQuestion;
    private final JButton reorderQuestions;
    private final JButton saveQuiz;
    private final JButton returnHome;
    private final JButton previewQuiz;
    
    private CreateQuizController createQuizController = null;
    private ViewManagerModel viewManagerModel;
    private ValidateQuestionViewModel validateQuestionViewModel;

    private PreviewQuizController previewQuizController;

    private final JPanel questionsPanel = new JPanel();
    private final JScrollPane questionsScrollPane;
    private final List<JPanel> questionPanels = new ArrayList<>();

    public CreateQuizView(CreateQuizViewModel createQuizViewModel) {
        this.createQuizViewModel = createQuizViewModel;
        this.createQuizViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Quiz Editing");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 16));


        final JLabel quizTitleLabel = new JLabel("Quiz Title:");
        quizTitleLabel.setPreferredSize(new Dimension(100, 25));
        final LabelTextPanel quizTitleInfo = new LabelTextPanel(quizTitleLabel, quizTitleField);
        quizTitleInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setPreferredSize(new Dimension(100, 25));
        final LabelTextPanel categoryInfo = new LabelTextPanel(categoryLabel, categoryField);
        categoryInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        questionsScrollPane = new JScrollPane(questionsPanel);
        questionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionsScrollPane.setPreferredSize(new Dimension(500, 200));
        questionsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        addQuestion = new JButton("Add Question");
        addQuestion.setBackground(Color.RED);
        addQuestion.setFocusPainted(false);
        
        saveQuiz = new JButton("Save Quiz");
        saveQuiz.setBackground(new Color(173, 216, 230)); 
        saveQuiz.setFocusPainted(false);
        
        previewQuiz = new JButton("Preview Quiz");
        previewQuiz.setBackground(new Color(173, 216, 230));
        previewQuiz.setFocusPainted(false);
        
        reorderQuestions = new JButton("Reorder Questions");
        reorderQuestions.setBackground(Color.YELLOW);
        reorderQuestions.setFocusPainted(false);
        
        returnHome = new JButton("Return Home");
        returnHome.setBackground(new Color(173, 216, 230)); 
        returnHome.setFocusPainted(false);

        addQuestion.addActionListener(this);
        reorderQuestions.addActionListener(this);
        saveQuiz.addActionListener(this);
        returnHome.addActionListener(this);
        previewQuiz.addActionListener(this);

        quizTitleField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final CreateQuizState currentState = createQuizViewModel.getState();
                currentState.setQuizName(quizTitleField.getText());
                createQuizViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        categoryField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final CreateQuizState currentState = createQuizViewModel.getState();
                currentState.setCategory(categoryField.getText());
                createQuizViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        this.add(title);
        this.add(Box.createVerticalStrut(20));

        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        formPanel.add(quizTitleInfo);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(categoryInfo);
        formPanel.add(Box.createVerticalStrut(8));
        errorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        formPanel.add(errorField);
        
        this.add(formPanel);
        this.add(Box.createVerticalStrut(20));

        final JLabel questionsListTitle = new JLabel("Questions List");
        questionsListTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionsListTitle.setFont(new Font(questionsListTitle.getFont().getName(), Font.BOLD, 12));
        this.add(questionsListTitle);
        this.add(Box.createVerticalStrut(5));

        this.add(questionsScrollPane);
        this.add(Box.createVerticalStrut(20));

        buttonsPanel.add(addQuestion);
        buttonsPanel.add(saveQuiz);
        buttonsPanel.add(previewQuiz);
        buttonsPanel.add(reorderQuestions);
        buttonsPanel.add(returnHome);
        
        this.add(buttonsPanel);
        
        updateQuestionsDisplay();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(saveQuiz)) {
            final CreateQuizState currentState = createQuizViewModel.getState();

            if (createQuizController != null) {
                createQuizController.execute(
                        currentState.getQuizName(),
                        currentState.getCategory(),
                        currentState.getQuestionsDetails(),
                        currentState.getCorrectAnswers(),
                        currentState.getUsername()
                );
            }
        } else if (evt.getSource().equals(addQuestion)) {
            CreateQuizState currentState = createQuizViewModel.getState();
            currentState.setEditingQuestionIndex(null);
            createQuizViewModel.firePropertyChange();
            
            if (validateQuestionViewModel != null) {
                validateQuestionViewModel.setState(new ValidateQuestionState());
                validateQuestionViewModel.firePropertyChange();
            }
            
            viewManagerModel.setState("validate question");
            viewManagerModel.firePropertyChange();
        } else if (evt.getSource().equals(reorderQuestions)) {
            CreateQuizState currentState = createQuizViewModel.getState();
            boolean newReorderMode = !currentState.isReorderMode();
            currentState.setReorderMode(newReorderMode);
            createQuizViewModel.setState(currentState);
            createQuizViewModel.firePropertyChange();
        } else if (evt.getSource().equals(returnHome)) {
            createQuizController.switchToLoggedInView();
            System.out.println("Return Home clicked");

        } else if (evt.getSource().equals(previewQuiz)) {
            CreateQuizState state = createQuizViewModel.getState();

            String quizName = state.getQuizName();
            String username = state.getUsername();
            String category = state.getCategory();
            List<List<String>> questions = state.getQuestionsDetails();
            List<String> answers = state.getCorrectAnswers();

            if (previewQuizController != null) {
                previewQuizController.execute(quizName, username, category, questions, answers);
            } else {
                System.out.println("Error: Preview Controller not set.");
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateQuizState state = (CreateQuizState) evt.getNewValue();
        setFields(state);
        if (state.getCreateError() != null) {
            errorField.setText(state.getCreateError());
            errorField.setForeground(Color.RED);
        } else {
            errorField.setText("");
        }
        
        if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
            String message = state.getSuccessMessage();

            state.setSuccessMessage(null);
            
            JOptionPane.showMessageDialog(
                null, 
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        
        updateQuestionsDisplay();
    }

    private void setFields(CreateQuizState state) {
        quizTitleField.setText(state.getQuizName());
        categoryField.setText(state.getCategory());
    }
    
    private void updateQuestionsDisplay() {
        questionsPanel.removeAll();
        questionPanels.clear();
        
        final CreateQuizState state = createQuizViewModel.getState();
        List<List<String>> questionsDetails = state.getQuestionsDetails();
        boolean isReorderMode = state.isReorderMode();
        
        // this updates button text based on reorder mode
        if (isReorderMode) {
            reorderQuestions.setText("Done Reordering");
            reorderQuestions.setBackground(Color.GREEN);
        } else {
            reorderQuestions.setText("Reorder Questions");
            reorderQuestions.setBackground(Color.YELLOW);
        }
        
        if (questionsDetails.isEmpty()) {
            JLabel noQuestionsLabel = new JLabel("No questions added yet. Click 'Add Question' to get started.");
            noQuestionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            noQuestionsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            questionsPanel.add(noQuestionsLabel);
        } else {
            for (int i = 0; i < questionsDetails.size(); i++) {
                JPanel questionPanel = new JPanel();
                questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
                questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                questionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                
                JLabel questionLabel = new JLabel("Q" + (i + 1) + ")");
                questionLabel.setPreferredSize(new Dimension(50, 30));
                questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, 12));
                
                String questionTitle = questionsDetails.get(i).isEmpty() ? "Untitled Question" : questionsDetails.get(i).get(0);
                JLabel questionTitleLabel = new JLabel(questionTitle);
                questionTitleLabel.setPreferredSize(new Dimension(300, 30));
                
                final int questionIndex = i;
                
                // added a reorder buttons if in reorder mode
                if (isReorderMode) {
                    JButton moveUpButton = new JButton("↑");
                    moveUpButton.setBackground(new Color(173, 216, 230));
                    moveUpButton.setFocusPainted(false);
                    moveUpButton.setEnabled(questionIndex > 0); // Disable for first question
                    moveUpButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            CreateQuizState currentState = createQuizViewModel.getState();
                            if (currentState.moveQuestionUp(questionIndex)) {
                                createQuizViewModel.setState(currentState);
                                createQuizViewModel.firePropertyChange();
                            }
                        }
                    });
                    
                    JButton moveDownButton = new JButton("↓");
                    moveDownButton.setFocusPainted(false);
                    moveDownButton.setEnabled(questionIndex < questionsDetails.size() - 1); // Disable for last question
                    moveDownButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            CreateQuizState currentState = createQuizViewModel.getState();
                            if (currentState.moveQuestionDown(questionIndex)) {
                                createQuizViewModel.setState(currentState);
                                createQuizViewModel.firePropertyChange();
                            }
                        }
                    });
                    
                    questionPanel.add(questionLabel);
                    questionPanel.add(Box.createHorizontalStrut(15));
                    questionPanel.add(questionTitleLabel);
                    questionPanel.add(Box.createHorizontalStrut(15));
                    questionPanel.add(moveUpButton);
                    questionPanel.add(Box.createHorizontalStrut(5));
                    questionPanel.add(moveDownButton);
                } else {
                    // goes back to the Edit button
                    JButton editQuestionButton = new JButton("Edit");
                    editQuestionButton.setBackground(Color.GREEN);
                    editQuestionButton.setPreferredSize(new Dimension(80, 30));
                    editQuestionButton.setFocusPainted(false);
                    editQuestionButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource().equals(editQuestionButton)) {
                                CreateQuizState createQuizState = createQuizViewModel.getState();
                                List<List<String>> questionsDetails = createQuizState.getQuestionsDetails();
                                List<String> correctAnswers = createQuizState.getCorrectAnswers();
                                
                                if (questionIndex < questionsDetails.size() && questionIndex < correctAnswers.size()) {
                                    createQuizState.setEditingQuestionIndex(questionIndex);

                                    List<String> questionDetails = questionsDetails.get(questionIndex);
                                    String correctAnswer = correctAnswers.get(questionIndex);

                                    ValidateQuestionState validateState = new ValidateQuestionState();
                                    if (!questionDetails.isEmpty()) {
                                        validateState.setTitle(questionDetails.get(0));
                                        if (questionDetails.size() > 1) {
                                            validateState.setOptions(new ArrayList<>(questionDetails.subList(1, questionDetails.size())));
                                        }
                                    }
                                    validateState.setAnswer(correctAnswer);
                                    validateState.setEditingIndex(questionIndex);

                                    validateQuestionViewModel.setState(validateState);
                                    validateQuestionViewModel.firePropertyChange();
                                    
                                    viewManagerModel.setState("validate question");
                                    viewManagerModel.firePropertyChange();
                                }
                            }
                        }
                    });
                    
                    questionPanel.add(questionLabel);
                    questionPanel.add(Box.createHorizontalStrut(15));
                    questionPanel.add(questionTitleLabel);
                    questionPanel.add(Box.createHorizontalStrut(15));
                    questionPanel.add(editQuestionButton);
                }
                
                questionsPanel.add(questionPanel);
                questionsPanel.add(Box.createVerticalStrut(8));
                questionPanels.add(questionPanel);
            }
        }
        
        questionsPanel.revalidate();
        questionsPanel.repaint();
    }

    public String getViewName() {
        return viewName;
    }

    public void setCreateQuizController(CreateQuizController createQuizController) {
        this.createQuizController = createQuizController;
    }

    public void setPreviewQuizController(PreviewQuizController controller) {
        this.previewQuizController = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void setValidateQuestionViewModel(ValidateQuestionViewModel validateQuestionViewModel) {
        this.validateQuestionViewModel = validateQuestionViewModel;
    }

}

