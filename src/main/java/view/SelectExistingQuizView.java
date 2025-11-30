package view;

import interface_adapter.select_existing_quiz.SelectExistingQuizController;
import interface_adapter.select_existing_quiz.SelectExistingQuizState;
import interface_adapter.select_existing_quiz.SelectExistingQuizViewModel;
import interface_adapter.quiz_menu.QuizMenuController;
import interface_adapter.share_quiz.ShareQuizController;
import entities.Quiz;
import entities.Question;
import entities.QuizFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The View for selecting an existing quiz from a list.
 */
public class SelectExistingQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "select existing quiz";
    private SelectExistingQuizController selectExistingQuizController;
    private QuizMenuController quizMenuController;
    private final SelectExistingQuizViewModel selectExistingQuizViewModel;
    private ShareQuizController shareQuizController;

    private final JList<String> quizList;
    private final JScrollPane quizScrollPane;
    private final JButton beginButton;
    private final JButton backButton;
    private final JButton shareButton;
    private final JLabel errorLabel;
    private List<Quiz> quizzes;

    public SelectExistingQuizView(SelectExistingQuizViewModel selectExistingQuizViewModel) {
        this.selectExistingQuizViewModel = selectExistingQuizViewModel;
        this.selectExistingQuizViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Select Existing Quiz");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        quizList = new JList<>(new String[]{"No quizzes available"});
        quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        quizList.setVisibleRowCount(10);
        quizScrollPane = new JScrollPane(quizList);
        quizScrollPane.setBorder(BorderFactory.createTitledBorder("Available Quizzes"));
        quizScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        quizScrollPane.setPreferredSize(new Dimension(500, 300));

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        beginButton = new JButton("Begin");
        backButton = new JButton("Back");
        shareButton = new JButton("Share Quiz");

        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftButtons.add(beginButton);
        leftButtons.add(backButton);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtons.add(shareButton);

        buttons.add(leftButtons, BorderLayout.WEST);
        buttons.add(rightButtons, BorderLayout.EAST);

        beginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = quizList.getSelectedIndex();
                boolean exactlyOneSelected = selectedIndex != -1 && quizzes != null && selectedIndex < quizzes.size();
                if (exactlyOneSelected) {
                    Quiz selectedQuiz = quizzes.get(selectedIndex);
                    if (selectExistingQuizController != null) {
                        selectExistingQuizController.beginQuiz(selectedQuiz);
                    }
                } else {
                    JOptionPane.showMessageDialog(SelectExistingQuizView.this,
                            "Please select a quiz from the list.",
                            "No Quiz Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizMenuController != null) {
                    quizMenuController.switchToQuizMenu();
                } else if (selectExistingQuizController != null) {
                    selectExistingQuizController.switchToQuizMenu();
                }
            }
        });
        shareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = quizList.getSelectedIndex();
                boolean exactlyOneSelected = selectedIndex != -1 && quizzes != null && selectedIndex < quizzes.size();
                if (exactlyOneSelected) {
                    Quiz selectedQuiz = quizzes.get(selectedIndex);
                    if (shareQuizController != null) {
                        shareQuizController.execute(selectedQuiz);
                    } else {
                        JOptionPane.showMessageDialog(SelectExistingQuizView.this,
                                "Sharing is not available right now.",
                                "Share Quiz",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SelectExistingQuizView.this,
                            "Please select a quiz to share.",
                            "No Quiz Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(Box.createVerticalStrut(10));
        this.add(quizScrollPane);
        this.add(Box.createVerticalStrut(10));
        this.add(errorLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttons);

        loadQuizzesFromFile();
    }

    private void loadQuizzesFromFile() {
        try {
            Path quizPath = Path.of("src/quizzes.json");
            if (!Files.exists(quizPath) || Files.size(quizPath) == 0) {
                quizList.setListData(new String[]{"No quizzes available"});
                return;
            }

            String jsonStr = Files.readString(quizPath);
            if (jsonStr.trim().isEmpty()) {
                quizList.setListData(new String[]{"No quizzes available"});
                return;
            }

            JSONArray quizArray = new JSONArray(jsonStr);
            quizzes = new ArrayList<>();
            QuizFactory quizFactory = new QuizFactory();

            for (int i = 0; i < quizArray.length(); i++) {
                JSONObject quizJson = quizArray.getJSONObject(i);

                String name = quizJson.getString("quizName");
                String creator = quizJson.getString("quizCreator");
                String category = quizJson.getString("category");

                JSONArray questionsJson = quizJson.getJSONArray("questions");
                List<Question> questionList = new ArrayList<>();

                for (int j = 0; j < questionsJson.length(); j++) {
                    JSONObject qJson = questionsJson.getJSONObject(j);

                    String questionName = qJson.getString("questionName");
                    String answer = qJson.getString("answer");

                    JSONArray optionsJson = qJson.getJSONArray("options");
                    List<String> options = new ArrayList<>();

                    for (int k = 0; k < optionsJson.length(); k++) {
                        options.add(optionsJson.getString(k));
                    }

                    questionList.add(new Question(questionName, options, answer));
                }

                quizzes.add(quizFactory.createQuiz(name, creator, category, questionList));
            }

            if (!quizzes.isEmpty()) {
                String[] quizNames = new String[quizzes.size()];
                for (int i = 0; i < quizzes.size(); i++) {
                    Quiz quiz = quizzes.get(i);
                    String name = quiz.getName() != null ? quiz.getName() : "Unnamed Quiz";
                    String category = quiz.getCategory() != null ? quiz.getCategory() : "No Category";
                    String creator = quiz.getCreatorUsername() != null ? quiz.getCreatorUsername() : "Unknown";
                    int questionCount = quiz.getQuestions() != null ? quiz.getQuestions().size() : 0;
                    quizNames[i] = String.format("%s (%s) - Created by: %s - %d questions",
                            name, category, creator, questionCount);
                }
                quizList.setListData(quizNames);
            } else {
                quizList.setListData(new String[]{"No quizzes available"});
            }

        } catch (Exception e) {
            errorLabel.setText("Error loading quizzes: " + e.getMessage());
            quizList.setListData(new String[]{"No quizzes available"});
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSelectExistingQuizController(SelectExistingQuizController controller) {
        this.selectExistingQuizController = controller;
    }

    public void setShareQuizController(ShareQuizController controller) {
        this.shareQuizController = controller;
    }

    public void setQuizMenuController(QuizMenuController controller) {
        this.quizMenuController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SelectExistingQuizState state = (SelectExistingQuizState) evt.getNewValue();
        if (state != null) {
            quizzes = state.getAvailableQuizzes();
            if (quizzes != null && !quizzes.isEmpty()) {
                String[] quizNames = new String[quizzes.size()];
                for (int i = 0; i < quizzes.size(); i++) {
                    Quiz quiz = quizzes.get(i);
                    if (quiz != null) {
                        String name = quiz.getName() != null ? quiz.getName() : "Unnamed Quiz";
                        String category = quiz.getCategory() != null ? quiz.getCategory() : "No Category";
                        String creator = quiz.getCreatorUsername() != null ? quiz.getCreatorUsername() : "Unknown";
                        int questionCount = quiz.getQuestions() != null ? quiz.getQuestions().size() : 0;
                        quizNames[i] = String.format("%s (%s) - Created by: %s - %d questions",
                                name, category, creator, questionCount);
                    } else {
                        quizNames[i] = "Unknown Quiz";
                    }
                }
                quizList.setListData(quizNames);
            } else {
                quizList.setListData(new String[]{"No quizzes available"});
            }

            String error = state.getErrorMessage();
            if (error != null && !error.isEmpty()) {
                errorLabel.setText(error);
            } else {
                errorLabel.setText("");
            }
        }
    }
}

