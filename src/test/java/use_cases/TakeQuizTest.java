package use_cases;

import entities.Question;
import entities.Quiz;
import org.junit.jupiter.api.Test;
import use_cases.take_quiz.TakeQuizInputData;
import use_cases.take_quiz.TakeQuizInteractor;
import use_cases.take_quiz.TakeQuizOutputBoundary;
import use_cases.take_quiz.TakeQuizOutputData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TakeQuizTest {

    @Test
    void successExecuteDisplaysFirstQuestion() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                assertEquals("Question 1", outputData.getCurrentQuestion().getTitle());
                assertEquals(1, outputData.getCurrentQuestionIndex());
                assertEquals(3, outputData.getTotalQuestions());
                assertFalse(outputData.isLastQuestion());
                assertEquals(quiz, outputData.getQuiz());
                assertEquals("testuser", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        TakeQuizInputData inputData = new TakeQuizInputData(quiz, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void nullQuizFails() {
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("No quiz available to take.", error);
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        TakeQuizInputData inputData = new TakeQuizInputData(null, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void emptyQuizFails() {
        Quiz emptyQuiz = new Quiz("Empty Quiz", "creator", "Test", new ArrayList<>());
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("No quiz available to take.", error);
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        TakeQuizInputData inputData = new TakeQuizInputData(emptyQuiz, "testuser");
        interactor.execute(inputData);
    }

    @Test
    void nextQuestionMovesToSecondQuestion() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                if (callCount[0] == 1) {
                    // First call from execute()
                    assertEquals("Question 1", outputData.getCurrentQuestion().getTitle());
                    assertEquals(1, outputData.getCurrentQuestionIndex());
                } else if (callCount[0] == 2) {
                    // Second call from nextQuestion()
                    assertEquals("Question 2", outputData.getCurrentQuestion().getTitle());
                    assertEquals(2, outputData.getCurrentQuestionIndex());
                    assertFalse(outputData.isLastQuestion());
                }
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.nextQuestion();
        assertEquals(2, callCount[0]);
    }

    @Test
    void previousQuestionMovesToFirstQuestion() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                if (callCount[0] == 1) {
                    assertEquals(1, outputData.getCurrentQuestionIndex());
                } else if (callCount[0] == 2) {
                    assertEquals(2, outputData.getCurrentQuestionIndex());
                } else if (callCount[0] == 3) {
                    assertEquals(1, outputData.getCurrentQuestionIndex());
                }
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.nextQuestion();
        interactor.previousQuestion();
        assertEquals(3, callCount[0]);
    }

    @Test
    void lastQuestionIsMarkedAsLast() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                if (callCount[0] == 3) {
                    assertEquals("Question 3", outputData.getCurrentQuestion().getTitle());
                    assertEquals(3, outputData.getCurrentQuestionIndex());
                    assertTrue(outputData.isLastQuestion());
                }
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.nextQuestion();
        interactor.nextQuestion();
        assertTrue(callCount[0] >= 3);
    }

    @Test
    void setAnswerUpdatesUserAnswers() {
        Quiz quiz = createValidQuiz();
        final boolean[] answerSet = {false};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                if (answerSet[0]) {
                    Map<Integer, String> answers = outputData.getUserAnswers();
                    assertEquals("Option A", answers.get(0));
                }
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.setAnswer(0, "Option A");
        answerSet[0] = true;

        Map<Integer, String> userAnswers = interactor.getUserAnswers();
        assertEquals("Option A", userAnswers.get(0));
    }

    @Test
    void setAnswerRejectsInvalidOption() {
        Quiz quiz = createValidQuiz();

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Initial display
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.setAnswer(0, "Invalid Option");

        Map<Integer, String> userAnswers = interactor.getUserAnswers();
        assertNull(userAnswers.get(0));
    }

    @Test
    void cannotMoveNextBeyondLastQuestion() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Allow all updates
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.nextQuestion();
        interactor.nextQuestion();
        interactor.nextQuestion(); // Should not move beyond question 3

        assertEquals(2, interactor.getCurrentQuestionIndex());
    }

    @Test
    void cannotMovePreviousBeforeFirstQuestion() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Allow all updates
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.previousQuestion(); // Should not move before question 1

        assertEquals(0, interactor.getCurrentQuestionIndex());
    }

    @Test
    void submitQuizDoesNotThrowException() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Allow all updates
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));

        assertDoesNotThrow(() -> interactor.submitQuiz());
    }

    @Test
    void getQuizReturnsCorrectQuiz() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Allow all updates
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));

        assertEquals(quiz, interactor.getQuiz());
    }

    @Test
    void getUsernameReturnsCorrectUsername() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Allow all updates
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));

        assertEquals("testuser", interactor.getUsername());
    }

    private Quiz createValidQuiz() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option A"));
        questions.add(new Question("Question 2", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option B"));
        questions.add(new Question("Question 3", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option C"));
        return new Quiz("Test Quiz", "creator", "Test", questions);
    }
}