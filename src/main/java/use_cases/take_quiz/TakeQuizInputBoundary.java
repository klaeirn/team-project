package use_cases.take_quiz;

public interface TakeQuizInputBoundary {
    void execute(TakeQuizInputData inputData);
    void nextQuestion();
    void previousQuestion();
    void setAnswer(int questionIndex, String answer);
    void submitQuiz();
}

