package use_cases;

import entities.Question;
import entities.Quiz;
import org.junit.jupiter.api.Test;
import use_cases.quickstart.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuickstartTest {

    @Test
    void successFetchQuiz() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
                fail("Navigation is unexpected.");
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                assertNotNull(outputData.getQuiz());
                assertEquals("Test Quiz", outputData.getQuiz().getName());
                assertEquals("testuser", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);
        QuickstartInputData inputData = new QuickstartInputData("Science", "easy", "multiple");
        interactor.execute(inputData);

        // Verify TakeQuizInteractor was called
        assertTrue(takeQuizInteractor.wasExecuted);
    }

    @Test
    void failsWhenApiThrowsIOException() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(null);
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
                fail("Navigation is unexpected.");
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(error.contains("Failed to fetch quiz"));
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);
        QuickstartInputData inputData = new QuickstartInputData("Science", "easy", "multiple");
        interactor.execute(inputData);

        // Verify TakeQuizInteractor was NOT called on failure
        assertFalse(takeQuizInteractor.wasExecuted);
    }

    @Test
    void failsWhenApiThrowsGenericException() {
        QuickstartDataAccessInterface dataAccess = new QuickstartDataAccessInterface() {
            @Override
            public Quiz fetchQuiz(String category, String difficulty, String type) throws IOException {
                throw new RuntimeException("Generic error");
            }
        };
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
                fail("Navigation is unexpected.");
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(error.contains("An error occurred"));
                assertTrue(error.contains("Generic error"));
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);
        QuickstartInputData inputData = new QuickstartInputData("Science", "easy", "multiple");
        interactor.execute(inputData);

        // Verify TakeQuizInteractor was NOT called on failure
        assertFalse(takeQuizInteractor.wasExecuted);
    }

    @Test
    void backToQuizMenuCallsPresenter() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();
        final boolean[] menuShown = {false};

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
                menuShown[0] = true;
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                // Not testing this path
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);
        interactor.backToQuizMenu();

        assertTrue(menuShown[0]);
    }

    @Test
    void correctUsernameIsRetrieved() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("alice");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
                fail("Navigation is unexpected.");
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                assertEquals("alice", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);
        QuickstartInputData inputData = new QuickstartInputData("History", "medium", "boolean");
        interactor.execute(inputData);
    }

    @Test
    void differentCategoriesWork() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
                fail("Navigation is unexpected.");
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                assertNotNull(outputData.getQuiz());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);

        interactor.execute(new QuickstartInputData("Science", "easy", "multiple"));
        interactor.execute(new QuickstartInputData("History", "medium", "boolean"));
        interactor.execute(new QuickstartInputData("Geography", "hard", "multiple"));
    }

    @Test
    void takeQuizInteractorCalledWithCorrectData() {
        Quiz quiz = createValidQuiz();
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(quiz);
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        InMemoryTakeQuizInteractor takeQuizInteractor = new InMemoryTakeQuizInteractor();

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, takeQuizInteractor);
        interactor.execute(new QuickstartInputData("Science", "easy", "multiple"));

        assertTrue(takeQuizInteractor.wasExecuted);
        assertEquals(quiz, takeQuizInteractor.receivedQuiz);
        assertEquals("testuser", takeQuizInteractor.receivedUsername);
    }

    @Test
    void nullTakeQuizInteractorDoesNotCauseError() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
        final boolean[] successCalled = {false};

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                successCalled[0] = true;
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not fail with null takeQuizInteractor");
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess, null);
        interactor.execute(new QuickstartInputData("Science", "easy", "multiple"));

        assertTrue(successCalled[0]);
    }

    private Quiz createValidQuiz() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", Arrays.asList("True", "False"), "True"));
        questions.add(new Question("Question 2", Arrays.asList("True", "False"), "False"));
        return new Quiz("Test Quiz", "api", "Science", questions);
    }

    private static final class InMemoryQuizDataAccess implements QuickstartDataAccessInterface {
        private final Quiz quiz;

        InMemoryQuizDataAccess(Quiz quiz) {
            this.quiz = quiz;
        }

        @Override
        public Quiz fetchQuiz(String category, String difficulty, String type) throws IOException {
            if (quiz == null) {
                throw new IOException("Failed to fetch quiz from API");
            }
            return quiz;
        }
    }

    private static final class InMemoryUserDataAccess implements QuickstartUserDataAccessInterface {
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