package entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for Question entity.
 */
public class QuestionTest {

    private Question question;
    private List<String> options;

    @BeforeEach
    public void setUp() {
        options = new ArrayList<>();
        options.add("Option A");
        options.add("Option B");
        options.add("Option C");
        options.add("Option D");

        question = new Question("What is 2+2?", options, "Option B");
    }

    @Test
    public void testQuestionCreation() {
        assertEquals("What is 2+2?", question.getTitle());
        assertEquals(4, question.getOptions().size());
        assertEquals("Option B", question.getAnswer());
    }

    @Test
    public void testValidateAnswerCorrect() {
        assertTrue(question.validate_answer("Option B"));
    }

    @Test
    public void testValidateAnswerIncorrect() {
        assertFalse(question.validate_answer("Option A"));
        assertFalse(question.validate_answer("Option C"));
        assertFalse(question.validate_answer("Wrong Answer"));
    }

    @Test
    public void testSetTitle() {
        question.setTitle("New Question Title");

        assertEquals("New Question Title", question.getTitle());
    }

    @Test
    public void testSetOptions() {
        List<String> newOptions = new ArrayList<>();
        newOptions.add("New Option 1");
        newOptions.add("New Option 2");

        question.setOptions(newOptions);

        assertEquals(2, question.getOptions().size());
        assertEquals("New Option 1", question.getOptions().get(0));
    }

    @Test
    public void testGetAnswer() {
        assertEquals("Option B", question.getAnswer());
    }

    @Test
    public void testValidateAnswerCaseSensitive() {
        assertFalse(question.validate_answer("option b"));
        assertFalse(question.validate_answer("OPTION B"));
    }
}

