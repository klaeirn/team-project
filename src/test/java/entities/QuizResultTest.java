package entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for QuizResult entity.
 */
public class QuizResultTest {

    @Test
    public void testQuizResultCreation() {
        QuizResult result = new QuizResult("testUser", 8, 10);

        assertEquals("testUser", result.getUsername());
        assertEquals(8, result.getScore());
        assertEquals(10, result.getTotalQuestions());
        assertEquals(80.0, result.getPercentage(), 0.01);
    }

    @Test
    public void testPercentageCalculation() {
        QuizResult result = new QuizResult("user1", 5, 10);
        assertEquals(50.0, result.getPercentage(), 0.01);
    }

    @Test
    public void testZeroTotalQuestions() {
        QuizResult result = new QuizResult("user1", 5, 0);
        assertEquals(0.0, result.getPercentage(), 0.01);
    }

    @Test
    public void testSetScore() {
        QuizResult result = new QuizResult("user1", 5, 10);
        result.setScore(7);

        assertEquals(7, result.getScore());
        assertEquals(70.0, result.getPercentage(), 0.01);
    }

    @Test
    public void testSetUsername() {
        QuizResult result = new QuizResult("user1", 5, 10);
        result.setUsername("newUser");

        assertEquals("newUser", result.getUsername());
    }
}

