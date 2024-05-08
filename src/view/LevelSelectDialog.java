package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.logic;
public class LevelSelectDialog extends JDialog {
	private static final long serialVersionUID = 1L;
    private final frame parent;
    private final JPanel levelPanel;
    private final JLabel[] levelLabels;
    private final logic game;

    public LevelSelectDialog(JFrame owner, frame parent) {
        super(owner, "Select Level", true);
        this.parent = parent;
        this.game = parent.getGame(); 
        setSize(300, 400);
        setResizable(false);
        setLocationRelativeTo(owner);
        JLabel backgroundLabel = new JLabel(new ImageIcon("design/background-4.jpg"));
        backgroundLabel.setLayout(new BorderLayout());
        levelPanel = new JPanel();
        levelPanel.setLayout(new GridLayout(4, 3, 10, 10)); 
        levelPanel.setOpaque(false);
        levelLabels = new JLabel[11];
        for (int i = 0; i <  11; i++) {
            final int level = i;
            levelLabels[i] = new JLabel("Level " + (level));
            levelLabels[i].setForeground(new Color(173, 216, 230));
            levelLabels[i].setFont(parent.getPixelFont().deriveFont(20f));
            levelLabels[i].setHorizontalAlignment(SwingConstants.CENTER); 
            levelLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	parent.setSelectMode(true);
                    parent.startGameWithLevel(level); 
                    dispose(); 
                }
                @Override
                public void mouseEntered(MouseEvent e) {levelLabels[level].setForeground(Color.WHITE);}
                @Override
                public void mouseExited(MouseEvent e) {levelLabels[level].setForeground(new Color(173, 216, 230));}
            });
            levelPanel.add(levelLabels[i]);
        }
        backgroundLabel.add(levelPanel, BorderLayout.CENTER);
        add(backgroundLabel);     
    }

}