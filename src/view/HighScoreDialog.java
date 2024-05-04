package view;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HighScoreDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public HighScoreDialog(JFrame parent, frame parentFrame, int highScore) {
        super(parent, "High Score", true);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);

        JLabel infoLabel = new JLabel("High Score: " + highScore);
        infoLabel.setFont(parentFrame.getPixelFont().deriveFont(25f));
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(infoLabel, BorderLayout.CENTER);
    }
}