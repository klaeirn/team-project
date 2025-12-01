package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.create_quiz.CreateQuizViewModel;
// importing validate question view model and state bc it needs to know about the question that is being edited
// does not violate clean architecture because its a view and not a use case
import interface_adapter.preview_quiz.PreviewQuizController;
import interface_adapter.validate_question.ValidateQuestionState;
import interface_adapter.validate_question.ValidateQuestionViewModel;

/**
 * The View for creating and editing quizzes.
 * Allows users to add questions, reorder them, preview the quiz, and save it.
 */
public class CreateQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int TEXT_FIELD_COLUMNS = 15;
    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 25;
    private static final int BORDER_PADDING = 10;
    private static final int SCROLL_PANE_WIDTH = 500;
    private static final int SCROLL_PANE_HEIGHT = 200;
    private static final int BUTTON_HGAP = 10;
    private static final int BUTTON_VGAP = 5;
    private static final int TITLE_FONT_SIZE = 16;
    private static final int LABEL_FONT_SIZE = 12;
    private static final int QUESTION_LABEL_WIDTH = 50;
    private static final int QUESTION_LABEL_HEIGHT = 30;
    private static final int QUESTION_TITLE_WIDTH = 300;
    private static final int EDIT_BUTTON_WIDTH = 80;
    private static final int HORIZONTAL_STRUT_LARGE = 15;
    private static final int HORIZONTAL_STRUT_SMALL = 5;
    private static final int VERTICAL_STRUT_LARGE = 20;
    private static final int VERTICAL_STRUT_MEDIUM = 10;
    private static final int VERTICAL_STRUT_SMALL = 8;
    private static final int VERTICAL_STRUT_TINY = 5;
    private static final Color BUTTON_COLOR_LIGHT_BLUE = new Color(173, 216, 230);

    private final String viewName = "create quiz";
    private final CreateQuizViewModel createQuizViewModel;

    private final JTextField quizTitleField = new JTextField(TEXT_FIELD_COLUMNS);
    private final JTextField categoryField = new JTextField(TEXT_FIELD_COLUMNS);
    private final JLabel errorField = new JLabel();
    
    private final JButton addQuestion;
    private final JButton reorderQuestions;
    private final JButton saveQuiz;
    private final JButton returnHome;
    private final JButton previewQuiz;
    
    private CreateQuizController createQuizController;
    private ViewManagerModel viewManagerModel;
    private ValidateQuestionViewModel validateQuestionViewModel;

    private PreviewQuizController previewQuizController;

    private final JPanel questionsPanel = new JPanel();
    private final JScrollPane questionsScrollPane;
    private final List<JPanel> questionPanels = new ArrayList<>();

    /**
     * Constructs a CreateQuizView with the given view model.
     *
     * @param createQuizViewModel : the view model for creating quizzes
     */
    public CreateQuizView(CreateQuizViewModel createQuizViewModel) {
        this.createQuizViewModel = createQuizViewModel;
        this.createQuizViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Quiz Editing");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, TITLE_FONT_SIZE));

        final JLabel quizTitleLabel = new JLabel("Quiz Title:");
        quizTitleLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        final LabelTextPanel quizTitleInfo = new LabelTextPanel(quizTitleLabel, quizTitleField);
        quizTitleInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        final LabelTextPanel categoryInfo = new LabelTextPanel(categoryLabel, categoryField);
        categoryInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(BORDER_PADDING, BORDER_PADDING, BORDER_PADDING, BORDER_PADDING)));

        questionsScrollPane = new JScrollPane(questionsPanel);
        questionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questionsScrollPane.setPreferredSize(new Dimension(SCROLL_PANE_WIDTH, SCROLL_PANE_HEIGHT));
        questionsScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, BUTTON_HGAP, BUTTON_VGAP));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        addQuestion = new JButton("Add Question");
        addQuestion.setBackground(Color.RED);
        addQuestion.setFocusPainted(false);
        
        saveQuiz = new JButton("Save Quiz");
        saveQuiz.setBackground(BUTTON_COLOR_LIGHT_BLUE); 
        saveQuiz.setFocusPainted(false);
        
        previewQuiz = new JButton("Preview Quiz");
        previewQuiz.setBackground(BUTTON_COLOR_LIGHT_BLUE);
        previewQuiz.setFocusPainted(false);
        
        reorderQuestions = new JButton("Reorder Questions");
        reorderQuestions.setBackground(Color.YELLOW);
        reorderQuestions.setFocusPainted(false);
        
        returnHome = new JButton("Return Home");
        returnHome.setBackground(BUTTON_COLOR_LIGHT_BLUE); 
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
        this.setBorder(BorderFactory.createEmptyBorder(VERTICAL_STRUT_LARGE, VERTICAL_STRUT_LARGE, VERTICAL_STRUT_LARGE,
                VERTICAL_STRUT_LARGE));
    
        this.add(title);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

        final JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        formPanel.add(quizTitleInfo);
        formPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_MEDIUM));
        formPanel.add(categoryInfo);
        formPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
        errorField.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        formPanel.add(errorField);
        
        this.add(formPanel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

        final JLabel questionsListTitle = new JLabel("Questions List");
        questionsListTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionsListTitle.setFont(new Font(questionsListTitle.getFont().getName(), Font.BOLD, LABEL_FONT_SIZE));
        this.add(questionsListTitle);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_TINY));

        this.add(questionsScrollPane);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

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
        }
        else if (evt.getSource().equals(addQuestion)) {
            final CreateQuizState currentState = createQuizViewModel.getState();
            currentState.setEditingQuestionIndex(null);
            createQuizViewModel.firePropertyChange();
            
            if (validateQuestionViewModel != null) {
                validateQuestionViewModel.setState(new ValidateQuestionState());
                validateQuestionViewModel.firePropertyChange();
            }
            
            viewManagerModel.setState("validate question");
            viewManagerModel.firePropertyChange();
        }
        else if (evt.getSource().equals(reorderQuestions)) {
            final CreateQuizState currentState = createQuizViewModel.getState();
            final boolean newReorderMode = !currentState.isReorderMode();
            currentState.setReorderMode(newReorderMode);
            createQuizViewModel.setState(currentState);
            createQuizViewModel.firePropertyChange();
        }
        else if (evt.getSource().equals(returnHome)) {
            createQuizController.switchToLoggedInView();
            System.out.println("Return Home clicked");

        }
        else if (evt.getSource().equals(previewQuiz)) {
            final CreateQuizState state = createQuizViewModel.getState();

            final String quizName = state.getQuizName();
            final String username = state.getUsername();
            final String category = state.getCategory();
            final List<List<String>> questions = state.getQuestionsDetails();
            final List<String> answers = state.getCorrectAnswers();

            if (previewQuizController != null) {
                previewQuizController.execute(quizName, username, category, questions, answers);
            }
            else {
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
        }
        else {
            errorField.setText("");
        }
        
        if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
            final String message = state.getSuccessMessage();

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
        final List<List<String>> questionsDetails = state.getQuestionsDetails();
        final boolean isReorderMode = state.isReorderMode();
        
        // this updates button text based on reorder mode
        if (isReorderMode) {
            reorderQuestions.setText("Done Reordering");
            reorderQuestions.setBackground(Color.GREEN);
        }
        else {
            reorderQuestions.setText("Reorder Questions");
            reorderQuestions.setBackground(Color.YELLOW);
        }
        
        if (questionsDetails.isEmpty()) {
            final JLabel noQuestionsLabel = new JLabel("No questions added yet. Click 'Add Question' to get started.");
            noQuestionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            noQuestionsLabel.setBorder(BorderFactory.createEmptyBorder(BORDER_PADDING, BORDER_PADDING,
                    BORDER_PADDING, BORDER_PADDING));
            questionsPanel.add(noQuestionsLabel);
        }
        else {
            for (int i = 0; i < questionsDetails.size(); i++) {
                final JPanel questionPanel = new JPanel();
                questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
                questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                questionPanel.setBorder(BorderFactory.createEmptyBorder(VERTICAL_STRUT_TINY, VERTICAL_STRUT_TINY,
                        VERTICAL_STRUT_TINY, VERTICAL_STRUT_TINY));
                
                final JLabel questionLabel = new JLabel("Q" + (i + 1) + ")");
                questionLabel.setPreferredSize(new Dimension(QUESTION_LABEL_WIDTH, QUESTION_LABEL_HEIGHT));
                questionLabel.setFont(new Font(questionLabel.getFont().getName(), Font.BOLD, LABEL_FONT_SIZE));

                final String questionTitle = questionsDetails.get(i).isEmpty() ? "Untitled Question"
                        : questionsDetails.get(i).get(0);
                final JLabel questionTitleLabel = new JLabel(questionTitle);
                questionTitleLabel.setPreferredSize(new Dimension(QUESTION_TITLE_WIDTH, QUESTION_LABEL_HEIGHT));
                
                final int questionIndex = i;
                // added a reorder buttons if in reorder mode
                if (isReorderMode) {
                    final JButton moveUpButton = new JButton("↑");
                    moveUpButton.setBackground(BUTTON_COLOR_LIGHT_BLUE);
                    moveUpButton.setFocusPainted(false);
                    moveUpButton.setEnabled(questionIndex > 0);
                    // disabled for first question
                    moveUpButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final CreateQuizState currentState = createQuizViewModel.getState();
                            if (currentState.moveQuestionUp(questionIndex)) {
                                createQuizViewModel.setState(currentState);
                                createQuizViewModel.firePropertyChange();
                            }
                        }
                    });

                    final JButton moveDownButton = new JButton("↓");
                    moveDownButton.setFocusPainted(false);
                    moveDownButton.setEnabled(questionIndex < questionsDetails.size() - 1);
                    // disable for last question
                    moveDownButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            final CreateQuizState currentState = createQuizViewModel.getState();
                            if (currentState.moveQuestionDown(questionIndex)) {
                                createQuizViewModel.setState(currentState);
                                createQuizViewModel.firePropertyChange();
                            }
                        }
                    });
                    
                    questionPanel.add(questionLabel);
                    questionPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT_LARGE));
                    questionPanel.add(questionTitleLabel);
                    questionPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT_LARGE));
                    questionPanel.add(moveUpButton);
                    questionPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT_SMALL));
                    questionPanel.add(moveDownButton);
                }
                else {
                    // goes back to the Edit button
                    final JButton editQuestionButton = new JButton("Edit");
                    editQuestionButton.setBackground(Color.GREEN);
                    editQuestionButton.setPreferredSize(new Dimension(EDIT_BUTTON_WIDTH, QUESTION_LABEL_HEIGHT));
                    editQuestionButton.setFocusPainted(false);
                    editQuestionButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource().equals(editQuestionButton)) {
                                final CreateQuizState createQuizState = createQuizViewModel.getState();
                                final List<List<String>> questionsDetails = createQuizState.getQuestionsDetails();
                                final List<String> correctAnswers = createQuizState.getCorrectAnswers();
                                
                                if (questionIndex < questionsDetails.size() && questionIndex < correctAnswers.size()) {
                                    createQuizState.setEditingQuestionIndex(questionIndex);

                                    final List<String> questionDetails = questionsDetails.get(questionIndex);
                                    final String correctAnswer = correctAnswers.get(questionIndex);

                                    final ValidateQuestionState validateState = new ValidateQuestionState();
                                    if (!questionDetails.isEmpty()) {
                                        validateState.setTitle(questionDetails.get(0));
                                        if (questionDetails.size() > 1) {
                                            validateState.setOptions(new ArrayList<>(questionDetails.subList(1,
                                                    questionDetails.size())));
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
                    questionPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT_LARGE));
                    questionPanel.add(questionTitleLabel);
                    questionPanel.add(Box.createHorizontalStrut(HORIZONTAL_STRUT_LARGE));
                    questionPanel.add(editQuestionButton);
                }
                
                questionsPanel.add(questionPanel);
                questionsPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
                questionPanels.add(questionPanel);
            }
        }
        
        questionsPanel.revalidate();
        questionsPanel.repaint();
    }

    /**
     * Method to the view name.
     *
     * @return the name of this view
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Method to the create quiz controller.
     *
     * @param createQuizController : the controller for creating quizzes
     */
    public void setCreateQuizController(CreateQuizController createQuizController) {
        this.createQuizController = createQuizController;
    }

    /**
     * Method to the preview quiz controller.
     *
     * @param controller : the controller for previewing quizzes
     */
    public void setPreviewQuizController(PreviewQuizController controller) {
        this.previewQuizController = controller;
    }

    /**
     * Method to set the view manager model.
     *
     * @param viewManagerModel : the view manager model for managing view transitions
     */
    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Method to the validate question view model.
     *
     * @param validateQuestionViewModel : the view model for validating questions
     */
    public void setValidateQuestionViewModel(ValidateQuestionViewModel validateQuestionViewModel) {
        this.validateQuestionViewModel = validateQuestionViewModel;
    }

}
