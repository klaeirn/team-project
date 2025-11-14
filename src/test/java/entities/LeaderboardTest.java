package entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for Leaderboard entity.
 */
public class LeaderboardTest {

    private Leaderboard leaderboard;
    private List<QuizResult> testResults;

    @BeforeEach
    public void setUp() {
        testResults = new ArrayList<>();
        testResults.add(new QuizResult("alice", 9, 10));
        testResults.add(new QuizResult("bob", 7, 10));
        testResults.add(new QuizResult("charlie", 5, 10));

        leaderboard = new Leaderboard(1, testResults);
    }

    @Test
    public void testLeaderboardCreation() {
        assertEquals(1, leaderboard.getQuizId());
        assertEquals(3, leaderboard.getParticipantCount());
    }

    @Test
    public void testGetUserRank() {
        assertEquals(1, leaderboard.getUserRank("alice"));
        assertEquals(2, leaderboard.getUserRank("bob"));
        assertEquals(3, leaderboard.getUserRank("charlie"));
        assertEquals(-1, leaderboard.getUserRank("nonexistent"));
    }

    @Test
    public void testIsUserFirst() {
        assertTrue(leaderboard.isUserFirst("alice"));
        assertFalse(leaderboard.isUserFirst("bob"));
        assertFalse(leaderboard.isUserFirst("charlie"));
    }

    @Test
    public void testGetUserResult() {
        QuizResult result = leaderboard.getUserResult("alice");
        assertNotNull(result);
        assertEquals("alice", result.getUsername());
        assertEquals(9, result.getScore());

        assertNull(leaderboard.getUserResult("nonexistent"));
    }

    @Test
    public void testIsEmpty() {
        Leaderboard emptyLeaderboard = new Leaderboard(1, new ArrayList<>());
        assertTrue(emptyLeaderboard.isEmpty());
        assertFalse(leaderboard.isEmpty());
    }

    @Test
    public void testNullRankedResults() {
        Leaderboard nullLeaderboard = new Leaderboard(1, null);
        assertTrue(nullLeaderboard.isEmpty());
        assertEquals(0, nullLeaderboard.getParticipantCount());
    }

    @Test
    public void testGetTopResults() {
        List<QuizResult> topTwo = leaderboard.getTopResults(2);
        assertEquals(2, topTwo.size());
        assertEquals("alice", topTwo.get(0).getUsername());
        assertEquals("bob", topTwo.get(1).getUsername());
    }
}


