package interface_adapter.share_quiz;
import entities.Quiz;
import use_cases.share_quiz.ShareQuizInputBoundary;
import use_cases.share_quiz.ShareQuizInputData;

public class ShareQuizController {
    private final ShareQuizInputBoundary shareQuizUseUseCaseInteractor;

    public ShareQuizController(ShareQuizInputBoundary shareQuizInputBoundary) {
        this.shareQuizUseUseCaseInteractor = shareQuizInputBoundary;
    }

    public void execute(Quiz quizToShare){
        final ShareQuizInputData shareQuizInputData = new ShareQuizInputData(quizToShare);

        shareQuizUseUseCaseInteractor.execute(shareQuizInputData);
    }
}
