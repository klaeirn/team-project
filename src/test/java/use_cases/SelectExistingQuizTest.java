package use_cases;

import entities.Question;
import entities.Quiz;
import org.junit.jupiter.api.Test;
import use_cases.select_existing_quiz.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectExistingQuizTest {

    @Test
    void successSelectValidQuiz() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals(quiz, outputData.getQuiz());
                assertEquals("testuser", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, null);
        interactor.execute(inputData);

        // Verify TakeQuizInteractor was called
        assertTrue(takeQuizInteractor.wasExecuted);
    }

    @Test
    void successWithExplicitUsername() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("defaultuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals(quiz, outputData.getQuiz());
                assertEquals("explicituser", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "explicituser");
        interactor.execute(inputData);
    }

    @Test
    void nullInputDataFails() {
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Please select a quiz.", message);
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        interactor.execute(null);

        // Verify TakeQuizInteractor was NOT called on failure
        assertFalse(takeQuizInteractor.wasExecuted);
    }

    @Test
    void nullQuizFails() {
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Please select a quiz.", message);
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(null, "testuser");
        interactor.execute(inputData);

        // Verify TakeQuizInteractor was NOT called on failure
        assertFalse(takeQuizInteractor.wasExecuted);
    }

    @Test
    void usernameFromDataAccessWhenNull() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("alice");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, null);
        interactor.execute(inputData);
    }

    @Test
    void multipleQuestionsPassThrough() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals(3, outputData.getQuiz().getQuestions().size());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void takeQuizInteractorCalledWithCorrectData() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
            }

            @Override
            public void prepareFailView(String message) {
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, null);
        interactor.execute(inputData);

        assertTrue(takeQuizInteractor.wasExecuted);
        assertEquals(quiz, takeQuizInteractor.receivedQuiz);
        assertEquals("testuser", takeQuizInteractor.receivedUsername);
    }

    @Test
    void nullTakeQuizInteractorDoesNotCauseError() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        final boolean[] successCalled = {false};

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                successCalled[0] = true;
            }

            @Override
            public void prepareFailView(String message) {
                fail("Should not fail with null takeQuizInteractor");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, null);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "testuser");
        interactor.execute(inputData);

        assertTrue(successCalled[0]);
    }

    @Test
    void emptyQuizPassesThrough() {
        Quiz emptyQuiz = new Quiz("Empty", "creator", "Test", new ArrayList<>());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals(emptyQuiz, outputData.getQuiz());
                assertEquals(0, outputData.getQuiz().getQuestions().size());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(emptyQuiz, "testuser");
        interactor.execute(inputData);

        assertTrue(takeQuizInteractor.wasExecuted);
    }

    @Test
    void singleQuestionQuizPassesThrough() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Q1", Arrays.asList("A", "B"), "A"));
        Quiz quiz = new Quiz("Single", "creator", "Test", questions);

        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals(1, outputData.getQuiz().getQuestions().size());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "testuser");
        interactor.execute(inputData);

        assertTrue(takeQuizInteractor.wasExecuted);
    }

    @Test
    void differentUsernames() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("bob");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals("charlie", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "charlie");
        interactor.execute(inputData);

        assertEquals("charlie", takeQuizInteractor.receivedUsername);
    }

    @Test
    void usernameFromInputDataTakesPriority() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("fromDataAccess");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        SelectExistingQuizOutputBoundary presenter = new SelectExistingQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectExistingQuizOutputData outputData) {
                assertEquals("fromInputData", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String message) {
                fail("Use case failure is unexpected.");
            }
        };

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter, takeQuizInteractor);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "fromInputData");
        interactor.execute(inputData);

        assertEquals("fromInputData", takeQuizInteractor.receivedUsername);
    }

    private Quiz createValidQuiz() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option A"));
        questions.add(new Question("Question 2", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option B"));
        questions.add(new Question("Question 3", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option C"));
        return new Quiz("Test Quiz", "creator", "Test", questions);
    }

    private static final class InMemoryUserDataAccess implements SelectExistingQuizDataAccessInterface {
        private final String username;

        InMemoryUserDataAccess(String username) {
            this.username = username;
        }

        @Override
        public String getCurrentUsername() {
            return username;
        }
    }

    private static final class InMemoryTakeQuizInteractor implements use_cases.take_quiz.TakeQuizInputBoundary {
        boolean wasExecuted = false;
        Quiz receivedQuiz = null;
        String receivedUsername = null;

        @Override
        public void execute(use_cases.take_quiz.TakeQuizInputData inputData) {
            wasExecuted = true;
            if (inputData != null) {
                receivedQuiz = inputData.getQuiz();
                receivedUsername = inputData.getUsername();
            }
        }

        @Override
        public void nextQuestion() {
        }

        @Override
        public void previousQuestion() {
        }

        @Override
        public void setAnswer(int questionIndex, String answer) {
        }

        @Override
        public void submitQuiz() {
        }
    }
}