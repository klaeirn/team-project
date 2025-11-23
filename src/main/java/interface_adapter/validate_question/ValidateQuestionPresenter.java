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
                              CreateQuizViewModel createQuizViewModel,ViewManagerModel viewManagerModel){
        this.validateQuestionViewModel = validateQuestionViewModel;
        this.createQuizViewModel = createQuizViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    public void prepareSuccessView(ValidateQuestionOutputData outputData) {
        final CreateQuizState createQuizState = createQuizViewModel.getState();
        final ValidateQuestionState validateState = validateQuestionViewModel.getState();
        
        Integer editingIndex = validateState.getEditingIndex();
        
        // if the question is being edited, replace the question at the specified index 
        if (editingIndex != null && editingIndex >= 0 && 
            editingIndex < createQuizState.getQuestionsDetails().size()) {
            createQuizState.getQuestionsDetails().set(editingIndex, outputData.getQuestionDetails());
            createQuizState.getCorrectAnswers().set(editingIndex, outputData.getAnswer());
            createQuizState.setEditingQuestionIndex(null);
        } else {
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
    public void prepareFailView(String errorMessage){
        final ValidateQuestionState validateQuestionState = validateQuestionViewModel.getState();
        validateQuestionState.setValidationError(errorMessage);

        validateQuestionViewModel.getState().setValid(false);

        validateQuestionViewModel.setState(validateQuestionState);
        validateQuestionViewModel.firePropertyChange();
    }
}
