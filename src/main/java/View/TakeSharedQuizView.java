package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.take_shared_quiz.TakeSharedQuizController;
import interface_adapter.take_shared_quiz.TakeSharedQuizState;
import interface_adapter.take_shared_quiz.TakeSharedQuizViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

/**
 * View where the user enters a shared quiz code and starts the quiz.
 * Talks to the TakeSharedQuizController and is updated by TakeSharedQuizViewModel.
 */

public class TakeSharedQuizView extends JPanel implements ActionListener,
        PropertyChangeListener {

    private final String viewName = "take shared quiz";

    // viewModel for take shared quiz screen.
    private final TakeSharedQuizViewModel viewModel;

    // this is View Model for the logged-in user, need this so i can get the
    // username from the logged-in.
    private final LoggedInViewModel loggedInViewModel;

    private final JTextField hashInputField = new JTextField(15);
    private final JLabel hashErrorField = new JLabel();

    // Controller wcich calls use case (take shared quiz).
    private TakeSharedQuizController controller;

    // Switch between thr views.
    private ViewManagerModel viewManagerModel;

    private final JButton startButton;
    private final JButton backButton;

    public TakeSharedQuizView(TakeSharedQuizViewModel viewModel,
                              LoggedInViewModel loggedInViewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.loggedInViewModel = loggedInViewModel;

        final JLabel title = new JLabel("Take Shared Quiz");
        title.setAlignmentX(CENTER_ALIGNMENT);

        final LabelTextPanel hashInfo = new LabelTextPanel(
                new JLabel("Quiz code"), hashInputField);

        final JPanel buttons = new JPanel();
        startButton = new JButton("Start");
        buttons.add(startButton);
        backButton = new JButton("Back");
        buttons.add(backButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(hashInfo);
        this.add(hashErrorField);
        this.add(buttons);

        hashInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final TakeSharedQuizState currentState = viewModel.getState();
                currentState.setHash(hashInputField.getText());
                viewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        // Start button, call the Controller with hash + current username
        startButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(startButton) && controller
                                !=
                                null) {
                            final TakeSharedQuizState currentState =
                                    viewModel.getState();

                            controller.execute(currentState.getHash(),
                                    loggedInViewModel.getState().getUsername());
                        }
                    }
                }
        );

        //        backButton.addActionListener(
        //                new ActionListener() {
        //                    public void actionPerformed(ActionEvent evt) {
        //                        if (evt.getSource().equals(backButton) &&
        //                                viewManagerModel != null) {
        //                            viewManagerModel.setState("logged in");
        //                            viewManagerModel.firePropertyChange();
        //                        }
        //                    }
        //                }
        //        );

        // Back button clears the state and switches view
        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)
                                &&
                                viewManagerModel != null) {
                            final TakeSharedQuizState currentState =
                                    viewModel.getState();
                            currentState.setHash("");
                            currentState.setErrorMessage(null);
                            viewModel.setState(currentState);

                            hashInputField.setText("");
                            hashErrorField.setText("");

                            viewManagerModel.setState("quiz menu");
                            viewManagerModel.firePropertyChange();
                        }
                    }
                }

        );
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(TakeSharedQuizController controller) {
        this.controller = controller;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final TakeSharedQuizState state =
                (TakeSharedQuizState) evt.getNewValue();
        if (state == null) {
            return;
        }
        if (!hashInputField.getText().equals(state.getHash())) {
            hashInputField.setText(state.getHash());
        }

        final String error = state.getErrorMessage();
        hashErrorField.setText(Objects.requireNonNullElse(error, ""));
        //        if (error == null) {
        //            hashErrorField.setText("");
        //        } else {
        //            hashErrorField.setText(error);
        //        }
    }
}
