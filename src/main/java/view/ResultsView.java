package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_results.ViewResultsController;
import interface_adapter.view_results.ViewResultsState;
import interface_adapter.view_results.ViewResultsViewModel;
import entities.QuizResult;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for displaying quiz results. Shows the user's username, score, and provides navigation to leaderboard.
 */
public class ResultsView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "view results";
    private ViewResultsController viewResultsController;
    private final ViewResultsViewModel viewResultsViewModel;
    private ViewManagerModel viewManagerModel;

    private final JLabel usernameLabel;
    private final JLabel scoreLabel;
    private final JLabel errorLabel;
    private final JButton leaderboardButton;
    private final JButton backButton;

    public ResultsView(ViewResultsViewModel viewResultsViewModel) {
        this.viewResultsViewModel = viewResultsViewModel;
        this.viewResultsViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Username panel at the top
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.Y_AXIS));
        usernameLabel = new JLabel();
        usernameLabel.setFont(new Font(usernameLabel.getFont().getName(), Font.BOLD, 18));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(new EmptyBorder(20, 10, 10, 10));
        usernamePanel.add(usernameLabel);

        // Score panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font(scoreLabel.getFont().getName(), Font.BOLD, 16));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scorePanel.add(scoreLabel);
        scorePanel.setBorder(new EmptyBorder(10, 10, 20, 10));

        // Error label
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Button panel at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        leaderboardButton = new JButton("View Leaderboard");
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Navigate to leaderboard view (not implemented yet)
                // if (viewManagerModel != null) {
                //     viewManagerModel.setState("leaderboard");
                //     viewManagerModel.firePropertyChange(); <-- probably something like that
                // }
            }
        });
        backButton = new JButton("Back to Quiz Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManagerModel != null) {
                    viewManagerModel.setState("quiz menu");
                    viewManagerModel.firePropertyChange();
                }
            }
        });
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(backButton);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(usernamePanel, BorderLayout.NORTH);
        topPanel.add(scorePanel, BorderLayout.CENTER);
        topPanel.add(errorLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateDisplay(ViewResultsState state) {
        QuizResult quizResult = state.getQuizResult();

        if (quizResult == null) {
            return;
        }

        // Update username display at the top
        String username = quizResult.getUsername();
        if (username != null && !username.isEmpty()) {
            usernameLabel.setText(username);
        } else {
            usernameLabel.setText("User");
        }

        // Update score display
        int score = quizResult.getScore();
        int total = quizResult.getTotalQuestions();
        scoreLabel.setText("Score: " + score + " / " + total);
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewResultsController(ViewResultsController controller) {
        this.viewResultsController = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewResultsState state = (ViewResultsState) evt.getNewValue();
        if (state != null) {
            String error = state.getErrorMessage();
            if (error != null && !error.isEmpty()) {
                errorLabel.setText(error);
                usernameLabel.setText("");
                scoreLabel.setText("");
            } else {
                errorLabel.setText("");
                updateDisplay(state);
            }
        }
    }
}

