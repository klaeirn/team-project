package interface_adapter.validate_question;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_quiz.CreateQuizState;
import interface_adapter.create_quiz.CreateQuizViewModel;
import use_cases.validate_question.ValidateQuestionOutputBoundary;
import use_cases.validate_question.ValidateQuestionOutputData;

public class ValidateQuestionPresenter implements ValidateQuestionOutputBoundary {
    private final ValidateQuestionViewModel validateQuestionViewModel;
    private final CreateQuizViewModel createQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public ValidateQuestionPresenter(ValidateQuestionViewModel validateQuestionViewModel,
                              CreateQuizViewModel createQuizViewModel, ViewManagerModel viewManagerModel) {
        this.validateQuestionViewModel = validateQuestionViewModel;
        this.createQuizViewModel = createQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the success view after validating a question.
     * Updates the create quiz state with the validated question and switches back to the create quiz view.
     *
     * @param outputData : the output data containing the validated question details and answer
     */
    public void prepareSuccessView(ValidateQuestionOutputData outputData) {
        final CreateQuizState createQuizState = createQuizViewModel.getState();
        final ValidateQuestionState validateState = validateQuestionViewModel.getState();
        
        final Integer editingIndex = validateState.getEditingIndex();
        // if the question is being edited, replace the question at the specified index
        if (editingIndex != null && editingIndex >= 0
                && editingIndex < createQuizState.getQuestionsDetails().size()) {
            createQuizState.getQuestionsDetails().set(editingIndex, outputData.getQuestionDetails());
            createQuizState.getCorrectAnswers().set(editingIndex, outputData.getAnswer());
            createQuizState.setEditingQuestionIndex(null);
        }
        else {
            createQuizState.getQuestionsDetails().add(outputData.getQuestionDetails());
            createQuizState.getCorrectAnswers().add(outputData.getAnswer());
        }
        
        createQuizViewModel.firePropertyChange();

        // clear the validate question state once everything has been saved to the create quiz
        validateQuestionViewModel.setState(new ValidateQuestionState());
        validateQuestionViewModel.getState().setValid(true);
        validateQuestionViewModel.firePropertyChange();

        // change view back to the create quiz view
        viewManagerModel.setState(createQuizViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    /**
     * Prepares the failure view with an error message when question validation fails.
     *
     * @param errorMessage : the error message to display
     */
    public void prepareFailView(String errorMessage) {
        final ValidateQuestionState validateQuestionState = validateQuestionViewModel.getState();
        validateQuestionState.setValidationError(errorMessage);

        validateQuestionViewModel.getState().setValid(false);

        validateQuestionViewModel.setState(validateQuestionState);
        validateQuestionViewModel.firePropertyChange();
    }
}
