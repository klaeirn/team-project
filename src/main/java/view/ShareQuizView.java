package view;

import interface_adapter.change_username.ChangeUsernameViewModel;
import interface_adapter.share_quiz.ShareQuizViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShareQuizView extends JPanel implements ActionListener, PropertyChangeListener {
    private final ShareQuizViewModel shareQuizViewModel;
    public ShareQuizView(ShareQuizViewModel shareQuizViewModel) {
        this.shareQuizViewModel = shareQuizViewModel;
    }

    public Object getViewName() {
        return "share quiz view";
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
