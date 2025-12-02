package use_cases;

import org.junit.jupiter.api.Test;
import entities.Quiz;

import use_cases.take_shared_quiz.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the TakeSharedQuizInteractor use case.
 * These tests cover all branches of the execute method:
 * invalid hash (null / blank), quiz not found, and quiz found.
 */

public class TakeSharedQuizTest {

    /**
     * test when the hash is valid and the quiz is successfully returned
     * for the take shared quiz use case
     * the interactor should call success view
     * should pass correct hash to dao
     */

    @Test
    void successTest() {
        InMemoryTakeSharedQuizDataAccessObject dataAccess =
                new InMemoryTakeSharedQuizDataAccessObject();

        Quiz quiz = new Quiz(
                "Test quiz",
                "testUser",
                "Test category",
                new ArrayList<>()
        );
        dataAccess.quizToReturn = quiz;

        // Input data with a valid hash
        TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData("ABC123", "user");

        // a Presenter that records whether success/failure was called
        final boolean[] successCalled = {false};
        final boolean[] failureCalled = {false};

        TakeSharedQuizOutputBoundary presenter =
                new TakeSharedQuizOutputBoundary() {

                    @Override
                    public void prepareSuccessView(
                            TakeSharedQuizOutputData outputData) {
                        successCalled[0] = true;
                    }

                    @Override
                    public void prepareFailureView(String message) {
                        failureCalled[0] = true;
                    }
                };

        // interactor
        TakeSharedQuizInteractor interactor =
                new TakeSharedQuizInteractor(dataAccess, presenter);

        interactor.execute(inputData);

        // Check results
        assertTrue(successCalled[0]);
        assertFalse(failureCalled[0]);

        assertTrue(dataAccess.getFromHashCalled);
        assertEquals("ABC123", dataAccess.lastHash);
    }

    /**
     * test when the hash is empty, the interactor should show
     * "Please enter hash" and not call the data access object.
     */

    @Test
    void emptyHashFailureTest() {
        InMemoryTakeSharedQuizDataAccessObject dataAccess =
                new InMemoryTakeSharedQuizDataAccessObject();

        // since it's in the if part for blank hash, don't need to create quiz
        TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData("", "user");

        // presenter
        final boolean[] successCalled = {false};
        final boolean[] failureCalled = {false};
        final String[] failureMessage = {null};

        TakeSharedQuizOutputBoundary presenter =
                new TakeSharedQuizOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TakeSharedQuizOutputData outputData) {
                        successCalled[0] = true;
                    }

                    @Override
                    public void prepareFailureView(String message) {
                        failureCalled[0] = true;
                        failureMessage[0] = message;
                    }
                };

        TakeSharedQuizInteractor interactor =
                new TakeSharedQuizInteractor(dataAccess, presenter);

        interactor.execute(inputData);

        assertTrue(failureCalled[0]);
        assertEquals("Please enter hash", failureMessage[0]);

        assertFalse(successCalled[0]);

        assertFalse(dataAccess.getFromHashCalled);
    }

    /**
     * test when the hash is spaces, the interactor should show
     * "Please enter hash" and not call the data access object.
     */

    @Test
    void spaceHashFailureTest() {
        InMemoryTakeSharedQuizDataAccessObject dataAccess =
                new InMemoryTakeSharedQuizDataAccessObject();

        // this is hash " " with spaces, don't need to create quiz
        TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData("   ", "user");

        // presenter
        final boolean[] successCalled = {false};
        final boolean[] failureCalled = {false};
        final String[] failureMessage = {null};

        TakeSharedQuizOutputBoundary presenter =
                new TakeSharedQuizOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TakeSharedQuizOutputData outputData) {
                        successCalled[0] = true;
                    }

                    @Override
                    public void prepareFailureView(String message) {
                        failureCalled[0] = true;
                        failureMessage[0] = message;
                    }
                };

        TakeSharedQuizInteractor interactor =
                new TakeSharedQuizInteractor(dataAccess, presenter);

        interactor.execute(inputData);

        assertTrue(failureCalled[0]);
        assertEquals("Please enter hash", failureMessage[0]);

        assertFalse(successCalled[0]);

        assertFalse(dataAccess.getFromHashCalled);
    }

    /**
     * test when the hash is null, the interactor should show
     * "Please enter hash" and not call the data access object.
     */

    @Test
    void nullHashFailureTest() {
        InMemoryTakeSharedQuizDataAccessObject dataAccess =
                new InMemoryTakeSharedQuizDataAccessObject();

        // this is hash null, don't need to create quiz
        TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData(null, "user");

        // presenter
        final boolean[] successCalled = {false};
        final boolean[] failureCalled = {false};
        final String[] failureMessage = {null};

        TakeSharedQuizOutputBoundary presenter =
                new TakeSharedQuizOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TakeSharedQuizOutputData outputData) {
                        successCalled[0] = true;
                    }

                    @Override
                    public void prepareFailureView(String message) {
                        failureCalled[0] = true;
                        failureMessage[0] = message;
                    }
                };

        TakeSharedQuizInteractor interactor =
                new TakeSharedQuizInteractor(dataAccess, presenter);

        interactor.execute(inputData);

        assertTrue(failureCalled[0]);
        assertEquals("Please enter hash", failureMessage[0]);

        assertFalse(successCalled[0]);

        assertFalse(dataAccess.getFromHashCalled);

    }

    /**
     * test when the hash is valid but quiz is not found,
     * the interactor should show "Quiz not Found" and dao is called.
     */

    @Test
    void quizNotFoundTest() {
        InMemoryTakeSharedQuizDataAccessObject dataAccess =
                new InMemoryTakeSharedQuizDataAccessObject();

        // this one does not create a quiz since quiz is not found
        TakeSharedQuizInputData inputData =
                new TakeSharedQuizInputData("ABC123", "user");

        final boolean[] successCalled = {false};
        final boolean[] failureCalled = {false};
        final String[] failureMessage = {null};

        TakeSharedQuizOutputBoundary presenter =
                new TakeSharedQuizOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            TakeSharedQuizOutputData outputData) {
                        successCalled[0] = true;
                    }

                    @Override
                    public void prepareFailureView(String message) {
                        failureCalled[0] = true;
                        failureMessage[0] = message;
                    }
                };

        TakeSharedQuizInteractor interactor =
                new TakeSharedQuizInteractor(dataAccess, presenter);

        interactor.execute(inputData);

        assertTrue(failureCalled[0]);
        assertEquals("Quiz not found", failureMessage[0]);

        assertFalse(successCalled[0]);

        assertTrue(dataAccess.getFromHashCalled);
        assertEquals("ABC123", dataAccess.lastHash);

    }

    private static final class InMemoryTakeSharedQuizDataAccessObject
            implements TakeSharedQuizDataAccessInterface {

        boolean getFromHashCalled = false;
        String lastHash = null;
        Quiz quizToReturn = null;

        @Override
        public Quiz getFromHash(String hash) {
            getFromHashCalled = true;
            lastHash = hash;
            return quizToReturn;
        }
    }
}


