package interface_adapter.preview_quiz;

import interface_adapter.ViewManagerModel;
import use_cases.preview_quiz.PreviewQuizOutputBoundary;
import use_cases.preview_quiz.PreviewQuizOutputData;

public class PreviewQuizPresenter implements PreviewQuizOutputBoundary{

    private final PreviewQuizViewModel previewQuizViewModel;
    private final ViewManagerModel viewManagerModel;

    public PreviewQuizPresenter(ViewManagerModel viewManagerModel,
                                PreviewQuizViewModel previewQuizViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.previewQuizViewModel = previewQuizViewModel;
    }


    @Override
    public void prepareSuccessView(PreviewQuizOutputData outputData) {
        final PreviewQuizState previewQuizState = previewQuizViewModel.getState();
        previewQuizState.setPreviewError(null);
        previewQuizState.setQuestions(outputData.getQuestions());
        previewQuizState.setCurrentQuestionIndex(0);

        previewQuizViewModel.firePropertyChange();

        viewManagerModel.setState(previewQuizViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String previewError) {
        final PreviewQuizState previewQuizState = previewQuizViewModel.getState();
        previewQuizState.setPreviewError(previewError);
        previewQuizState.setQuestions(null);
        previewQuizState.setCurrentQuestionIndex(0);

        previewQuizViewModel.firePropertyChange();

        viewManagerModel.setState(previewQuizViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
