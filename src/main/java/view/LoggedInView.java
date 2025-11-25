package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_username.ChangeUsernameController;
import interface_adapter.create_quiz.CreateQuizController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.quiz_menu.QuizMenuController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import interface_adapter.change_username.ChangeUsernameViewModel;


/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    //    private final JLabel passwordErrorField = new JLabel();

//    private final JLabel username;

    private final JButton changeUsername;
    private final JButton takeQuizButton;
    private final JButton createQuizButton;
    private ChangeUsernameController  changeUsernameController;
    private QuizMenuController quizMenuController;
    private CreateQuizController createQuizController;

    // , ViewManagerModel viewManagerModel
    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Logged In Screen");
        this.add(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        final JLabel usernameInfo = new JLabel("Currently logged in: ");
//        username = new JLabel();

        final JPanel buttons = new JPanel();
        this.changeUsername = new JButton("Change Username");
        this.takeQuizButton = new JButton("Take Quiz");
        this.createQuizButton = new JButton("Create Quiz");
        buttons.add(takeQuizButton);
        buttons.add(changeUsername);
        buttons.add(createQuizButton);

        changeUsername.addActionListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        changeUsername.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent evt) {
                                                 if (evt.getSource().equals(changeUsername)) {
                                                     changeUsernameController.switchToChangeUsernameView();
                                                 }
                                             }
                                         }
        );

        // Navigate to the Quiz Menu when Take Quiz is clicked.
        takeQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (quizMenuController != null) {
                    quizMenuController.switchToQuizMenu();
                }
            }
        });
        createQuizButton.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent evt) {
                                                 if (evt.getSource().equals(createQuizButton)) {
                                                     createQuizController.switchToCreateQuizView();
                                                 }
                                             }
                                         }
        );

        this.add(title);
//        this.add(usernameInfo);
//        this.add(username);
        this.add(buttons);
    }
//
//    /**
//     * React to a button click that results in evt.
//     * @param evt the ActionEvent to react to
//     */
//    public void actionPerformed(ActionEvent evt) {
//        // TODO: execute the logout use case through the Controller
//        System.out.println("Click " + evt.getActionCommand());
//
//        String command = evt.getActionCommand();
//
//        if (command == "Change Username") {
//
//            LoggedInState state = loggedInViewModel.getState();
//            String currentUsername = state.getUsername();
//
//            changeUsernameController.execute(currentUsername);
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click: " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("username")) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setChangeUsernameController(ChangeUsernameController changeUsernameController) {
        this.changeUsernameController = changeUsernameController;
    }

    public void setQuizMenuController(QuizMenuController quizMenuController) {
        this.quizMenuController = quizMenuController;
    }

    public void setCreateQuizController(CreateQuizController createQuizController) {
        this.createQuizController = createQuizController;
    }

}