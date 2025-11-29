package entities;

import org.junit.jupiter.api.Test;
import use_cases.validate_question.ValidateQuestionInputData;
import use_cases.validate_question.ValidateQuestionInteractor;
import use_cases.validate_question.ValidateQuestionOutputBoundary;
import use_cases.validate_question.ValidateQuestionOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidateQuestionTest {

    @Test
    void successTest() {
        ValidateQuestionOutputBoundary successPresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                assertEquals("What is 2+2?", outputData.getQuestionDetails().get(0));
                assertEquals(5, outputData.getQuestionDetails().size());
                assertEquals("Option C", outputData.getQuestionDetails().get(3));
                assertEquals("Option B", outputData.getAnswer());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, successPresenter);
        interactor.execute(new ValidateQuestionInputData(
                "What is 2+2?", createValidOptions(), "Option B"
        ));
    }

    @Test
    void emptyTitleFails() {
        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You must enter a title.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "", createValidOptions(), "Option A"
        ));
    }

    @Test
    void whitespaceTitlePassesBecauseNotEmpty() {
        ValidateQuestionOutputBoundary successPresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                assertEquals("   ", outputData.getQuestionDetails().get(0));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, successPresenter);
        interactor.execute(new ValidateQuestionInputData(
                "   ", createValidOptions(), "Option A"
        ));
    }

    @Test
    void emptyOptionFails() {
        List<String> options = new ArrayList<>();
        options.add("Paris");
        options.add("");
        options.add("London");
        options.add("Berlin");

        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please enter an option for each field.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "Capital of France?", options, "Paris"
        ));
    }

    @Test
    void duplicateOptionsFail() {
        List<String> options = new ArrayList<>();
        options.add("Option A");
        options.add("Option B");
        options.add("Option A");
        options.add("Option D");

        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("All options must be unique.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "What is 2+2?", options, "Option A"
        ));
    }

    @Test
    void emptyAnswerFails() {
        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please select a correct answer.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "What is 2+2?", createValidOptions(), ""
        ));
    }

    @Test
    void whitespaceAnswerPassesBecauseNotEmpty() {
        ValidateQuestionOutputBoundary successPresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                assertEquals("   ", outputData.getAnswer());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, successPresenter);
        interactor.execute(new ValidateQuestionInputData(
                "What is 2+2?", createValidOptions(), "   "
        ));
    }

    @Test
    void validationOrderStopsOnTitle() {
        List<String> options = new ArrayList<>();
        options.add("Duplicate");
        options.add("Duplicate");

        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You must enter a title.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "", options, ""
        ));
    }

    @Test
    void validationOrderStopsOnOptions() {
        List<String> options = new ArrayList<>();
        options.add("Option A");
        options.add("");

        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please enter an option for each field.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "Valid Title", options, ""
        ));
    }

    @Test
    void validationOrderStopsOnUniqueness() {
        List<String> options = new ArrayList<>();
        options.add("Duplicate");
        options.add("Duplicate");

        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("All options must be unique.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "Valid Title", options, ""
        ));
    }

    @Test
    void validationOrderStopsOnAnswer() {
        ValidateQuestionOutputBoundary failurePresenter = new ValidateQuestionOutputBoundary() {
            @Override
            public void prepareSuccessView(ValidateQuestionOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please select a correct answer.", errorMessage);
            }
        };

        ValidateQuestionInteractor interactor = new ValidateQuestionInteractor(null, failurePresenter);
        interactor.execute(new ValidateQuestionInputData(
                "Valid Title", createValidOptions(), ""
        ));
    }

    private List<String> createValidOptions() {
        List<String> options = new ArrayList<>();
        options.add("Option A");
        options.add("Option B");
        options.add("Option C");
        options.add("Option D");
        return options;
    }
}

