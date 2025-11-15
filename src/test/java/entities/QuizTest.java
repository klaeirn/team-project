package entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic unit tests for Quiz entity.
 */
public class QuizTest {

    private Quiz quiz;
    private List<Question> questions;
    private Question question1;
    private Question question2;
    private Question question3;

    @BeforeEach
    public void setUp() {
        questions = new ArrayList<>();

        List<String> options1 = new ArrayList<>();
        options1.add("A");
        options1.add("B");
        options1.add("C");
        question1 = new Question("Question 1?", options1, "A");

        List<String> options2 = new ArrayList<>();
        options2.add("X");
        options2.add("Y");
        options2.add("Z");
        question2 = new Question("Question 2?", options2, "Y");

        List<String> options3 = new ArrayList<>();
        options3.add("1");
        options3.add("2");
        question3 = new Question("Question 3?", options3, "1");

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);

        quiz = new Quiz("Math Quiz", "creator123", "Mathematics", questions);
    }

    @Test
    public void testQuizCreation() {
        assertEquals("Math Quiz", quiz.getName());
        assertEquals("creator123", quiz.getCreatorUsername());
        assertEquals("Mathematics", quiz.getCategory());
        assertEquals(3, quiz.getQuestions().size());
    }

    @Test
    public void testSetName() {
        quiz.setName("New Quiz Name");

        assertEquals("New Quiz Name", quiz.getName());
    }

    @Test
    public void testPreviewQuestions() {
        List<Question> previewed = quiz.previewQuestions();

        assertEquals(3, previewed.size());
        assertEquals(question1, previewed.get(0));
        assertEquals(question2, previewed.get(1));
    }

    @Test
    public void testAddQuestion() {
        List<String> newOptions = new ArrayList<>();
        newOptions.add("Yes");
        newOptions.add("No");
        Question newQuestion = new Question("New Question?", newOptions, "Yes");

        quiz.addQuestion(newQuestion);

        assertEquals(4, quiz.getQuestions().size());
        assertTrue(quiz.getQuestions().contains(newQuestion));
    }

    @Test
    public void testRemoveQuestionByTitle() {
        quiz.removeQuestion("Question 2?");

        assertEquals(2, quiz.getQuestions().size());
        assertFalse(quiz.getQuestions().contains(question2));
    }

    @Test
    public void testRemoveQuestionByObject() {
        quiz.removeQuestion(question1);

        assertEquals(2, quiz.getQuestions().size());
        assertFalse(quiz.getQuestions().contains(question1));
    }

    @Test
    public void testRemoveNonExistentQuestionByTitle() {
        int initialSize = quiz.getQuestions().size();
        quiz.removeQuestion("Non-existent Question");

        assertEquals(initialSize, quiz.getQuestions().size());
    }

    @Test
    public void testGetQuestions() {
        List<Question> retrievedQuestions = quiz.getQuestions();

        assertEquals(3, retrievedQuestions.size());
        assertEquals(questions, retrievedQuestions);
    }

    @Test
    public void testEmptyQuiz() {
        Quiz emptyQuiz = new Quiz("Empty Quiz", "creator", "General", new ArrayList<>());

        assertEquals(0, emptyQuiz.getQuestions().size());
        assertTrue(emptyQuiz.getQuestions().isEmpty());
    }
}

