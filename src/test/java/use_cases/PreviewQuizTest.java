package use_cases;

import org.junit.jupiter.api.Test;
import entities.Question;
import entities.Quiz;
import entities.QuizFactory;
import use_cases.preview_quiz.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreviewQuizTest {

    @Test
    void testPreviewExistingQuizSuccess() {
        PreviewQuizInputData inputData = new PreviewQuizInputData("NewQuiz", "User1");
        PreviewQuizDataAccessInterface dao = getPreviewQuizDataAccessInterface();

        PreviewQuizOutputBoundary presenter = new PreviewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(PreviewQuizOutputData outputData) {
                assertEquals(1, outputData.getQuestions().size());
                assertEquals("2+2=?", outputData.getQuestions().get(0).getTitle());
                assertEquals("A", outputData.getQuestions().get(0).getAnswer());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not have failed");
            }
        };

        QuizFactory factory = new QuizFactory();
        PreviewQuizInteractor interactor = new PreviewQuizInteractor(dao, presenter, factory);
        interactor.execute(inputData);
    }

    private static PreviewQuizDataAccessInterface getPreviewQuizDataAccessInterface() {
        List<String> options = Arrays.asList("A", "B", "C", "D");
        Question q1 = new Question("2+2=?", options, "A");
        List<Question> questions = List.of(q1);
        Quiz existingQuiz = new Quiz("NewQuiz", "User1", "Math", questions);

        return new PreviewQuizDataAccessInterface() {
            @Override
            public Quiz getQuiz(String quizName, String creatorUsername) {
                return existingQuiz; // Simulate database found the quiz
            }
        };
    }

    @Test
    void testPreviewExistingQuizNotFound() {
        PreviewQuizInputData inputData = new PreviewQuizInputData("NonexistentQuiz", "User1");

        PreviewQuizDataAccessInterface dao = new PreviewQuizDataAccessInterface() {
            @Override
            public Quiz getQuiz(String quizName, String creatorUsername) {
                return null;
            }
        };

        PreviewQuizOutputBoundary presenter = new PreviewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(PreviewQuizOutputData outputData) {
                fail("Should not have succeeded");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Quiz not found.", errorMessage);
            }
        };

        PreviewQuizInteractor interactor = new PreviewQuizInteractor(dao, presenter, new QuizFactory());
        interactor.execute(inputData);
    }

    @Test
    void testPreviewNewQuizSuccess() {
        List<String> q1 = Arrays.asList("2+2=?", "1", "2", "4", "5");
        List<List<String>> rawQs = new ArrayList<>();
        rawQs.add(q1);
        List<String> rawAnswers = List.of("D");

        PreviewQuizInputData inputData = new PreviewQuizInputData(
                "NewQuiz", "User2", "Math", rawQs, rawAnswers
        );

        QuizFactory factory = new QuizFactory() {
            @Override
            public Quiz createQuiz(String name, String user, String category, List<Question> questions) {
                assertEquals("NewQuiz", name);
                assertEquals(1, questions.size());
                return new Quiz(name, user, category, questions);
            }
        };

        PreviewQuizOutputBoundary presenter = new PreviewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(PreviewQuizOutputData outputData) {
                assertEquals(1, outputData.getQuestions().size());
                assertEquals("2+2=?", outputData.getQuestions().get(0).getTitle());
                assertEquals("D", outputData.getQuestions().get(0).getAnswer());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not have failed: " + errorMessage);
            }
        };

        PreviewQuizDataAccessInterface dao = new PreviewQuizDataAccessInterface() {
            @Override
            public Quiz getQuiz(String quizName, String creatorUsername) {
                return null;
            }
        };

        PreviewQuizInteractor interactor = new PreviewQuizInteractor(dao, presenter, factory);
        interactor.execute(inputData);
    }

    @Test
    void testPreviewNewQuizEmpty() {
        PreviewQuizInputData inputData = new PreviewQuizInputData(
                "EmptyQuiz", "User3", "Mystery", new ArrayList<>(), new ArrayList<>()
        );

        PreviewQuizOutputBoundary presenter = new PreviewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(PreviewQuizOutputData outputData) {
                fail("Should not have succeeded");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("No questions to preview.", errorMessage);
            }
        };

        PreviewQuizDataAccessInterface dao = new PreviewQuizDataAccessInterface() {
            @Override
            public Quiz getQuiz(String quizName, String creatorUsername) {
                return null;
            }
        };

        PreviewQuizInteractor interactor = new PreviewQuizInteractor(dao, presenter, new QuizFactory());
        interactor.execute(inputData);
    }

    @Test
    void testPreviewNewQuizException() {
        // Force an exception by passing a malformed list (empty inner list).
        List<List<String>> brokenQuestions = new ArrayList<>();
        brokenQuestions.add(new ArrayList<>()); // Empty inner list causes crash
        List<String> answers = List.of("A");
        PreviewQuizInputData inputData = new PreviewQuizInputData(
                "BrokenQuiz", "User4", "Tech", brokenQuestions, answers
        );

        PreviewQuizOutputBoundary presenter = new PreviewQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(PreviewQuizOutputData outputData) {
                fail("Should not have succeeded");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertTrue(errorMessage.startsWith("Error building preview:"));
            }
        };

        PreviewQuizDataAccessInterface dao = new PreviewQuizDataAccessInterface() {
            @Override
            public Quiz getQuiz(String quizName, String creatorUsername) {
                return null;
            }
        };

        PreviewQuizInteractor interactor = new PreviewQuizInteractor(dao, presenter, new QuizFactory());
        interactor.execute(inputData);
    }
}
