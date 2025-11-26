package use_cases.share_quiz;

import use_cases.change_username.*;

import java.sql.SQLOutput;

public class ShareQuizInteractor implements ShareQuizInputBoundary {

    private final ShareQuizDataAccessInterface dataAccessInterface;
    private final ShareQuizOutputBoundary shareQuizPresenter;


    public ShareQuizInteractor(ShareQuizDataAccessInterface dataAccessInterface,
                               ShareQuizOutputBoundary shareQuizPresenter) {

        this.dataAccessInterface = dataAccessInterface;
        this.shareQuizPresenter = shareQuizPresenter;
    }


    public void execute(ShareQuizInputData inputData) {

        try {

            String hash = this.dataAccessInterface.makeHash(inputData.getQuiz());
            final ShareQuizOutputData outputData = new ShareQuizOutputData(hash);
            this.shareQuizPresenter.prepareSuccessView(outputData);

        } catch (RuntimeException e) {

            shareQuizPresenter.prepareFailView("Failed to share quiz");
        }

    }

    @Override
    public void switchToShareQuizView() {

    }
}
