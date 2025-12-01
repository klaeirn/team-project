package use_cases.share_quiz;

import data_access.HashtoQuizDataAccessObject;
import entities.Question;
import entities.Quiz;
import entities.QuizFactory;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShareQuizInteractorTest {

    private static final Path TEST_HASH_FILE = Path.of("test_hashtoquiz.json");

    private Quiz createSampleQuiz() {
        QuizFactory quizFactory = new QuizFactory();
        List<Question> questions = new ArrayList<>();
        return quizFactory.createQuiz(
                "Sample Quiz",
                "creatorUser",
                "General",
                questions
        );
    }

    @Test
    void shareQuizSuccessWithTestDaoWritesHashAndMapping() throws Exception {
        Files.deleteIfExists(TEST_HASH_FILE);

        Quiz quiz = createSampleQuiz();

        HashtoQuizDataAccessObject dao =
                new HashtoQuizDataAccessObject(TEST_HASH_FILE.toString());

        final String[] capturedHash = new String[1];

        ShareQuizOutputBoundary presenter = new ShareQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ShareQuizOutputData outputData) {
                String hash = outputData.getHash();
                capturedHash[0] = hash;

                assertNotNull(hash, "Hash should not be null");
                assertFalse(hash.isBlank(), "Hash should not be blank");
                assertEquals(64, hash.length(), "SHA-256 hash should be 64 hex chars");
                assertTrue(hash.matches("[0-9a-fA-F]{64}"), "Hash should be hex");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        ShareQuizInteractor interactor = new ShareQuizInteractor(dao, presenter);
        interactor.execute(new ShareQuizInputData(quiz));

        assertNotNull(capturedHash[0], "Presenter should receive a hash");
        assertTrue(Files.exists(TEST_HASH_FILE), "Test hash file should be created");

        String fileContent = Files.readString(TEST_HASH_FILE);
        assertFalse(fileContent.isBlank(), "Test hash file should not be blank");

        JSONObject root = new JSONObject(fileContent);
        assertTrue(root.has(capturedHash[0]),
                "JSON should contain an entry for the generated hash");

        JSONObject map = root.getJSONObject(capturedHash[0]);
        assertEquals("creatorUser", map.getString("username"));
        assertEquals("Sample Quiz", map.getString("quizName"));
    }

    @Test
    void shareQuizFailureWhenDataAccessThrows() {
        Quiz quiz = createSampleQuiz();

        // Fake DAO that simulates a failure
        ShareQuizDataAccessInterface failingDao = new ShareQuizDataAccessInterface() {
            @Override
            public String makeHash(Quiz quiz) {
                throw new RuntimeException("Simulated DAO failure");
            }
        };

        ShareQuizOutputBoundary failurePresenter = new ShareQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ShareQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("There was a hashing error! Please try again. ", errorMessage);
            }
        };

        ShareQuizInteractor interactor = new ShareQuizInteractor(failingDao, failurePresenter);

        assertDoesNotThrow(() -> interactor.execute(new ShareQuizInputData(quiz)));
    }

    @Test
    void sharingSameQuizTwiceUsuallyProducesDifferentHashes() throws InterruptedException {
        Quiz quiz = createSampleQuiz();

        HashtoQuizDataAccessObject dao =
                new HashtoQuizDataAccessObject(TEST_HASH_FILE.toString());

        final String[] firstHash = new String[1];
        final String[] secondHash = new String[1];

        ShareQuizOutputBoundary presenter1 = new ShareQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ShareQuizOutputData outputData) {
                firstHash[0] = outputData.getHash();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("First share should not fail.");
            }
        };


        TimeUnit.SECONDS.sleep(10);

        ShareQuizOutputBoundary presenter2 = new ShareQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ShareQuizOutputData outputData) {
                secondHash[0] = outputData.getHash();
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Second share should not fail.");
            }
        };

        ShareQuizInteractor interactor1 = new ShareQuizInteractor(dao, presenter1);
        ShareQuizInteractor interactor2 = new ShareQuizInteractor(dao, presenter2);

        interactor1.execute(new ShareQuizInputData(quiz));
        interactor2.execute(new ShareQuizInputData(quiz));

        assertNotNull(firstHash[0]);
        assertNotNull(secondHash[0]);

        assert(!firstHash.equals(secondHash));
    }

    @Test
    void switchToShareQuizViewDoesNothingAndDoesNotThrow() {
        ShareQuizDataAccessInterface dummyDao = new ShareQuizDataAccessInterface() {
            @Override
            public String makeHash(Quiz quiz) {
                return "ignored";
            }
        };

        ShareQuizOutputBoundary dummyPresenter = new ShareQuizOutputBoundary() {
            @Override
            public void prepareSuccessView(ShareQuizOutputData outputData) {}

            @Override
            public void prepareFailView(String errorMessage) {}
        };

        ShareQuizInteractor interactor = new ShareQuizInteractor(dummyDao, dummyPresenter);

        assertDoesNotThrow(interactor::switchToShareQuizView);
    }
}
