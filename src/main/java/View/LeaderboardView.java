package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.leaderboard.LeaderboardController;
import interface_adapter.leaderboard.LeaderboardViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for displaying leaderboard information.
 * For quickstart quizzes, shows a message that no leaderboard is available.
 */
public class LeaderboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "leaderboard";
    private LeaderboardController leaderboardController;
    private final LeaderboardViewModel leaderboardViewModel;
    private ViewManagerModel viewManagerModel;

    private final JLabel messageLabel;
    private final JButton backButton;

    public LeaderboardView(LeaderboardViewModel leaderboardViewModel) {
        this.leaderboardViewModel = leaderboardViewModel;
        this.leaderboardViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        // Message panel
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messageLabel = new JLabel("No Leaderboard for Local Quickstart Quiz");
        messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 16));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setBorder(new EmptyBorder(50, 20, 20, 20));
        messagePanel.add(Box.createVerticalGlue());
        messagePanel.add(messageLabel);
        messagePanel.add(Box.createVerticalGlue());

        // Back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Back");
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

        add(messagePanel, BorderLayout.CENTER);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // No state changes to handle for now
    }
}

