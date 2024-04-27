package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import controller.logic;
import controller.ScoreManager;
import controller.RowMananger;
//import controller.SoundEffectManager;
    public class frame {
 
        private JFrame jframe;
        private JPanel startPanel;
         JPanel gamePanel;
        private JPanel gridPanel;
        private JPanel queuePanel;
        private JPanel gameOverPanel;
        private JPanel pauseCoverPanel;
        private JPanel[][] gameGrid;
        private JPanel winGamePanel;
        private JLabel finalScoreLabel1;

        private JLabel titleLabel;
        private JLabel backgroundLabel;
        JLabel scoreLabel;
        private JLabel levelLabel;
        private JLabel linesLabel;
        private JLabel holdImgLabel;
        private JLabel gameOverScoreLabel;
        private JLabel highScoreLabel;
        private JLabel[] queuePicLabels;

        public Font pixelFont;

        private static final int MAX_LEVEL = 10;

        private JPanel scorePanel_2;
        private JLabel scoreLabel_2;
        private JPanel scorePanel_3;
        private JLabel scoreLabel_3;
//        private OverLayDialog OverLayDialog_;

 

        private Thread titleLabelThread;
        private Thread blockGravityThread;


        private boolean animateTitle = true;

        private final logic game;
		private JLabel high_score;

		private JLabel howtoplaygame;
		private int highestScore;
//		private boolean isSoundPlaying = false;

		private JLabel music;
        public frame(logic newGame) {
            game = newGame;
            SoundManager.toggleSound();
            highestScore = game.getHighScoreFromFile();
            frame_tetris();
            initFont();
            initStartPanel();
            initGameOverMessage();
            initGamePanel();
            initPausePanel();
            initGridPanel();
            initGameGrid();
            ScoreView.initScorePanel(this);
            initLevelPanel();
            initLinesPanel();
            ImageManager.initImageIcons();
            initQueuePanel();
            initHoldPanel();

            ImageManager.initImageIcons();
//            OverLayDialog_ = new OverLayDialog(jframe, this);
            initFrameBackground();

            jframe.setVisible(true);
        }
        
        private void frame_tetris() {
            jframe = new JFrame("Game Tetris");
            jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jframe.getContentPane().setLayout(null);
            jframe.setResizable(false);
            jframe.setSize(418, 561);
            jframe.addKeyListener(keyListener);
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
        private void replaceBackgroundLabel() {
            ImageIcon backgroundImage = BackgroundManager.getBackgroundImageForLevel(game.getLevel());
            int frameWidth = jframe.getWidth();
            int frameHeight = jframe.getHeight();
            Image scaledImage = backgroundImage.getImage().getScaledInstance(frameWidth, frameHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
            JLabel newBackgroundLabel = new JLabel(scaledBackgroundImage);
            newBackgroundLabel.setBounds(0, 0, frameWidth, frameHeight);

            if (backgroundLabel != null) {
                jframe.getContentPane().remove(backgroundLabel);
            }
            jframe.getContentPane().add(newBackgroundLabel);
            backgroundLabel = newBackgroundLabel;
        }
        private void initFrameBackground() {
            try {
                replaceBackgroundLabel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void resetAllComponents() {
            game.resetGame();
            updateHoldImage();
            updateQueue();
            updateScoreLabel();
            updateLinesLabel();
            updateLevelLabel();
            initFrameBackground();
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 10; j++) {
                    gameGrid[j][i].setBackground(Color.WHITE);
                    gameGrid[j][i].setBorder(null);
                    gameGrid[j][i].setOpaque(false);
                }
            }
        }
        private void initStartPanel() {
            startPanel = new JPanel();
            startPanel.setBounds(10, 77, 392, 445);
            startPanel.setOpaque(false);
            startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

            titleLabel = new JLabel("Tetris");
            titleLabel.setForeground(new Color(173, 216, 230));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setFont(pixelFont.deriveFont(55f));
            startPanel.add(titleLabel);
            animateTitleLabel();

            JLabel startGameLabel = new JLabel("Start");
            startGameLabel.setForeground(new Color(173, 216, 230));
            startGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            startGameLabel.setFont(pixelFont.deriveFont(25f));
            startPanel.add(startGameLabel);
            
            high_score = new JLabel("High score");
            high_score.setForeground(new Color(173, 216, 230));
            high_score.setAlignmentX(Component.CENTER_ALIGNMENT);
            high_score.setFont(pixelFont.deriveFont(25f));
            startPanel.add(high_score);
            
            howtoplaygame = new JLabel("How to play");
            howtoplaygame.setForeground(new Color(173, 216, 230));
            howtoplaygame.setAlignmentX(Component.CENTER_ALIGNMENT);
            howtoplaygame.setFont(pixelFont.deriveFont(25f));
            startPanel.add(howtoplaygame);

            music = new JLabel("Music");
            music.setForeground(new Color(173, 216, 230));
            music.setAlignmentX(Component.CENTER_ALIGNMENT);
            music.setFont(pixelFont.deriveFont(25f));
            startPanel.add(music);
            
            JLabel switchThemeLabel = new JLabel("Switch Theme");
            switchThemeLabel.setForeground(new Color(173, 216, 230));
            switchThemeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            switchThemeLabel.setFont(pixelFont.deriveFont(25f));
            startPanel.add(switchThemeLabel);

            JLabel exit = new JLabel("Exit");
            exit.setForeground(new Color(173, 216, 230));
            exit.setAlignmentX(Component.CENTER_ALIGNMENT);
            exit.setFont(pixelFont.deriveFont(25f));
            startPanel.add(exit);

            jframe.getContentPane().add(startPanel);

            startGameLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    animateTitle = false;
                    titleLabelThread.interrupt();
                    startPanel.setVisible(false);
                    gamePanel.setVisible(true);
                    resetAllComponents();
                    game.startGame();
                    updateQueue();
                    updateCurrentBlock(true);
                    gravity();
                }

                public void mouseEntered(MouseEvent e) {
                    startGameLabel.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    startGameLabel.setForeground(new Color(173, 216, 230));
                }
            });
            music.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                	VolumeDialog volumeDialog = new VolumeDialog(jframe);
                    volumeDialog.setVisible(true);
                }

                public void mouseEntered(MouseEvent e) {
                	music.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	music.setForeground(new Color(173, 216, 230));
                }
            });
            switchThemeLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
//                    changeBackgroundImage();
                }

                public void mouseEntered(MouseEvent e) {
                    switchThemeLabel.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    switchThemeLabel.setForeground(new Color(173, 216, 230));
                }
            });
            exit.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }

                public void mouseEntered(MouseEvent e) {
                	exit.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	exit.setForeground(new Color(173, 216, 230));
                }
            });
            
            howtoplaygame.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                	HowToPlayDialog howToPlayDialog = new HowToPlayDialog(jframe, frame.this);
                    howToPlayDialog.setVisible(true);
                }
                public void mouseEntered(MouseEvent e) {
                    howtoplaygame.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    howtoplaygame.setForeground(new Color(173, 216, 230));
                }
            });
            high_score.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                	HighScoreDialog highScoreDialog = new HighScoreDialog(jframe, frame.this, highestScore);
                    highScoreDialog.setVisible(true);

                }

                public void mouseEntered(MouseEvent e) {
                	high_score.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	high_score.setForeground(new Color(173, 216, 230));
                }
            });
        }
       
        
        private void initGamePanel() {
            gamePanel = new JPanel();
            gamePanel.setBounds(0, 0, 402, 522);
            gamePanel.setOpaque(false);
            gamePanel.setVisible(false);
            gamePanel.setLayout(null);
            jframe.getContentPane().add(gamePanel);
        }
        private void initGridPanel() {
            gridPanel = new JPanel();
            gridPanel.setBounds(10, 10, 252, 502);
            gridPanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
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

        private void initLevelPanel() {
            JPanel levelPanel = new JPanel();
            levelPanel.setBounds(272, 49, 120, 35);
            levelPanel.setOpaque(false);
            levelPanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            levelLabel = new JLabel("Level: 0");
            levelLabel.setForeground(new Color(173, 216, 230));
            levelLabel.setFont(pixelFont.deriveFont(14f));
            levelPanel.add(levelLabel);
            gamePanel.add(levelPanel);
        }
        private void initLinesPanel() {
            JPanel linesPanel = new JPanel();
            linesPanel.setBounds(272, 86, 120, 30);
            linesPanel.setOpaque(false);
            linesPanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            linesLabel = new JLabel("Lines: 0");
            linesLabel.setFont(pixelFont.deriveFont(14f));
            linesLabel.setForeground(new Color(173, 216, 230));
            linesPanel.add(linesLabel);
            gamePanel.add(linesPanel);
        }

        private void initQueuePanel() {
            queuePanel = new JPanel();
            queuePanel.setBounds(272, 118, 120, 189);
            queuePanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            queuePanel.setLayout(null);
            queuePanel.setOpaque(false);
            gamePanel.add(queuePanel);
            queuePicLabels = new JLabel[3];
            for (int i = 0; i < 3; i++) {
                queuePicLabels[i] = new JLabel();
                queuePicLabels[i].setBounds(10, 22 + (i * 60), 100, 25); 
                queuePanel.add(queuePicLabels[i]);
            }
//            updateQueue();
        }
        private void initHoldPanel() {
            JLabel holdLabel = new JLabel("Hold Block (H)");
            holdLabel.setForeground(new Color(173, 216, 230));
            holdLabel.setFont(pixelFont.deriveFont(15f));
            JPanel holdPanel = new JPanel();
            holdPanel.setBounds(272, 317, 120, 91);
            holdPanel.setBackground(null);
            holdPanel.setOpaque(false);
            holdPanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            holdPanel.add(holdLabel);
            holdImgLabel = new JLabel();
            holdPanel.add(holdImgLabel);
            gamePanel.add(holdPanel);
            
            scorePanel_2 = new JPanel();
            scorePanel_2.setOpaque(false);
            scorePanel_2.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            scorePanel_2.setBounds(272, 418, 120, 25);
            gamePanel.add(scorePanel_2);
            
            scoreLabel_2 = new JLabel("Music");
            scoreLabel_2.setForeground(new Color(173, 216, 230));
            scoreLabel_2.setFont(pixelFont.deriveFont(15f));
            scorePanel_2.add(scoreLabel_2);
            scorePanel_2.setLayout(new BoxLayout(scorePanel_2, BoxLayout.Y_AXIS));

            scoreLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT); 

            scorePanel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
            scorePanel_2.setAlignmentY(Component.CENTER_ALIGNMENT); 
            
            scoreLabel_2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                	VolumeDialog volumeDialog = new VolumeDialog(jframe);
                    volumeDialog.setVisible(true);
                    
                }

                public void mouseEntered(MouseEvent e) {
                	scoreLabel_2.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	scoreLabel_2.setForeground(new Color(173, 216, 230));
                }
            });
            scorePanel_3 = new JPanel();
            scorePanel_3.setOpaque(false);
            scorePanel_3.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            scorePanel_3.setBounds(272, 485, 120, 27);
            gamePanel.add(scorePanel_3);
            
            scoreLabel_3 = new JLabel("Exit");
            scoreLabel_3.setForeground(new Color(173, 216, 230));
            scoreLabel_3.setFont(pixelFont.deriveFont(19f));
            scorePanel_3.add(scoreLabel_3);
            scorePanel_3.setLayout(new BoxLayout(scorePanel_3, BoxLayout.Y_AXIS));

            scoreLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT); 

            scorePanel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
            scorePanel_3.setAlignmentY(Component.CENTER_ALIGNMENT);
            
            scoreLabel_3.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }

                public void mouseEntered(MouseEvent e) {
                	scoreLabel_3.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	scoreLabel_3.setForeground(new Color(173, 216, 230));
                }
            });
          
        }
        private void initPausePanel() {
            pauseCoverPanel = new JPanel();
            pauseCoverPanel.setBounds(10, 10, 252, 502);
            pauseCoverPanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
            pauseCoverPanel.setBackground(new Color(51, 51, 51, 150));
            pauseCoverPanel.setVisible(false);
            gamePanel.add(pauseCoverPanel);

            JLabel pauseCoverLabel = new JLabel("Paused");
            pauseCoverLabel.setFont(pixelFont.deriveFont(65f));
            pauseCoverLabel.setForeground(Color.WHITE);
            pauseCoverPanel.add(pauseCoverLabel);

            JPanel pausePanel = new JPanel();
            pausePanel.setForeground(new Color(173, 216, 230));
            pausePanel.setBounds(272, 453, 120, 30);
            pausePanel.setOpaque(false);
            pausePanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));

            JLabel pauseLabel = new JLabel("Pause (P)");
            pauseLabel.setForeground(new Color(173, 216, 230));
            pauseLabel.setFont(pixelFont.deriveFont(19f));
            pausePanel.add(pauseLabel);
            pausePanel.setLayout(new BoxLayout(pausePanel, BoxLayout.Y_AXIS));

            pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            pauseLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


            gamePanel.add(pausePanel);
            JLabel homeLabel = new JLabel("Home");
            homeLabel.setForeground(new Color(173, 216, 230));
            homeLabel.setFont(pixelFont.deriveFont(25f));
            homeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            pauseCoverPanel.add(homeLabel);

            homeLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int option = JOptionPane.showOptionDialog(
                        jframe,
                        "Do you want to continue the current game or exit to the main menu?",
                        "Home",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Continue", "Exit to Main Menu"},
                        "Continue"
                    );

                    if (option == JOptionPane.NO_OPTION) {
                        gamePanel.setVisible(false);
                        startPanel.setVisible(true);
                        pauseCoverPanel.setVisible(false);
                        game.resetGame();
                    } else {
                        pauseGame(); 
                    }
                }

                public void mouseEntered(MouseEvent e) {
                    homeLabel.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    homeLabel.setForeground(new Color(173, 216, 230));
                }
            });
            pausePanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    pauseGame();
                }

                public void mouseEntered(MouseEvent e) {
                    pauseLabel.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    pauseLabel.setForeground(new Color(173, 216, 230));
                }
            });
        }
        private void initGameOverMessage() {
            gameOverPanel = new JPanel();
            gameOverPanel.setBounds(10, 110, 392, 412);
            gameOverPanel.setOpaque(false);
            gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));

            JLabel gameOverLabel = new JLabel("Game Over");
            gameOverLabel.setFont(pixelFont.deriveFont(55f));
            gameOverLabel.setForeground(new Color(173, 216, 230));
            gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(gameOverLabel);

            highScoreLabel = new JLabel("High Score: 0");
            highScoreLabel.setFont(pixelFont.deriveFont(25f));
            highScoreLabel.setForeground(new Color(173, 216, 230));
            highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(highScoreLabel);

            gameOverScoreLabel = new JLabel("Score: 0");
            gameOverScoreLabel.setForeground(new Color(173, 216, 230));
            gameOverScoreLabel.setFont(pixelFont.deriveFont(25f));
            gameOverScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(gameOverScoreLabel);
            
            JLabel homeLabel1 = new JLabel("Home");
            homeLabel1.setForeground(new Color(173, 216, 230));
            homeLabel1.setFont(pixelFont.deriveFont(25f));
            homeLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(homeLabel1);
            
            JLabel playAgainLabel = new JLabel("Play Again?");
            playAgainLabel.setForeground(new Color(173, 216, 230));
            playAgainLabel.setFont(pixelFont.deriveFont(25f));
            playAgainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(playAgainLabel);

            JLabel exit = new JLabel("Exit");
            exit.setFont(pixelFont);
            exit.setForeground(new Color(173, 216, 230));
            exit.setAlignmentX(Component.CENTER_ALIGNMENT);
            exit.setFont(pixelFont.deriveFont(25f));
            gameOverPanel.add(exit);

            winGamePanel = new JPanel();
            winGamePanel.setBounds(10, 110, 392, 200);
            winGamePanel.setOpaque(false);
            winGamePanel.setLayout(new BoxLayout(winGamePanel, BoxLayout.Y_AXIS));

            JLabel winGameLabel = new JLabel("You Win!");
            winGameLabel.setFont(pixelFont.deriveFont(55f));
            winGameLabel.setForeground(new Color(173, 216, 230));
            winGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            winGamePanel.add(winGameLabel);
            
            finalScoreLabel1 = new JLabel();
            finalScoreLabel1.setText("Final Score: " + game.getScore());
            finalScoreLabel1.setFont(pixelFont.deriveFont(25f));
            finalScoreLabel1.setForeground(new Color(173, 216, 230));
            finalScoreLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            winGamePanel.add(finalScoreLabel1);

            JLabel homeLabel2 = new JLabel("Home");
            homeLabel2.setForeground(new Color(173, 216, 230));
            homeLabel2.setFont(pixelFont.deriveFont(25f));
            homeLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            winGamePanel.add(homeLabel2);
            
            JLabel exit1 = new JLabel("Exit");
            exit1.setFont(pixelFont);
            exit1.setForeground(new Color(173, 216, 230));
            exit1.setAlignmentX(Component.CENTER_ALIGNMENT);
            exit1.setFont(pixelFont.deriveFont(25f));
            winGamePanel.add(exit1);

            
            winGamePanel.setVisible(false);
            jframe.getContentPane().add(winGamePanel);
            homeLabel1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                        gameOverPanel.setVisible(false);
                        startPanel.setVisible(true);
                        game.resetGame();
                    
                }

                public void mouseEntered(MouseEvent e) {
                    homeLabel1.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    homeLabel1.setForeground(new Color(173, 216, 230));
                }
            });
            homeLabel2.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                	 winGamePanel.setVisible(false);
                        startPanel.setVisible(true);
                        game.resetGame();
                    
                }

                public void mouseEntered(MouseEvent e) {
                    homeLabel2.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    homeLabel2.setForeground(new Color(173, 216, 230));
                }
            });
            playAgainLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    resetGame();
                }

                public void mouseEntered(MouseEvent e) {
                    playAgainLabel.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                    playAgainLabel.setForeground(new Color(173, 216, 230));
                }
                
            });

            exit.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }

                public void mouseEntered(MouseEvent e) {
                	exit.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	exit.setForeground(new Color(173, 216, 230));
                }
            });
            exit1.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }

                public void mouseEntered(MouseEvent e) {
                	exit1.setForeground(Color.WHITE);
                }

                public void mouseExited(MouseEvent e) {
                	exit1.setForeground(new Color(173, 216, 230));
                }
            });
            gameOverPanel.setVisible(false);
            jframe.getContentPane().add(gameOverPanel);
        }
        public void updateQueue() {
            SwingUtilities.invokeLater(() -> {
                for (int i = 0; i < 3; i++) {
                    int blockType = game.getBlockQueue()[i];
                    ImageIcon icon = ImageManager.getBlockIcon(blockType);
                    switch (blockType) {
                        case 0 -> queuePicLabels[i].setBounds(10, 22 + (i * 60), 100, 25);
                        case 1, 2, 4, 5, 6 -> queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                        case 3 -> queuePicLabels[i].setBounds(35, 10 + (i * 60), 50, 50);
                    }
                    queuePicLabels[i].setIcon(icon);
                }
            });
        }
     
        private void updateHoldImage() {
        	ImageManager.updateHoldImage(holdImgLabel, game);
        }
        private void updateScoreLabel() {
            ScoreManager.updateScoreLabel(scoreLabel, game);
        }
        private void updateLevelLabel() {
            ScoreManager.updateLevelLabel(levelLabel, game);
            replaceBackgroundLabel();
        }
        private void updateLinesLabel() {
            ScoreManager.updateLinesLabel(linesLabel, game);
        }
        private void updateCurrentBlock(boolean isNewBlock) {
            ImageManager.updateCurrentBlock(gameGrid, game, isNewBlock);
        }
        private final KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 37) {
                    game.moveSide(0);
                    updateCurrentBlock(false);
                } else if (e.getKeyCode() == 39) {
                    game.moveSide(1);
                    updateCurrentBlock(false);
                } else if (e.getKeyCode() == 40) {
                    game.setFastFall(true);
                } else if (e.getKeyCode() == 38) {
                    game.rotateBlock();
                    updateCurrentBlock(false);
                } else if (e.getKeyCode() == 72) {
                    holdBlock();
                } else if (e.getKeyCode() == 80) {
                    pauseGame();
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    moveBlockDownFast();
                }
            }

            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 40 || e.getKeyCode() == 83) {
                    game.setFastFall(false);
                }
            }
        };

        private void moveBlockDownFast() {
            while (!game.isTouchingBottomOrBlock()) {
                game.updatePreviousBlockPos();
                game.moveBlockDown();
                updateCurrentBlock(false);
            }
        }
   
        private void animateTitleLabel() {
            titleLabelThread = new Thread(() -> {
                int count = 0;
                while (animateTitle) {
                    if (count < 5) {
                        titleLabel.setLocation(titleLabel.getX(), titleLabel.getY() + 1);
                    } else if (count < 9) {
                        titleLabel.setLocation(titleLabel.getX(), titleLabel.getY() - 1);
                    } else {
                        count = 0;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {}
                    count++;
                }
            });
            titleLabelThread.start();
        }
        private void gravity() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            blockGravityThread = new Thread(() -> {
                while (game.isRunning()) {
                    if (!game.isTouchingBottomOrBlock()) {
                        game.updatePreviousBlockPos();
                        game.moveBlockDown();
                        updateCurrentBlock(false);
                        try {
                            for (int i = 0; i < game.getFallDelay()/50 && !game.isFastFall(); i++) {
                                Thread.sleep(50);
                            }
                            if (game.isFastFall()) {
                                Thread.sleep(50);
                            }
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        game.addCurrentToSetBlock();
                        removeRows(game.checkForFullRows());
                        updateScoreLabel();
                        updateLevelLabel();
                        updateLinesLabel();
                        initFrameBackground();
                        if (game.getLevel() >= MAX_LEVEL) { 
                            blockGravityThread.interrupt(); 
                            showWinGamePanel();
                            break;}
                        else if (!game.checkIfGameOver()) {
                            game.nextBlock();
                            game.setHeldThisTurn(false);
                            updateQueue();
                            updateCurrentBlock(true);
                        } else {
                            blockGravityThread.interrupt();
                            gameOverAnimation();
                            game.checkIfHighScore();
                            showGameOverMessage();
                        }
                    }
                }
            });
            blockGravityThread.start();
        }
        private void showGameOverMessage() {
            gamePanel.setVisible(false);
            gameOverPanel.setVisible(true);
            gameOverScoreLabel.setText("Score: " + game.getScore());
            highScoreLabel.setText("High Score: " + game.getHighScoreFromFile());
            String name = JOptionPane.showInputDialog(jframe, "Enter your name:", "Game Over", JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.isEmpty()) {
                ScoreView.saveScore(name, game.getScore());
            }

        }
        private void showWinGamePanel() {
            gamePanel.setVisible(false);
            finalScoreLabel1.setText("Final Score: " + game.getScore());
            winGamePanel.add(finalScoreLabel1);            
            winGamePanel.setVisible(true);
            String name = JOptionPane.showInputDialog(jframe, "Enter your name:", "Game Won", JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.isEmpty()) {
                ScoreView.saveScore(name, game.getScore());
            }
            JOptionPane.showMessageDialog(jframe, "Congratulations! You've reached the maximum score of " + MAX_LEVEL + "!");
        }
        public void gameOverAnimation() {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 10; j++) {
                    if (gameGrid[j][i].getBackground() != Color.WHITE) {
                        int green = ThreadLocalRandom.current().nextInt(150, 251);
                        int blue = ThreadLocalRandom.current().nextInt(85, 186);
                        gameGrid[j][i].setBackground(new Color(250, green, blue));
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException ignored) {}
                        gameGrid[j][i].setBackground(Color.WHITE);
                        gameGrid[j][i].setBorder(null);
                        gameGrid[j][i].setOpaque(false);
                    }
                }
            }
        }

        public Font getPixelFont() {
            return pixelFont;
        }
        private void removeRows(ArrayList<Integer> rowsToRemove) {
            RowMananger.removeRows(gameGrid, game, rowsToRemove);
        }
        private void holdBlock() {
            if (!game.isHeldThisTurn()) {
                if (game.getCurrentHoldBlock() != -1) {
                    for (int i = 0; i < 4; i++) {
                        gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBackground(Color.WHITE);
                        gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBorder(null);
                        gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setOpaque(false);
                    }
                    int temp = game.getCurrentBlockType();
                    game.newCurrentBlock(game.getCurrentHoldBlock());
                    game.setCurrentHoldBlock(temp);
                    updateHoldImage();
                    updateCurrentBlock(true);
                } else {
                    game.setCurrentHoldBlock(game.getCurrentBlockType());
                    updateHoldImage();
                    for (int i = 0; i < 4; i++) {
                        gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBackground(Color.WHITE);
                        gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBorder(null);
                        gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setOpaque(false);
                    }
                    game.nextBlock();
                    updateCurrentBlock(true);
                    game.shuffleAndAddToQueue();
                    updateQueue();
                }
                game.setHeldThisTurn(true);
            }
        }
        private void pauseGame() {
            if (game.isRunning()) {
                game.setRunning(false);
                blockGravityThread.interrupt();
                pauseCoverPanel.setVisible(true);
            } else {
                game.setRunning(true);
                gravity();
                pauseCoverPanel.setVisible(false);
            }
        }
        private void resetGame() {
            game.resetGame();
            gameOverPanel.setVisible(false);
            gamePanel.setVisible(true);
            updateHoldImage();
            updateQueue();
            updateScoreLabel();
            updateLinesLabel();
            updateLevelLabel();
            gravity();
        }
    }