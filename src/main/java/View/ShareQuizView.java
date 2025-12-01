package view;

import interface_adapter.share_quiz.ShareQuizViewModel;

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

    public ShareQuizView(ShareQuizViewModel shareQuizViewModel) {
        this.shareQuizViewModel = shareQuizViewModel;
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
    }

    public String getViewName() {
        return VIEW_NAME;
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
