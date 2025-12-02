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

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);
        QuickstartInputData inputData = new QuickstartInputData("Science", "easy", "multiple");
        interactor.execute(inputData);
    }

    @Test
    void failsWhenApiThrowsIOException() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(null);
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);
        QuickstartInputData inputData = new QuickstartInputData("Science", "easy", "multiple");
        interactor.execute(inputData);
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

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);
        QuickstartInputData inputData = new QuickstartInputData("Science", "easy", "multiple");
        interactor.execute(inputData);
    }

    @Test
    void backToQuizMenuCallsPresenter() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");
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

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);
        interactor.backToQuizMenu();

        assertTrue(menuShown[0]);
    }

    @Test
    void correctUsernameIsRetrieved() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("alice");

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

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);
        QuickstartInputData inputData = new QuickstartInputData("History", "medium", "boolean");
        interactor.execute(inputData);
    }

    @Test
    void differentCategoriesWork() {
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(createValidQuiz());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);

        interactor.execute(new QuickstartInputData("Science", "easy", "multiple"));
        interactor.execute(new QuickstartInputData("History", "medium", "boolean"));
        interactor.execute(new QuickstartInputData("Geography", "hard", "multiple"));
    }

    @Test
    void outputDataContainsQuizAndUsername() {
        Quiz quiz = createValidQuiz();
        InMemoryQuizDataAccess dataAccess = new InMemoryQuizDataAccess(quiz);
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

        QuickstartOutputBoundary presenter = new QuickstartOutputBoundary() {
            @Override
            public void showQuizMenu() {
            }

            @Override
            public void prepareSuccessView(QuickstartOutputData outputData) {
                assertEquals(quiz, outputData.getQuiz());
                assertEquals("testuser", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not fail");
            }
        };

        QuickstartInteractor interactor = new QuickstartInteractor(presenter, dataAccess, userDataAccess);
        interactor.execute(new QuickstartInputData("Science", "easy", "multiple"));
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
}