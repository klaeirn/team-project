package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.create_quiz.CreateQuizViewModel;
// importing create quiz viewmodel and state bc it needs take the changed info and update the create quiz state
// with the edited question
// does not violate clean architecture because its a view and not a use case
import interface_adapter.validate_question.ValidateQuestionController;
import interface_adapter.validate_question.ValidateQuestionState;
import interface_adapter.validate_question.ValidateQuestionViewModel;

public class ValidateQuestionView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int QUESTION_TEXT_AREA_ROWS = 3;
    private static final int QUESTION_TEXT_AREA_COLUMNS = 30;
    private static final int NUMBER_OF_OPTIONS = 4;
    private static final int OPTION_TEXT_FIELD_COLUMNS = 20;
    private static final int TITLE_FONT_SIZE = 16;
    private static final int BORDER_PADDING_LARGE = 20;
    private static final int BORDER_PADDING_MEDIUM = 10;
    private static final int BORDER_PADDING_SMALL = 5;
    private static final int VERTICAL_STRUT_LARGE = 20;
    private static final int VERTICAL_STRUT_MEDIUM = 10;
    private static final int VERTICAL_STRUT_SMALL = 5;
    private static final int OPTIONS_GRID = 2;
    private static final int OPTIONS_GRID_GAP = 10;
    private static final int OPTIONS_PANEL_WIDTH = 500;
    private static final int OPTIONS_PANEL_HEIGHT = 200;
    private static final int QUESTION_SCROLL_PANE_HEIGHT = 20;
    private static final int BUTTON_PANEL_HGAP = 10;
    private static final int BUTTON_PANEL_VGAP = 5;
    private static final int LIGHT_BLUE_RED = 173;
    private static final int LIGHT_BLUE_GREEN = 216;
    private static final int LIGHT_BLUE_BLUE = 230;

    private final String viewName = "validate question";
    private final ValidateQuestionViewModel validateQuestionViewModel;
    private ValidateQuestionController validateQuestionController;
    private ViewManagerModel viewManagerModel;
    private CreateQuizViewModel createQuizViewModel;

    private final JTextArea questionInputField = new JTextArea(QUESTION_TEXT_AREA_ROWS, QUESTION_TEXT_AREA_COLUMNS);

    private final JLabel instructionLabel = new JLabel("Check the box for the correct answer");
    
    private final JTextField[] optionFields = new JTextField[NUMBER_OF_OPTIONS];
    private final JCheckBox[] optionCheckboxes = new JCheckBox[NUMBER_OF_OPTIONS];
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
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, TITLE_FONT_SIZE));

        questionInputField.setLineWrap(true);
        questionInputField.setWrapStyleWord(true);

        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
            optionFields[i] = new JTextField(OPTION_TEXT_FIELD_COLUMNS);
            
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
        saveButton.setBackground(new Color(LIGHT_BLUE_RED, LIGHT_BLUE_GREEN, LIGHT_BLUE_BLUE));
        saveButton.setFocusPainted(false);

        deleteButton.addActionListener(this);
        saveButton.addActionListener(this);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(BORDER_PADDING_LARGE, BORDER_PADDING_LARGE,
                BORDER_PADDING_LARGE, BORDER_PADDING_LARGE));

        this.add(titleLabel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

        // Question input section
        final JLabel questionLabel = new JLabel("Question:");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(questionLabel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));

        final JPanel questionPanel = new JPanel(new BorderLayout());
        questionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        final JScrollPane questionScrollPane = new JScrollPane(questionInputField);
        questionScrollPane.setPreferredSize(new Dimension(OPTIONS_PANEL_WIDTH, QUESTION_SCROLL_PANE_HEIGHT));
        questionScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(BORDER_PADDING_SMALL, BORDER_PADDING_SMALL,
                    BORDER_PADDING_SMALL, BORDER_PADDING_SMALL)));
        questionPanel.add(questionScrollPane, BorderLayout.CENTER);
        this.add(questionPanel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(instructionLabel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_MEDIUM));

        final JPanel optionsGrid = new JPanel(new GridLayout(OPTIONS_GRID, OPTIONS_GRID,
                OPTIONS_GRID_GAP, OPTIONS_GRID_GAP));
        optionsGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsGrid.setPreferredSize(new Dimension(OPTIONS_PANEL_WIDTH, OPTIONS_PANEL_HEIGHT));
        
        for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
            final JPanel optionPanel = new JPanel();
            optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
            optionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(BORDER_PADDING_MEDIUM, BORDER_PADDING_MEDIUM,
                        BORDER_PADDING_MEDIUM, BORDER_PADDING_MEDIUM)));
            optionPanel.setBackground(Color.WHITE);
            optionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            optionCheckboxes[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            optionPanel.add(optionCheckboxes[i]);
            optionPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SMALL));
            
            optionFields[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            optionFields[i].setBorder(BorderFactory.createEmptyBorder(BORDER_PADDING_SMALL, BORDER_PADDING_SMALL,
                    BORDER_PADDING_SMALL, BORDER_PADDING_SMALL));
            optionPanel.add(optionFields[i]);
            
            optionsGrid.add(optionPanel);
        }
        
        this.add(optionsGrid);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

        errorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.add(errorLabel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_LARGE));

        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, BUTTON_PANEL_HGAP, BUTTON_PANEL_VGAP));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(saveButton);
        this.add(buttonsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(saveButton)) {
            final String title = questionInputField.getText().trim();

            final List<String> options = new ArrayList<>();
            String answer = null;
            for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
                final String optionText = optionFields[i].getText().trim();
                options.add(optionText);
                if (optionCheckboxes[i].isSelected()) {
                    answer = optionText;
                }
            }

            validateQuestionController.execute(title, options, answer != null ? answer : "");

            // clear all UI fields directly
            if (validateQuestionViewModel.getState().isValid()) {
                questionInputField.setText("");
                for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
                    optionFields[i].setText("");
                    optionCheckboxes[i].setSelected(false);
                }
                errorLabel.setText("");
            }

        }
        else if (evt.getSource().equals(deleteButton)) {
            final ValidateQuestionState currentState = validateQuestionViewModel.getState();
            final Integer editingIndex = currentState.getEditingIndex();
            
            // if editing, remove the question from CreateQuizState
            if (editingIndex != null && createQuizViewModel != null) {
                final CreateQuizState createQuizState = createQuizViewModel.getState();
                if (editingIndex >= 0 && editingIndex < createQuizState.getQuestionsDetails().size()) {
                    createQuizState.getQuestionsDetails().remove(editingIndex.intValue());
                    createQuizState.getCorrectAnswers().remove(editingIndex.intValue());
                    createQuizState.setEditingQuestionIndex(null);
                    createQuizViewModel.firePropertyChange();
                }
            }
            
            // clear all UI fields directly
            questionInputField.setText("");
            for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
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
        final ValidateQuestionState state = (ValidateQuestionState) evt.getNewValue();
        if (state != null) {
            final String error = state.getValidationError();
            if (error != null && !error.isEmpty()) {
                errorLabel.setText(error);
            }
            else {
                errorLabel.setText("");
            }
            
            // this populates the fields if question data is present (edit mode)
            if (state.getEditingIndex() != null && !state.getTitle().isEmpty()) {
                titleLabel.setText("Edit Question");

                final String title = state.getTitle();
                questionInputField.setText(title);
                questionInputField.setForeground(Color.BLACK);

                final List<String> options = state.getOptions();
                final String answer = state.getAnswer();
                
                for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
                    if (i < options.size()) {
                        optionFields[i].setText(options.get(i));
                        if (options.get(i).equals(answer)) {
                            optionCheckboxes[i].setSelected(true);
                        }
                        else {
                            optionCheckboxes[i].setSelected(false);
                        }
                    }
                    else {
                        optionFields[i].setText("");
                        optionCheckboxes[i].setSelected(false);
                    }
                }
            }
            else {
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

