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
    void quizWithNullQuestionsFails() {
        Quiz invalidQuiz = new Quiz("Invalid Quiz", "creator", "Test", null);
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
        TakeQuizInputData inputData = new TakeQuizInputData(invalidQuiz, "testuser");
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
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                if (callCount[0] == 2) {
                    // Second call after setAnswer
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

        // Verify presenter was called twice (execute + setAnswer)
        assertEquals(2, callCount[0]);
    }

    @Test
    void setAnswerRejectsInvalidOption() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                // Verify user answers never contains invalid option
                Map<Integer, String> answers = outputData.getUserAnswers();
                assertNull(answers.get(0));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.setAnswer(0, "Invalid Option");

        // Only one call to presenter (from execute), setAnswer doesn't trigger update for invalid option
        assertEquals(1, callCount[0]);
    }


    @Test
    void setAnswerWithInvalidIndexDoesNothing() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));

        // Try to set answer at invalid indices
        interactor.setAnswer(-1, "Option A");
        interactor.setAnswer(999, "Option A");

        // Only one call from execute
        assertEquals(1, callCount[0]);
    }

    @Test
    void setAnswerWithNullOptionsDoesNothing() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Q1", null, "Answer"));
        Quiz quiz = new Quiz("Test", "creator", "Test", questions);

        final int[] callCount = {0};
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.setAnswer(0, "Option A");

        // Only one call from execute
        assertEquals(1, callCount[0]);
    }

    @Test
    void setAnswerOnDifferentQuestionDoesNotTriggerView() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        // Set answer for question 1 (index 1), but we're on question 0
        interactor.setAnswer(1, "Option B");

        // Only one call from execute (setAnswer on different question doesn't trigger view)
        assertEquals(1, callCount[0]);
    }

    @Test
    void cannotMoveNextBeyondLastQuestion() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
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

        // Only 3 calls: execute + 2 successful nextQuestion calls (3rd nextQuestion does nothing)
        assertEquals(3, callCount[0]);
    }

    @Test
    void cannotMovePreviousBeforeFirstQuestion() {
        Quiz quiz = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                // Always on first question
                assertEquals(1, outputData.getCurrentQuestionIndex());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
        interactor.previousQuestion(); // Should not move before question 1

        // Only 1 call from execute, previousQuestion does nothing
        assertEquals(1, callCount[0]);
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
    void quizPassedThroughOutputData() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Verify quiz is passed through output data
                assertEquals(quiz, outputData.getQuiz());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
    }

    @Test
    void usernamePassedThroughOutputData() {
        Quiz quiz = createValidQuiz();
        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                // Verify username is passed through output data
                assertEquals("testuser", outputData.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz, "testuser"));
    }

    @Test
    void executeResetsState() {
        Quiz quiz1 = createValidQuiz();
        Quiz quiz2 = createValidQuiz();
        final int[] callCount = {0};

        TakeQuizOutputBoundary presenter = new TakeQuizOutputBoundary() {
            @Override
            public void prepareQuestionView(TakeQuizOutputData outputData) {
                callCount[0]++;
                // After second execute, should be back to first question
                if (callCount[0] == 4) {
                    assertEquals(1, outputData.getCurrentQuestionIndex());
                    assertEquals(0, outputData.getUserAnswers().size());
                }
            }

            @Override
            public void prepareFailView(String error) {
            }
        };

        TakeQuizInteractor interactor = new TakeQuizInteractor(presenter);
        interactor.execute(new TakeQuizInputData(quiz1, "user1"));
        interactor.nextQuestion();
        interactor.setAnswer(1, "Option B"); // Set answer for current question (index 1)
        // Execute again with new quiz - should reset state
        interactor.execute(new TakeQuizInputData(quiz2, "user2"));

        assertEquals(4, callCount[0]);
    }

    private Quiz createValidQuiz() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Question 1", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option A"));
        questions.add(new Question("Question 2", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option B"));
        questions.add(new Question("Question 3", Arrays.asList("Option A", "Option B", "Option C", "Option D"), "Option C"));
        return new Quiz("Test Quiz", "creator", "Test", questions);
    }
}