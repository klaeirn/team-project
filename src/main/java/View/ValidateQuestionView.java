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

        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < 4; i++) {
            optionFields[i] = new JTextField(20);
            
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
        deleteButton.setFocusPainted(false);

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(173, 216, 230));
        saveButton.setFocusPainted(false);

        deleteButton.addActionListener(this);
        saveButton.addActionListener(this);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.add(titleLabel);
        this.add(Box.createVerticalStrut(20));

        // Question input section
        final JLabel questionLabel = new JLabel("Question:");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(questionLabel);
        this.add(Box.createVerticalStrut(5));
        
        JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JScrollPane questionScrollPane = new JScrollPane(questionInputField);
        questionScrollPane.setPreferredSize(new Dimension(500, 20));
        questionScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
        this.add(questionPanel);
        this.add(Box.createVerticalStrut(20));

        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(instructionLabel);
        this.add(Box.createVerticalStrut(10));

        JPanel optionsGrid = new JPanel(new GridLayout(2, 2, 10, 10));
        optionsGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsGrid.setPreferredSize(new Dimension(500, 200));
        
        for (int i = 0; i < 4; i++) {
            JPanel optionPanel = new JPanel();
            optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
            optionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            optionPanel.setBackground(Color.WHITE);
            optionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            optionCheckboxes[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            optionPanel.add(optionCheckboxes[i]);
            optionPanel.add(Box.createVerticalStrut(5));
            
            optionFields[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            optionFields[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            optionPanel.add(optionFields[i]);
            
            optionsGrid.add(optionPanel);
        }
        
        this.add(optionsGrid);
        this.add(Box.createVerticalStrut(20));

        errorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(errorLabel);
        this.add(Box.createVerticalStrut(20));

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(saveButton);
        this.add(buttonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(saveButton)) {
            String title = questionInputField.getText().trim();


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
                questionInputField.setText("");
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
            questionInputField.setText("");
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

