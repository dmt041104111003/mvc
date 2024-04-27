package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HowToPlayDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public HowToPlayDialog(JFrame parent, frame parentFrame) {
        super(parent, "How to Play", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel);

        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon("design/how.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(300, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(imageLabel, BorderLayout.CENTER);
    }
}