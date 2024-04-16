package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class frame {

    private JFrame jframe;

    private JPanel startPanel;
    private JPanel gamePanel;
    private JPanel gridPanel;
    private JPanel queuePanel;
    private JPanel gameOverPanel;
    private JPanel pauseCoverPanel;
    private JPanel[][] gameGrid;

    private JLabel titleLabel;
    private JLabel backgroundLabel;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private JLabel linesLabel;
    private JLabel holdImgLabel;
    private JLabel gameOverScoreLabel;
    private JLabel highScoreLabel;
    private JLabel[] queuePicLabels;
    private Font pixelFont;

    public frame() {

        frame_tetris();
        initFont();
        initStartPanel();
        initGameOverMessage();
        initGamePanel();
        initPausePanel();
        initGridPanel();
        initGameGrid();
        initScorePanel();
        initLevelPanel();
        initLinesPanel();
        initQueuePanel();
        initHoldPanel();

        initFrameBackground();

        jframe.setVisible(true);
    }


    private void frame_tetris() {
        jframe = new JFrame("Game Tetris");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLayout(null);
        jframe.setResizable(false);
        jframe.setSize(418, 561);
    }

 
    private void initFont() {
        try {
            File fontFile = new File("design/font.ttf");
            Font newFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            pixelFont = newFont.deriveFont(20f);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }


    private void initFrameBackground() {
        try {
        	ImageIcon backgroundImage = new ImageIcon(ImageIO.read(new File("design/background.jpg")));
            Image scaledImage = backgroundImage.getImage().getScaledInstance(418, 561, Image.SCALE_SMOOTH);
            ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
            backgroundLabel = new JLabel(scaledBackgroundImage);
            backgroundLabel.setBounds(0,0, 402, 522);
            jframe.add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    private void initStartPanel() {
        startPanel = new JPanel();
        startPanel.setBounds(10, 110, 382, 200);
        startPanel.setOpaque(false);
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel("Tetris");
        titleLabel.setFont(pixelFont);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(pixelFont.deriveFont(70f));
        startPanel.add(titleLabel);

        JLabel startGameLabel = new JLabel("Start");
        startGameLabel.setFont(pixelFont);
        startGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameLabel.setFont(pixelFont.deriveFont(40f));
        startPanel.add(startGameLabel);

        JLabel switchThemeLabel = new JLabel("Switch Theme");
        switchThemeLabel.setFont(pixelFont);
        switchThemeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchThemeLabel.setFont(pixelFont.deriveFont(40f));
        startPanel.add(switchThemeLabel);

        jframe.add(startPanel);

        startGameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                startPanel.setVisible(false);
                gamePanel.setVisible(true);
    
            }

            public void mouseEntered(MouseEvent e) {
                startGameLabel.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                startGameLabel.setForeground(new Color(51, 51, 51));
            }
        });

        switchThemeLabel.addMouseListener(new MouseAdapter() {
  
            public void mouseEntered(MouseEvent e) {
                switchThemeLabel.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                switchThemeLabel.setForeground(new Color(51, 51, 51));
            }
        });
    }
    private void initGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setBounds(0, 0, 402, 522);
        gamePanel.setOpaque(false);
        gamePanel.setVisible(false);
        gamePanel.setLayout(null);
        jframe.add(gamePanel);
    }
    private void initGridPanel() {
        gridPanel = new JPanel();
        gridPanel.setBounds(10, 10, 252, 502);
        gridPanel.setBorder(new LineBorder(Color.BLACK, 2));
        gridPanel.setOpaque(false);
        gridPanel.setLayout(null);
        gamePanel.add(gridPanel);
    }
    private void initGameGrid() {
        gameGrid = new JPanel[10][20];
        int y = 1;
        for (int i = 0; i < 20; i++) {
            int x = 1;
            for (int j = 0; j < 10; j++) {
                gameGrid[j][i] = new JPanel();
                gameGrid[j][i].setBounds(x, y, 25, 25);
                gameGrid[j][i].setBackground(Color.WHITE);
                gameGrid[j][i].setOpaque(false);
                gridPanel.add(gameGrid[j][i]);
                x += 25;
            }
            y += 25;
        }
    }
    private void initScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setBounds(272, 10, 120, 30);
        scorePanel.setOpaque(false);
        scorePanel.setBorder(new LineBorder(Color.BLACK, 2));
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(pixelFont);
        scorePanel.add(scoreLabel);
        gamePanel.add(scorePanel);
    }
    private void initLevelPanel() {
        JPanel levelPanel = new JPanel();
        levelPanel.setBounds(272, 50, 120, 30);
        levelPanel.setOpaque(false);
        levelPanel.setBorder(new LineBorder(Color.BLACK, 2));
        levelLabel = new JLabel("Level: 0");
        levelLabel.setFont(pixelFont);
        levelPanel.add(levelLabel);
        gamePanel.add(levelPanel);
    }
    private void initLinesPanel() {
        JPanel linesPanel = new JPanel();
        linesPanel.setBounds(272, 90, 120, 30);
        linesPanel.setOpaque(false);
        linesPanel.setBorder(new LineBorder(Color.BLACK, 2));
        linesLabel = new JLabel("Lines: 0");
        linesLabel.setFont(pixelFont);
        linesPanel.add(linesLabel);
        gamePanel.add(linesPanel);
    }
    private void initQueuePanel() {
        queuePanel = new JPanel();
        queuePanel.setBounds(272, 130, 120, 190);
        queuePanel.setBorder(new LineBorder(Color.BLACK, 2));
        queuePanel.setLayout(null);
        queuePanel.setOpaque(false);
        gamePanel.add(queuePanel);
        queuePicLabels  = new JLabel[3];
        queuePicLabels[0] = new JLabel();
        queuePicLabels[1] = new JLabel();
        queuePicLabels[2] = new JLabel();
    }
    private void initHoldPanel() {
        JLabel holdLabel = new JLabel("Hold Block (H)");
        holdLabel.setFont(pixelFont);
        JPanel holdPanel = new JPanel();
        holdPanel.setBounds(272, 330, 120, 90);
        holdPanel.setBackground(null);
        holdPanel.setOpaque(false);
        holdPanel.setBorder(new LineBorder(Color.BLACK, 2));
        holdPanel.add(holdLabel);
        holdImgLabel = new JLabel();
        holdPanel.add(holdImgLabel);
        gamePanel.add(holdPanel);
    }
    private void initPausePanel() {
        pauseCoverPanel = new JPanel();
        pauseCoverPanel.setBounds(10, 10, 252, 502);
        pauseCoverPanel.setBorder(new LineBorder(Color.BLACK, 2));
        pauseCoverPanel.setBackground(new Color(51, 51, 51, 150));
        pauseCoverPanel.setVisible(false);
        gamePanel.add(pauseCoverPanel);

        JLabel pauseCoverLabel = new JLabel("Paused");
        pauseCoverLabel.setFont(pixelFont.deriveFont(70f));
        pauseCoverLabel.setForeground(Color.WHITE);
        pauseCoverPanel.add(pauseCoverLabel);

        JPanel pausePanel = new JPanel();
        pausePanel.setBounds(272, 430, 120, 30);
        pausePanel.setOpaque(false);
        pausePanel.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel pauseLabel = new JLabel("Pause (P)");
        pauseLabel.setFont(pixelFont.deriveFont(26f));
        pausePanel.add(pauseLabel);

        gamePanel.add(pausePanel);

        pausePanel.addMouseListener(new MouseAdapter() {
     

            public void mouseEntered(MouseEvent e) {
                pauseLabel.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                pauseLabel.setForeground(new Color(51, 51, 51));
            }
        });
    }
    private void initGameOverMessage() {
        gameOverPanel = new JPanel();
        gameOverPanel.setBounds(10, 110, 382, 200);
        gameOverPanel.setOpaque(false);
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));

        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setFont(pixelFont.deriveFont(70f));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverPanel.add(gameOverLabel);

        highScoreLabel = new JLabel("High Score: 0");
        highScoreLabel.setFont(pixelFont.deriveFont(40f));
        highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverPanel.add(highScoreLabel);

        gameOverScoreLabel = new JLabel("Score: 0");
        gameOverScoreLabel.setFont(pixelFont.deriveFont(40f));
        gameOverScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverPanel.add(gameOverScoreLabel);

        JLabel playAgainLabel = new JLabel("Play Again?");
        playAgainLabel.setFont(pixelFont.deriveFont(40f));
        playAgainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameOverPanel.add(playAgainLabel);

        playAgainLabel.addMouseListener(new MouseAdapter() {
  
            public void mouseEntered(MouseEvent e) {
                playAgainLabel.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                playAgainLabel.setForeground(new Color(51, 51, 51));
            }
        });
        gameOverPanel.setVisible(false);
        jframe.add(gameOverPanel);
    }

 
}


 






