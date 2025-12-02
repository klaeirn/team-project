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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, null);
        interactor.execute(inputData);
    }

    @Test
    void successWithExplicitUsername() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("defaultuser");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "explicituser");
        interactor.execute(inputData);
    }

    @Test
    void nullInputDataFails() {
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        interactor.execute(null);
    }

    @Test
    void nullQuizFails() {
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(null, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void usernameFromDataAccessWhenNull() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("alice");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, null);
        interactor.execute(inputData);
    }

    @Test
    void multipleQuestionsPassThrough() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void emptyQuizPassesThrough() {
        Quiz emptyQuiz = new Quiz("Empty", "creator", "Test", new ArrayList<>());
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(emptyQuiz, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void singleQuestionQuizPassesThrough() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Q1", Arrays.asList("A", "B"), "A"));
        Quiz quiz = new Quiz("Single", "creator", "Test", questions);

        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("testuser");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void differentUsernames() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("bob");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "charlie");
        interactor.execute(inputData);
    }

    @Test
    void usernameFromInputDataTakesPriority() {
        Quiz quiz = createValidQuiz();
        InMemoryUserDataAccess userDataAccess = new InMemoryUserDataAccess("fromDataAccess");

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

        SelectExistingQuizInteractor interactor = new SelectExistingQuizInteractor(userDataAccess, presenter);
        SelectExistingQuizInputData inputData = new SelectExistingQuizInputData(quiz, "fromInputData");
        interactor.execute(inputData);
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
}
