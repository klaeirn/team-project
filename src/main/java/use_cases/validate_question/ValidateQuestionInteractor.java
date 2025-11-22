package use_cases.validate_question;

import entities.Quiz;
import entities.Question;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ValidateQuestionInteractor implements ValidateQuestionInputBoundary {
    private final ValidateQuestionDataAccessInterface validateQuestionDataAccessObject;
    private final ValidateQuestionOutputBoundary validateQuestionPresenter;

    public ValidateQuestionInteractor(ValidateQuestionDataAccessInterface validateQuestionDataAccessObject,
                                ValidateQuestionOutputBoundary validateQuestionPresenter) {
        this.validateQuestionDataAccessObject = validateQuestionDataAccessObject;
        this.validateQuestionPresenter = validateQuestionPresenter;
    }

    @Override
    public void execute(ValidateQuestionInputData validateQuestionInputData) {
        String title = validateQuestionInputData.getTitle();
        List<String> options = validateQuestionInputData.getOptions();
        String answer = validateQuestionInputData.getAnswer();


        boolean allValidOptions = true;
        for (String option : options) {
            if (option.isEmpty()) {
                allValidOptions = false;
            }
        }

        boolean allUniqueAnswers = true;
        HashMap<String, Integer> optionsHash = new HashMap<>();
        for (String option : options) {
            optionsHash.put(option, optionsHash.getOrDefault(option, 0) + 1);
        }
        for (String key : optionsHash.keySet()) {
            if (optionsHash.get(key) > 1) {
                allUniqueAnswers = false;
            }
        }

        if (title.isEmpty()){
            validateQuestionPresenter.prepareFailView("You must enter a title.");
        } else if (!allValidOptions) {
            validateQuestionPresenter.prepareFailView("Please enter an option for each field.");
        } else if (!allUniqueAnswers) {
            validateQuestionPresenter.prepareFailView("All options must be unique.");
        } else if (answer.isEmpty()) {
            validateQuestionPresenter.prepareFailView("Please select a correct answer.");
        } else {
            List<String> details = new ArrayList<>();
            details.add(title);
            details.addAll(options);
            validateQuestionPresenter.prepareSuccessView(new ValidateQuestionOutputData(details, answer));
        }
    }

}

