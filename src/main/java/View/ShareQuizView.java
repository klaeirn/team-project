package view;

import interface_adapter.share_quiz.ShareQuizViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.take_shared_quiz.TakeSharedQuizState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShareQuizView extends JPanel implements ActionListener, PropertyChangeListener {

    private final ShareQuizViewModel shareQuizViewModel;
    private final JTextField hashInfo;
    private static final String VIEW_NAME = "share quiz view";

    private ViewManagerModel viewManagerModel;
    private final JButton backButton;

    public ShareQuizView(ShareQuizViewModel shareQuizViewModel,
                         ViewManagerModel viewManagerModel) {
        this.shareQuizViewModel = shareQuizViewModel;
        this.viewManagerModel = viewManagerModel;
        this.shareQuizViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        final JLabel title = new JLabel("Share Quiz Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);

        String hash = shareQuizViewModel.getState().getHash();
        System.out.println("initial hash: " + hash);

        if (hash == null || hash.isEmpty()) {
            hash = "There was an error hashing! Please make sure your Java installation is okay";
        }

        hashInfo = new JTextField(hash);
        hashInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(hashInfo);

        backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (viewManagerModel != null) {
                    viewManagerModel.setState("select existing quiz");
                    viewManagerModel.firePropertyChange();
                }
            }
        });
    }


    public String getViewName() {
        return VIEW_NAME;
    }

    public void setViewManagerModel(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // hook up buttons here later if needed

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if ("state".equals(evt.getPropertyName())) {
            String newHash = shareQuizViewModel.getState().getHash();
            System.out.println("updated hash: " + newHash);

            if (newHash == null || newHash.isEmpty()) {
                newHash = "There was an error hashing! Please make sure your Java installation is okay.";
            }

            hashInfo.setText(newHash);
            revalidate();
            repaint();
        }
    }
}
