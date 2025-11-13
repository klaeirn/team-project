package view;

import interface_adapter.change_username.ChangeUsernameController;
import interface_adapter.change_username.ChangeUsernameState;
import interface_adapter.change_username.ChangeUsernameViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChangeUsernameView extends JPanel implements ActionListener, PropertyChangeListener {
    private ChangeUsernameController changeUsernameController = null;
    private final String viewName = "change username";
    private final ChangeUsernameViewModel changeUsernameViewModel;

    private final JTextField usernameField = new JTextField(15);
    private final JButton changeUsernameButton;

    public ChangeUsernameView(ChangeUsernameViewModel changeUsernameViewModel) {
        this.changeUsernameViewModel = changeUsernameViewModel;
        this.changeUsernameViewModel.addPropertyChangeListener(this);

        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("New Username"), usernameField);

        this.add(usernameInfo);

        changeUsernameButton = new JButton("Change Username");
        changeUsernameButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(changeUsernameButton)) {
                            final ChangeUsernameState currentState = changeUsernameViewModel.getState();
                            changeUsernameController.execute(currentState.getNewUsername());
                        }
                    }
                }
        );

        this.add(changeUsernameButton);

        usernameField.getDocument().addDocumentListener( new DocumentListener() {
            private void documentListenerHelper() {
                final ChangeUsernameState currentState = changeUsernameViewModel.getState();
                currentState.setNewUsername(usernameField.getText());
                changeUsernameViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper();} // might be able to take out these 3

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper();}

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper();}

        });
    }


    public String getViewName() {
        return viewName;
    }

    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    private void setFields(ChangeUsernameState state) {
        usernameField.setText(state.getNewUsername());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChangeUsernameState state = (ChangeUsernameState) evt.getNewValue();
        setFields(state);
    }

    public void setChangeUsernameController(ChangeUsernameController changeUsernameController) {
        this.changeUsernameController = changeUsernameController;
    }


}
