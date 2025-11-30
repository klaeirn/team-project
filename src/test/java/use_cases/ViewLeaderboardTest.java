package use_cases;

import entities.Leaderboard;
import entities.QuizResult;
import org.junit.jupiter.api.Test;
import use_cases.view_leaderboard.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewLeaderboardTest {

    @Test
    void testExecuteSuccessWithLeaderboard() {
        // Create a leaderboard with results
        List<QuizResult> results = new ArrayList<>();
        results.add(new QuizResult("user1", 8, 10));
        results.add(new QuizResult("user2", 7, 10));
        results.add(new QuizResult("user3", 6, 10));
        Leaderboard leaderboard = new Leaderboard(1, results);

        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                assertEquals("Math Quiz", quizName);
                assertEquals("creator1", creatorUsername);
                return leaderboard;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                assertNotNull(outputData);
                assertNotNull(outputData.getLeaderboard());
                assertEquals("Math Quiz", outputData.getQuizName());
                assertNull(outputData.getErrorMessage());
                assertEquals(3, outputData.getLeaderboard().getParticipantCount());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("Math Quiz", "creator1");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithEmptyLeaderboard() {
        // Create an empty leaderboard
        Leaderboard emptyLeaderboard = new Leaderboard(1, new ArrayList<>());

        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return emptyLeaderboard;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                assertNotNull(outputData);
                assertNull(outputData.getLeaderboard());
                assertEquals("Science Quiz", outputData.getQuizName());
                assertEquals("No results found for this quiz.", outputData.getErrorMessage());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("Science Quiz", "creator2");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteSuccessWithNullLeaderboard() {
        // Create mock data access object that returns null
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                assertNotNull(outputData);
                assertNull(outputData.getLeaderboard());
                assertEquals("History Quiz", outputData.getQuizName());
                assertEquals("No results found for this quiz.", outputData.getErrorMessage());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case success is expected, but prepareFailView was called with: " + error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("History Quiz", "creator3");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithNullQuizName() {
        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                fail("getLeaderboard should not be called when quiz name is null");
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz name is required.", error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData(null, "creator1");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithBlankQuizName() {
        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                fail("getLeaderboard should not be called when quiz name is blank");
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz name is required.", error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("   ", "creator1");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithEmptyQuizName() {
        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                fail("getLeaderboard should not be called when quiz name is empty");
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Quiz name is required.", error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("", "creator1");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithNullCreatorUsername() {
        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                fail("getLeaderboard should not be called when creator username is null");
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Creator username is required.", error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("Math Quiz", null);
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithBlankCreatorUsername() {
        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                fail("getLeaderboard should not be called when creator username is blank");
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Creator username is required.", error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("Math Quiz", "   ");
        interactor.execute(inputData);
    }

    @Test
    void testExecuteFailWithEmptyCreatorUsername() {
        // Create mock data access object
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
                // Not used in this test
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                fail("getLeaderboard should not be called when creator username is empty");
                return null;
            }
        };

        // Create mock presenter
        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
                fail("Use case failure is expected, but prepareSuccessView was called");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Creator username is required.", error);
            }
        };

        // Create interactor and execute
        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("Math Quiz", "");
        interactor.execute(inputData);
    }

    @Test
    void testConstructor() {
        // Test that constructor properly initializes dependencies
        LeaderboardDataAccessInterface dataAccess = new LeaderboardDataAccessInterface() {
            @Override
            public void saveQuizResult(String quizName, String creatorUsername, QuizResult quizResult) {
            }

            @Override
            public Leaderboard getLeaderboard(String quizName, String creatorUsername) {
                return null;
            }
        };

        ViewLeaderboardOutputBoundary presenter = new ViewLeaderboardOutputBoundary() {
            @Override
            public void prepareSuccessView(ViewLeaderboardOutputData outputData) {
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        ViewLeaderboardInteractor interactor = new ViewLeaderboardInteractor(presenter, dataAccess);
        assertNotNull(interactor);

        // Verify it works by executing with valid input
        ViewLeaderboardInputData inputData = new ViewLeaderboardInputData("Test Quiz", "testCreator");
        interactor.execute(inputData);
    }
}

