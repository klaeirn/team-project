package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizViewModel;
import interface_adapter.create_quiz.CreateQuizState;
// importing create quiz viewmodel and state bc it needs take the changed info and update the create quiz state with the edited question
// does not violate clean architecture because its a view and not a use case

import interface_adapter.validate_question.ValidateQuestionController;
import interface_adapter.validate_question.ValidateQuestionState;
import interface_adapter.validate_question.ValidateQuestionViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ValidateQuestionView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "validate question";
    private final ValidateQuestionViewModel validateQuestionViewModel;
    private ValidateQuestionController validateQuestionController;
    private ViewManagerModel viewManagerModel;
    private CreateQuizViewModel createQuizViewModel;

    private final JTextArea questionInputField = new JTextArea(3, 30);

    private final JLabel instructionLabel = new JLabel("Check the box for the correct answer");
    
    private final JTextField[] optionFields = new JTextField[4];
    private final JCheckBox[] optionCheckboxes = new JCheckBox[4];
    private final ButtonGroup checkboxGroup = new ButtonGroup();
    
    private final JButton deleteButton;
    private final JButton saveButton;
    
    private final JLabel errorLabel = new JLabel();
    private final JLabel titleLabel;

    public ValidateQuestionView(ValidateQuestionViewModel validateQuestionViewModel) {
        this.validateQuestionViewModel = validateQuestionViewModel;
        this.validateQuestionViewModel.addPropertyChangeListener(this);

        titleLabel = new JLabel("Add Question");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 16));

      
        questionInputField.setLineWrap(true);
        questionInputField.setWrapStyleWord(true);

        questionInputField.setText("Type question here...");
        questionInputField.setForeground(Color.GRAY);
        questionInputField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (questionInputField.getText().equals("Type question here...")) {
                    questionInputField.setText("");
                    questionInputField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (questionInputField.getText().trim().isEmpty()) {
                    questionInputField.setText("Type question here...");
                    questionInputField.setForeground(Color.GRAY);
                }
            }
        });

    
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < 4; i++) {
            optionFields[i] = new JTextField(15);
            
            optionCheckboxes[i] = new JCheckBox();
            optionCheckboxes[i].setText("Option " + (i + 1));
            checkboxGroup.add(optionCheckboxes[i]);
            
            final int index = i;
            optionCheckboxes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (optionCheckboxes[index].isSelected()) {
                        if (optionFields[index].getText().trim().isEmpty()) {
                            optionCheckboxes[index].setSelected(false);
                        }
                    }
                }
            });
        }

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.RED);
        deleteButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(173, 216, 230));
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        deleteButton.addActionListener(this);
        saveButton.addActionListener(this);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(titleLabel);
        this.add(Box.createVerticalStrut(10));

        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JScrollPane questionScrollPane = new JScrollPane(questionInputField);
        questionScrollPane.setPreferredSize(new Dimension(400, 80));
        questionScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
        this.add(questionPanel);
        this.add(Box.createVerticalStrut(10));
        
        JPanel instructionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        instructionPanel.add(instructionLabel);
        this.add(instructionPanel);
        this.add(Box.createVerticalStrut(10));
        
        JPanel optionsGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsGrid.setPreferredSize(new Dimension(400, 200));
        
        for (int i = 0; i < 4; i++) {
            JPanel optionPanel = new JPanel(new BorderLayout());
            optionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            optionPanel.setBackground(Color.WHITE);
            
            JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            checkboxPanel.setBackground(Color.WHITE);
            checkboxPanel.add(optionCheckboxes[i]);
            optionPanel.add(checkboxPanel, BorderLayout.NORTH);
            
            optionFields[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            optionPanel.add(optionFields[i], BorderLayout.CENTER);
            
            optionsGrid.add(optionPanel);
        }
        
        this.add(optionsGrid);
        this.add(Box.createVerticalStrut(10));
        
        final JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.add(deleteButton);
        buttons.add(saveButton);
        this.add(buttons);
        
        this.add(Box.createVerticalStrut(10));
        
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(errorLabel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(saveButton)) {
            String titleText = questionInputField.getText();
            if (titleText.equals("Type question here...")) {
                titleText = "";
            }
            String title = titleText.trim();


            List<String> options = new ArrayList<>();
            String answer = null;
            for (int i = 0; i < 4; i++) {
                String optionText = optionFields[i].getText().trim();
                options.add(optionText);
                if (optionCheckboxes[i].isSelected()) {
                    answer = optionText;
                }
            }

            validateQuestionController.execute(title, options, answer != null ? answer : "");

            // clear all UI fields directly
            if (validateQuestionViewModel.getState().isValid()){
                questionInputField.setText("Type question here...");
                questionInputField.setForeground(Color.GRAY);
                for (int i = 0; i < 4; i++) {
                    optionFields[i].setText("");
                    optionCheckboxes[i].setSelected(false);
                }
                errorLabel.setText("");
            }


        } else if (evt.getSource().equals(deleteButton)) {
            ValidateQuestionState currentState = validateQuestionViewModel.getState();
            Integer editingIndex = currentState.getEditingIndex();
            
            // if editing, remove the question from CreateQuizState
            if (editingIndex != null && createQuizViewModel != null) {
                CreateQuizState createQuizState = createQuizViewModel.getState();
                if (editingIndex >= 0 && editingIndex < createQuizState.getQuestionsDetails().size()) {
                    createQuizState.getQuestionsDetails().remove(editingIndex.intValue());
                    createQuizState.getCorrectAnswers().remove(editingIndex.intValue());
                    createQuizState.setEditingQuestionIndex(null);
                    createQuizViewModel.firePropertyChange();
                }
            }
            
            // clear all UI fields directly
            questionInputField.setText("Type question here...");
            questionInputField.setForeground(Color.GRAY);
            for (int i = 0; i < 4; i++) {
                optionFields[i].setText("");
                optionCheckboxes[i].setSelected(false);
            }
            errorLabel.setText("");
            
            validateQuestionViewModel.setState(new ValidateQuestionState());
            validateQuestionViewModel.firePropertyChange();
            
            viewManagerModel.setState("create quiz");
            viewManagerModel.firePropertyChange();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ValidateQuestionState state = (ValidateQuestionState) evt.getNewValue();
        if (state != null) {
            String error = state.getValidationError();
            if (error != null && !error.isEmpty()) {
                errorLabel.setText(error);
            } else {
                errorLabel.setText("");
            }
            
            // this populates the fields if question data is present (edit mode)
            if (state.getEditingIndex() != null && !state.getTitle().isEmpty()) {
                titleLabel.setText("Edit Question");
                
                String title = state.getTitle();
                questionInputField.setText(title);
                questionInputField.setForeground(Color.BLACK);
                
                List<String> options = state.getOptions();
                String answer = state.getAnswer();
                
                for (int i = 0; i < 4; i++) {
                    if (i < options.size()) {
                        optionFields[i].setText(options.get(i));
                        if (options.get(i).equals(answer)) {
                            optionCheckboxes[i].setSelected(true);
                        } else {
                            optionCheckboxes[i].setSelected(false);
                        }
                    } else {
                        optionFields[i].setText("");
                        optionCheckboxes[i].setSelected(false);
                    }
                }
            } else {
                titleLabel.setText("Add Question");
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setValidateQuestionController(ValidateQuestionController controller) {
        this.validateQuestionController = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void setCreateQuizViewModel(CreateQuizViewModel createQuizViewModel) {
        this.createQuizViewModel = createQuizViewModel;
    }
}

