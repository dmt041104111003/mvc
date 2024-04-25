package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OverLayDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private JPanel contentPanel;
    private JLabel imageLabel;
    private JLabel infoLabel;
    private JLabel titleLabel;
    private Point initialClick;

    public OverLayDialog(JFrame parent) {
        super(parent, true);
        setUndecorated(true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });
        contentPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - initialClick.x;
                int deltaY = e.getY() - initialClick.y;
                setLocation(getLocation().x + deltaX, getLocation().y + deltaY);
            }
        });
        getContentPane().add(contentPanel);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(2, 5, 2, 5));
        titlePanel.setBackground(Color.LIGHT_GRAY);

        titleLabel = new JLabel("Overlay");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titlePanel.add(titleLabel, BorderLayout.WEST);

        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.addActionListener(e -> setVisible(false));
        titlePanel.add(closeButton, BorderLayout.EAST);

        contentPanel.add(titlePanel, BorderLayout.NORTH);

        imageLabel = new JLabel();
        contentPanel.add(imageLabel, BorderLayout.CENTER);

        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(infoLabel, BorderLayout.SOUTH);
    }

    public void showImage(String imagePath) {
        titleLabel.setText("How to Play");
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setText("");
        setVisible(true);
    }

    public void showHighScore(int highScore) {
        titleLabel.setText("High Score");
        imageLabel.setIcon(null);
        infoLabel.setText("High Score: " + highScore);
        infoLabel.setHorizontalAlignment(JLabel.CENTER);

        setVisible(true);
    }
}