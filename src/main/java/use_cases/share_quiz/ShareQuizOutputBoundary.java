package use_cases.share_quiz;


public interface ShareQuizOutputBoundary {

    void prepareSuccessView(ShareQuizOutputData outputData);


    void prepareFailView(String error);
}

