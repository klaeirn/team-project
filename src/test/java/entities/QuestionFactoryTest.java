package entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for QuestionFactory.
 */
public class QuestionFactoryTest {

    private QuestionFactory factory;
    private List<String> options;

    @BeforeEach
    public void setUp() {
        factory = new QuestionFactory();
        options = new ArrayList<>();
        options.add("Option A");
        options.add("Option B");
        options.add("Option C");
    }

    @Test
    public void testCreateQuestion() {
        Question question = factory.createQuestion("Test Question?", options, "Option B");

        assertNotNull(question);
        assertEquals("Test Question?", question.getTitle());
        assertEquals(3, question.getOptions().size());
        assertEquals("Option B", question.getAnswer());
    }

    @Test
    public void testCreateMultipleQuestions() {
        Question question1 = factory.createQuestion("Q1?", options, "A");
        Question question2 = factory.createQuestion("Q2?", options, "B");

        assertNotSame(question1, question2);
        assertEquals("Q1?", question1.getTitle());
        assertEquals("Q2?", question2.getTitle());
    }

    @Test
    public void testCreateQuestionWithEmptyOptions() {
        List<String> emptyOptions = new ArrayList<>();
        Question question = factory.createQuestion("Question?", emptyOptions, "Answer");

        assertNotNull(question);
        assertEquals(0, question.getOptions().size());
    }

    @Test
    public void testCreateQuestionWithSingleOption() {
        List<String> singleOption = new ArrayList<>();
        singleOption.add("Only Option");
        Question question = factory.createQuestion("Question?", singleOption, "Only Option");

        assertEquals(1, question.getOptions().size());
        assertTrue(question.validate_answer("Only Option"));
    }
}

