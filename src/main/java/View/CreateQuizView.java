package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.create_quiz.CreateQuizViewModel;

// importing validate question view model and state bc it needs to know about the question that is being edited
// does not violate clean architecture because its a view and not a use case
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
    
    private CreateQuizController createQuizController = null;
    private ViewManagerModel viewManagerModel;
    private ValidateQuestionViewModel validateQuestionViewModel;

    private final JPanel questionsPanel = new JPanel();
    private final List<JPanel> questionPanels = new ArrayList<>();

    public CreateQuizView(CreateQuizViewModel createQuizViewModel) {
        this.createQuizViewModel = createQuizViewModel;
        this.createQuizViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Quiz Editing");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(title.getFont().getName(), Font.BOLD, 16));


        final LabelTextPanel quizTitleInfo = new LabelTextPanel(
                new JLabel("Quiz Title"), quizTitleField);
        
        final LabelTextPanel categoryInfo = new LabelTextPanel(
                new JLabel("Category"), categoryField);

        final JLabel questionsLabel = new JLabel("Questions:");
        questionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionsPanel.setBorder(BorderFactory.createTitledBorder("Questions List"));

        final JPanel actionButtons = new JPanel();
        actionButtons.setLayout(new BoxLayout(actionButtons, BoxLayout.Y_AXIS));
        
        addQuestion = new JButton("Add Question");
        addQuestion.setBackground(Color.RED);
        addQuestion.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        reorderQuestions = new JButton("Reorder Questions");
        reorderQuestions.setBackground(Color.YELLOW);
        reorderQuestions.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        saveQuiz = new JButton("Save Quiz");
        saveQuiz.setBackground(new Color(173, 216, 230)); 
        saveQuiz.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        returnHome = new JButton("Return Home");
        returnHome.setBackground(new Color(173, 216, 230)); 
        returnHome.setAlignmentX(Component.LEFT_ALIGNMENT);

        addQuestion.addActionListener(this);
        reorderQuestions.addActionListener(this);
        saveQuiz.addActionListener(this);
        returnHome.addActionListener(this);

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
    
        this.add(title);
        this.add(Box.createVerticalStrut(10));
        this.add(quizTitleInfo);
        this.add(categoryInfo);
        this.add(errorField);
        this.add(Box.createVerticalStrut(10));
        this.add(questionsLabel);
        this.add(questionsPanel);
        this.add(Box.createVerticalStrut(10));
        
        actionButtons.add(addQuestion);
        actionButtons.add(Box.createVerticalStrut(5));
        actionButtons.add(reorderQuestions);
        actionButtons.add(Box.createVerticalStrut(5));
        actionButtons.add(saveQuiz);
        actionButtons.add(Box.createVerticalStrut(5));
        actionButtons.add(returnHome);
        
        this.add(actionButtons);
        
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
                        currentState.getCorrectAnswers()
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
            // TODO: still need to implement reorder functionality
            System.out.println("Reorder Questions clicked");
        } else if (evt.getSource().equals(returnHome)) {
            createQuizController.switchToLoggedInView();
            System.out.println("Return Home clicked");
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
        
        if (questionsDetails.isEmpty()) {
            JLabel noQuestionsLabel = new JLabel("No questions added yet. Click 'Add Question' to get started.");
            noQuestionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            questionsPanel.add(noQuestionsLabel);
        } else {
            for (int i = 0; i < questionsDetails.size(); i++) {
                JPanel questionPanel = new JPanel();
                questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
                questionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                JLabel questionLabel = new JLabel("Q" + (i + 1) + ")");
                questionLabel.setPreferredSize(new Dimension(50, 25));
                
                String questionTitle = questionsDetails.get(i).isEmpty() ? "Untitled Question" : questionsDetails.get(i).get(0);
                JLabel questionTitleLabel = new JLabel(questionTitle);
                questionTitleLabel.setPreferredSize(new Dimension(200, 25));
                
                JButton editQuestionButton = new JButton("Edit Question");
                editQuestionButton.setBackground(Color.GREEN);
                final int questionIndex = i;
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
                questionPanel.add(Box.createHorizontalStrut(10));
                questionPanel.add(questionTitleLabel);
                questionPanel.add(Box.createHorizontalStrut(10));
                questionPanel.add(editQuestionButton);
                
                questionsPanel.add(questionPanel);
                questionsPanel.add(Box.createVerticalStrut(5));
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

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void setValidateQuestionViewModel(ValidateQuestionViewModel validateQuestionViewModel) {
        this.validateQuestionViewModel = validateQuestionViewModel;
    }

}

