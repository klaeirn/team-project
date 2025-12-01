package use_cases;

import entities.Question;
import entities.Quiz;
import entities.QuizResult;
import org.junit.jupiter.api.Test;
import use_cases.view_leaderboard.LeaderboardDataAccessInterface;
import use_cases.view_results.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewResultsTest {

    @Test
    void testExecuteSuccessWithAllCorrectAnswers() {
        // Create quiz with questions
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("Math Quiz", "creator1", "Mathematics", questions);

        // Create user answers - all correct
        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "A");
        userAnswers.put(1, "B");
        userAnswers.put(2, "C");

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                assertEquals("Math Quiz", quizName);
                assertEquals("creator1", creatorUsername);
                assertEquals(3, quizResult.getScore());
                assertEquals(3, quizResult.getTotalQuestions());
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertNotNull(outputData);
                assertNotNull(outputData.getQuizResult());
                assertEquals("Math Quiz", outputData.getQuizName());
                assertEquals("creator1", outputData.getCreatorUsername());
                assertNull(outputData.getErrorMessage());
                assertEquals(3, outputData.getQuizResult().getScore());
                assertEquals(3, outputData.getQuizResult().getTotalQuestions());
                assertEquals(100.0, outputData.getQuizResult().getPercentage(), 0.01);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user1", userAnswers);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithSomeCorrectAnswers() {
        // Create quiz with questions
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("Science Quiz", "creator2", "Science", questions);

        // Create user answers - 2 correct, 1 wrong
        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "A"); // correct
        userAnswers.put(1, "B"); // correct
        userAnswers.put(2, "Wrong"); // incorrect

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                assertEquals(2, quizResult.getScore());
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals(2, outputData.getQuizResult().getScore());
                assertEquals(3, outputData.getQuizResult().getTotalQuestions());
                assertEquals(66.67, outputData.getQuizResult().getPercentage(), 0.1);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user2", userAnswers);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithAllWrongAnswers() {
        // Create quiz with questions
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("History Quiz", "creator3", "History", questions);

        // Create user answers - all wrong
        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "Wrong1");
        userAnswers.put(1, "Wrong2");
        userAnswers.put(2, "Wrong3");

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                assertEquals(0, quizResult.getScore());
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals(0, outputData.getQuizResult().getScore());
                assertEquals(3, outputData.getQuizResult().getTotalQuestions());
                assertEquals(0.0, outputData.getQuizResult().getPercentage(), 0.01);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user3", userAnswers);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithNullUserAnswers() {
        // Create quiz with questions
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("Geography Quiz", "creator4", "Geography", questions);

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                assertEquals(0, quizResult.getScore()); // No answers provided
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals(0, outputData.getQuizResult().getScore());
                assertEquals(3, outputData.getQuizResult().getTotalQuestions());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute with null userAnswers
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user4", null);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithNullAnswersInMap() {
        // Create quiz with questions
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("English Quiz", "creator5", "English", questions);

        // Create user answers with null values
        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "A"); // correct
        userAnswers.put(1, null); // null answer should not count
        userAnswers.put(2, "C"); // correct

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                assertEquals(2, quizResult.getScore()); // Only 2 correct (null doesn't count)
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals(2, outputData.getQuizResult().getScore());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user5", userAnswers);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithSystemCreatorDoesNotSaveToLeaderboard() {
        // Create quiz with System creator (quickstart quiz)
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("Quickstart Quiz", "System", "General", questions);

        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "A");
        userAnswers.put(1, "B");
        userAnswers.put(2, "C");

        // Create mock leaderboard data access that should NOT be called
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                fail("saveQuizResult should not be called for System creator");
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals("System", outputData.getCreatorUsername());
                assertEquals(3, outputData.getQuizResult().getScore());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user6", userAnswers);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithNullLeaderboardDataAccess() {
        // Create quiz with questions
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("Art Quiz", "creator7", "Art", questions);

        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "A");

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals(1, outputData.getQuizResult().getScore());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor with null leaderboardDataAccess
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, null);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user7", userAnswers);
        interactor.execute(inputData);
        // Should not throw NullPointerException
    }

    @Test
    void testExecuteFailWithNullQuiz() {
        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                fail("saveQuizResult should not be called when quiz is null");
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz not found.", error);
            }
        };

        // Create interactor and execute with null quiz
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(null, "user8", new HashMap<>());
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithNullQuestions() {
        // Create quiz with null questions
        Quiz quiz = new Quiz("Empty Quiz", "creator9", "General", null);

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                fail("saveQuizResult should not be called when questions are null");
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz has no questions.", error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user9", new HashMap<>());
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithEmptyQuestions() {
        // Create quiz with empty questions list
        Quiz quiz = new Quiz("Empty Questions Quiz", "creator10", "General", new ArrayList<>());

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                fail("saveQuizResult should not be called when questions are empty");
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz has no questions.", error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user10", new HashMap<>());
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithPartialAnswers() {
        // Create quiz with 5 questions
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Q1?", Arrays.asList("A", "B", "C", "D"), "A"));
        questions.add(new Question("Q2?", Arrays.asList("A", "B", "C", "D"), "B"));
        questions.add(new Question("Q3?", Arrays.asList("A", "B", "C", "D"), "C"));
        questions.add(new Question("Q4?", Arrays.asList("A", "B", "C", "D"), "D"));
        questions.add(new Question("Q5?", Arrays.asList("A", "B", "C", "D"), "A"));

        Quiz quiz = new Quiz("Partial Answers Quiz", "creator11", "General", questions);

        // Only answer first 3 questions
        Map<Integer, String> userAnswers = new HashMap<>();
        userAnswers.put(0, "A"); // correct
        userAnswers.put(1, "B"); // correct
        userAnswers.put(2, "C"); // correct
        // Questions 3 and 4 not answered

        // Create mock leaderboard data access
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                assertEquals(3, quizResult.getScore()); // 3 correct out of 5
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
                assertEquals(3, outputData.getQuizResult().getScore());
                assertEquals(5, outputData.getQuizResult().getTotalQuestions());
                assertEquals(60.0, outputData.getQuizResult().getPercentage(), 0.01);
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "user11", userAnswers);
        interactor.execute(inputData);
    }

    @Test
    void testConstructor() {
        // Test that constructor properly initializes dependencies
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
            }

            @Override
            public entities.Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        ViewResultsOutputBoundary presenter = new ViewResultsOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewResultsOutputData outputData) {
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        ViewResultsInteractor interactor = new ViewResultsInteractor(presenter, leaderboardDataAccess);
        assertNotNull(interactor);

        // Verify it works by executing with valid input
        List<Question> questions = createQuestions();
        Quiz quiz = new Quiz("Test Quiz", "testCreator", "Test", questions);
        ViewResultsInputData inputData = new ViewResultsInputData(quiz, "testUser", new HashMap<>());
        interactor.execute(inputData);
        // If we get here without exception, constructor worked correctly
    }

    // Helper method to create test questions
    private List<Question> createQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is 2+2?", Arrays.asList("A", "B", "C", "D"), "A"));
        questions.add(new Question("What is 3+3?", Arrays.asList("A", "B", "C", "D"), "B"));
        questions.add(new Question("What is 4+4?", Arrays.asList("A", "B", "C", "D"), "C"));
        return questions;
    }
}

