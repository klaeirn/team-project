package entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for QuizFactory.
 */
public class QuizFactoryTest {

    private QuizFactory factory;
    private List<Question> questions;

    @BeforeEach
    public void setUp() {
        factory = new QuizFactory();
        questions = new ArrayList<>();

        List<String> options1 = new ArrayList<>();
        options1.add("A");
        options1.add("B");
        Question q1 = new Question("Question 1?", options1, "A");

        List<String> options2 = new ArrayList<>();
        options2.add("X");
        options2.add("Y");
        Question q2 = new Question("Question 2?", options2, "X");

        questions.add(q1);
        questions.add(q2);
    }

    @Test
    public void testCreateQuiz() {
        Quiz quiz = factory.createQuiz("Test Quiz", "creator", "Science", questions);

        assertNotNull(quiz);
        assertEquals("Test Quiz", quiz.getName());
        assertEquals("creator", quiz.getCreatorUsername());
        assertEquals("Science", quiz.getCategory());
        assertEquals(2, quiz.getQuestions().size());
    }

    @Test
    public void testCreateMultipleQuizzes() {
        Quiz quiz1 = factory.createQuiz("Quiz 1", "user1", "Math", questions);
        Quiz quiz2 = factory.createQuiz("Quiz 2", "user2", "History", questions);

        assertNotSame(quiz1, quiz2);
        assertEquals("Quiz 1", quiz1.getName());
        assertEquals("Quiz 2", quiz2.getName());
        assertEquals("user1", quiz1.getCreatorUsername());
        assertEquals("user2", quiz2.getCreatorUsername());
    }

    @Test
    public void testCreateQuizWithEmptyQuestions() {
        List<Question> emptyQuestions = new ArrayList<>();
        Quiz quiz = factory.createQuiz("Empty Quiz", "creator", "General", emptyQuestions);

        assertNotNull(quiz);
        assertEquals(0, quiz.getQuestions().size());
        assertTrue(quiz.getQuestions().isEmpty());
    }

    @Test
    public void testCreateQuizWithSingleQuestion() {
        List<Question> singleQuestion = new ArrayList<>();
        List<String> options = new ArrayList<>();
        options.add("Yes");
        options.add("No");
        singleQuestion.add(new Question("Single Q?", options, "Yes"));

        Quiz quiz = factory.createQuiz("Single Question Quiz", "creator", "Test", singleQuestion);

        assertEquals(1, quiz.getQuestions().size());
    }
}

