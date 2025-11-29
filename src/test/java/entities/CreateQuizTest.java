package entities;

import org.junit.jupiter.api.Test;
import use_cases.create_quiz.CreateQuizDataAccessInterface;
import use_cases.create_quiz.CreateQuizInputData;
import use_cases.create_quiz.CreateQuizInteractor;
import use_cases.create_quiz.CreateQuizOutputBoundary;
import use_cases.create_quiz.CreateQuizOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateQuizTest {

    @Test
    void successTest() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();
        List<List<String>> questionsDetails = createValidQuestionsDetails();
        List<String> answers = createValidCorrectAnswers();

        CreateQuizInputData inputData = new CreateQuizInputData(
                "Math Quiz", "Mathematics", questionsDetails, answers, "paul"
        );

        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                assertEquals("Math Quiz", outputData.getQuizName());
                assertEquals("paul", outputData.getCreatorUsername());
                assertEquals("Mathematics", outputData.getCategory());
                assertEquals(10, outputData.getQuestionCount());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToCreateQuizView(String username) {
                // navigation not part of this test
            }

            @Override
            public void switchToLoggedInView() {
                // navigation not part of this test
            }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, successPresenter
        );

        interactor.execute(inputData);

        Quiz savedQuiz = quizRepository.getSavedQuiz();
        assertNotNull(savedQuiz);
        assertEquals("Math Quiz", savedQuiz.getName());
        assertEquals("paul", savedQuiz.getCreatorUsername());
        assertEquals("Mathematics", savedQuiz.getCategory());
        assertEquals(10, savedQuiz.getQuestions().size());
    }

    @Test
    void emptyTitleFails() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();

        CreateQuizInputData inputData = new CreateQuizInputData(
                "", "Mathematics", createValidQuestionsDetails(), createValidCorrectAnswers(), "alex"
        );

        CreateQuizOutputBoundary failurePresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Quiz title cannot be empty.", errorMessage);
            }

            @Override
            public void switchToCreateQuizView(String username) { }

            @Override
            public void switchToLoggedInView() { }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, failurePresenter
        );

        interactor.execute(inputData);
        assertNull(quizRepository.getSavedQuiz());
    }

    @Test
    void whitespaceTitleFails() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();

        CreateQuizInputData inputData = new CreateQuizInputData(
                "   ", "Mathematics", createValidQuestionsDetails(), createValidCorrectAnswers(), "alex"
        );

        CreateQuizOutputBoundary failurePresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Quiz title cannot be empty.", errorMessage);
            }

            @Override
            public void switchToCreateQuizView(String username) { }

            @Override
            public void switchToLoggedInView() { }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, failurePresenter
        );

        interactor.execute(inputData);
        assertNull(quizRepository.getSavedQuiz());
    }

    @Test
    void emptyCategoryFails() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();

        CreateQuizInputData inputData = new CreateQuizInputData(
                "Math Quiz", "", createValidQuestionsDetails(), createValidCorrectAnswers(), "alex"
        );

        CreateQuizOutputBoundary failurePresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Category cannot be empty.", errorMessage);
            }

            @Override
            public void switchToCreateQuizView(String username) { }

            @Override
            public void switchToLoggedInView() { }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, failurePresenter
        );

        interactor.execute(inputData);
        assertNull(quizRepository.getSavedQuiz());
    }

    @Test
    void nullQuestionsFails() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();

        CreateQuizInputData inputData = new CreateQuizInputData(
                "Math Quiz", "Mathematics", null, createValidCorrectAnswers(), "alex"
        );

        CreateQuizOutputBoundary failurePresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.contains("Quiz must have exactly 10 questions"));
                assertTrue(errorMessage.contains("Current count: 0"));
            }

            @Override
            public void switchToCreateQuizView(String username) { }

            @Override
            public void switchToLoggedInView() { }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, failurePresenter
        );

        interactor.execute(inputData);
        assertNull(quizRepository.getSavedQuiz());
    }

    @Test
    void nineQuestionsFails() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();
        List<List<String>> questions = createValidQuestionsDetails();
        questions.remove(0);
        List<String> answers = createValidCorrectAnswers();
        answers.remove(0);

        CreateQuizInputData inputData = new CreateQuizInputData(
                "Math Quiz", "Mathematics", questions, answers, "alex"
        );

        CreateQuizOutputBoundary failurePresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.contains("Quiz must have exactly 10 questions"));
                assertTrue(errorMessage.contains("Current count: 9"));
            }

            @Override public void switchToCreateQuizView(String username) { }
            @Override public void switchToLoggedInView() { }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, failurePresenter
        );

        interactor.execute(inputData);
        assertNull(quizRepository.getSavedQuiz());
    }

    @Test
    void elevenQuestionsFails() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();
        List<List<String>> questions = createValidQuestionsDetails();
        List<String> extraQuestion = new ArrayList<>();
        extraQuestion.add("Bonus?");
        extraQuestion.add("A");
        extraQuestion.add("B");
        extraQuestion.add("C");
        extraQuestion.add("D");
        questions.add(extraQuestion);

        List<String> answers = createValidCorrectAnswers();
        answers.add("A");

        CreateQuizInputData inputData = new CreateQuizInputData(
                "Math Quiz", "Mathematics", questions, answers, "alex"
        );

        CreateQuizOutputBoundary failurePresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.contains("Quiz must have exactly 10 questions"));
                assertTrue(errorMessage.contains("Current count: 11"));
            }

            @Override public void switchToCreateQuizView(String username) { }
            @Override public void switchToLoggedInView() { }
        };

        CreateQuizInteractor interactor = new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, failurePresenter
        );

        interactor.execute(inputData);
        assertNull(quizRepository.getSavedQuiz());
    }

    @Test
    void savedQuestionsMatchInput() {
        InMemoryQuizDataAccessObject quizRepository = new InMemoryQuizDataAccessObject();

        CreateQuizInteractor interactor = getCreateQuizInteractor(quizRepository);

        interactor.execute(new CreateQuizInputData(
                "Science Quiz", "Science", createValidQuestionsDetails(), createValidCorrectAnswers(), "scientist"
        ));

        Quiz savedQuiz = quizRepository.getSavedQuiz();
        assertNotNull(savedQuiz);
        assertEquals(10, savedQuiz.getQuestions().size());
        assertEquals("Question 1?", savedQuiz.getQuestions().get(0).getTitle());
        assertEquals("Option D", savedQuiz.getQuestions().get(9).getAnswer());
    }

    private static CreateQuizInteractor getCreateQuizInteractor(InMemoryQuizDataAccessObject quizRepository) {
        CreateQuizOutputBoundary successPresenter = new CreateQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateQuizOutputData outputData) {
                // nothing extra to assert here beyond DAO checks
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override public void switchToCreateQuizView(String username) { }
            @Override public void switchToLoggedInView() { }
        };

        return new CreateQuizInteractor(
                quizRepository, new QuizFactory(), null, successPresenter
        );
    }

    private List<List<String>> createValidQuestionsDetails() {
        List<List<String>> questionsDetails = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            List<String> questionDetails = new ArrayList<>();
            questionDetails.add("Question " + i + "?");
            questionDetails.add("Option A");
            questionDetails.add("Option B");
            questionDetails.add("Option C");
            questionDetails.add("Option D");
            questionsDetails.add(questionDetails);
        }
        return questionsDetails;
    }

    private List<String> createValidCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<>();
        correctAnswers.add("Option A");
        correctAnswers.add("Option B");
        correctAnswers.add("Option C");
        correctAnswers.add("Option D");
        correctAnswers.add("Option A");
        correctAnswers.add("Option B");
        correctAnswers.add("Option C");
        correctAnswers.add("Option D");
        correctAnswers.add("Option A");
        correctAnswers.add("Option D");
        return correctAnswers;
    }

    private static final class InMemoryQuizDataAccessObject implements CreateQuizDataAccessInterface {
        private Quiz savedQuiz;

        @Override
        public void saveUserQuiz(Quiz quiz) {
            this.savedQuiz = quiz;
        }

        Quiz getSavedQuiz() {
            return savedQuiz;
        }
    }
}

