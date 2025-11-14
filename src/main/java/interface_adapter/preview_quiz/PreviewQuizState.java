package interface_adapter.preview_quiz;

import use_cases.preview_quiz.PreviewQuizOutputData;
import java.util.List;

public class PreviewQuizState {

    private List<PreviewQuizOutputData.QuestionData> questions;
    private int currentQuestionIndex;
    private String previewError;

    public List<PreviewQuizOutputData.QuestionData> getQuestions() { return questions; }
    public void setQuestions(List<PreviewQuizOutputData.QuestionData> questions) { this.questions = questions; }

    public int getCurrentQuestionIndex() { return currentQuestionIndex; }
    public void setCurrentQuestionIndex(int currentQuestionIndex) { this.currentQuestionIndex = currentQuestionIndex; }

    public String getPreviewError() { return previewError; }
    public void setPreviewError(String previewError) { this.previewError = previewError; }
}
