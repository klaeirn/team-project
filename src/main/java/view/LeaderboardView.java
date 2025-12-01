package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.view_leaderboard.ViewLeaderboardState;
import interface_adapter.view_leaderboard.ViewLeaderboardViewModel;
import entities.Leaderboard;
import entities.QuizResult;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * The View for displaying leaderboard information.
 * For quickstart quizzes, shows a message that no leaderboard is available.
 * For shared quizzes, displays the ranked leaderboard.
 */
public class LeaderboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "leaderboard";
    private LeaderboardController leaderboardController;
    private final ViewLeaderboardViewModel viewLeaderboardViewModel;
    private ViewManagerModel viewManagerModel;

    private final JLabel titleLabel;
    private final JPanel leaderboardPanel;
    private final JLabel messageLabel;
    private final JButton backButton;
    private final JPanel contentPanel;

    public LeaderboardView(ViewLeaderboardViewModel viewLeaderboardViewModel) {
        this.viewLeaderboardViewModel = viewLeaderboardViewModel;
        this.viewLeaderboardViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        titleLabel = new JLabel("Leaderboard");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(20, 10, 10, 10));
        titlePanel.add(titleLabel);

        // Leaderboard panel (will be populated with data)
        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));
        leaderboardPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Message label (for quickstart quizzes)
        messageLabel = new JLabel("No Leaderboard for Local Quickstart Quiz");
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 16));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBorder(new EmptyBorder(50, 20, 50, 20));

        // Scroll pane for leaderboard
        JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        // Back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back to Results");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leaderboardController != null) {
                    leaderboardController.navigateBackToResults();
                } else if (viewManagerModel != null) {
                    viewManagerModel.setState("view results");
                    viewManagerModel.firePropertyChange();
                }
            }
        });
        buttonPanel.add(backButton);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Use a container panel to switch between scrollPane and messageLabel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(messageLabel, BorderLayout.CENTER); // Default to message

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }

    public void setLeaderboardController(LeaderboardController controller) {
        this.leaderboardController = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click: " + e.getActionCommand());
    }

    private void updateDisplay(ViewLeaderboardState state) {
        Leaderboard leaderboard = state.getLeaderboard();
        String error = state.getErrorMessage();

        // Clear previous content
        leaderboardPanel.removeAll();
        contentPanel.removeAll();

        if (error != null && !error.isEmpty()) {
            // Show error message
            messageLabel.setText(error);
            contentPanel.add(messageLabel, BorderLayout.CENTER);
        } else if (leaderboard == null || leaderboard.isEmpty()) {
            // Show "No Leaderboard" message for quickstart or empty leaderboard
            messageLabel.setText("No Leaderboard for Local Quickstart Quiz");
            contentPanel.add(messageLabel, BorderLayout.CENTER);
        } else {
            // Display leaderboard
            JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(null);
            contentPanel.add(scrollPane, BorderLayout.CENTER);

            // Add quiz name if available
            if (state.getQuizName() != null && !state.getQuizName().isEmpty()) {
                JLabel quizNameLabel = new JLabel("Quiz: " + state.getQuizName());
                quizNameLabel.setFont(new Font(quizNameLabel.getFont().getName(), Font.BOLD, 14));
                quizNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                quizNameLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
                leaderboardPanel.add(quizNameLabel);
            }

            // Add header
            JPanel headerPanel = new JPanel(new BorderLayout());
            JLabel rankHeader = new JLabel("Rank");
            JLabel usernameHeader = new JLabel("Username");
            JLabel scoreHeader = new JLabel("Score");
            rankHeader.setFont(new Font(rankHeader.getFont().getName(), Font.BOLD, 12));
            usernameHeader.setFont(new Font(usernameHeader.getFont().getName(), Font.BOLD, 12));
            scoreHeader.setFont(new Font(scoreHeader.getFont().getName(), Font.BOLD, 12));
            headerPanel.add(rankHeader, BorderLayout.WEST);
            headerPanel.add(usernameHeader, BorderLayout.CENTER);
            headerPanel.add(scoreHeader, BorderLayout.EAST);
            headerPanel.setBorder(new EmptyBorder(5, 0, 5, 0));
            leaderboardPanel.add(headerPanel);
            leaderboardPanel.add(new JSeparator());

            // Add leaderboard entries
            List<QuizResult> results = leaderboard.getRankedResults();
            for (int i = 0; i < results.size(); i++) {
                QuizResult result = results.get(i);
                int rank = i + 1;

                JPanel entryPanel = new JPanel(new BorderLayout());
                JLabel rankLabel = new JLabel(String.valueOf(rank));
                JLabel usernameLabel = new JLabel(result.getUsername());
                JLabel scoreLabel = new JLabel(result.getScore() + " / " + result.getTotalQuestions());

                entryPanel.add(rankLabel, BorderLayout.WEST);
                entryPanel.add(usernameLabel, BorderLayout.CENTER);
                entryPanel.add(scoreLabel, BorderLayout.EAST);
                entryPanel.setBorder(new EmptyBorder(5, 0, 5, 0));

                leaderboardPanel.add(entryPanel);
                if (i < results.size() - 1) {
                    leaderboardPanel.add(new JSeparator());
                }
            }
        }

        revalidate();
        repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ViewLeaderboardState state = (ViewLeaderboardState) evt.getNewValue();
        if (state != null) {
            updateDisplay(state);
        }
    }
}

