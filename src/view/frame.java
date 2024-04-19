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
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import controller.logic;

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
        private JLabel highLabel;
        private JLabel[] queuePicLabels;

        private Font pixelFont;
		private JLabel imageLabel;

        private ImageIcon iBlockIcon;
        private ImageIcon jBlockIcon;
        private ImageIcon lBlockIcon;
        private ImageIcon oBlockIcon;
        private ImageIcon sBlockIcon;
        private ImageIcon tBlockIcon;
        private ImageIcon zBlockIcon;

        private boolean isHighScoreVisible = false;
		private boolean isTextDisplayed = false;

        private Thread titleLabelThread;
        private Thread blockGravityThread;

        private int currentBackground = 0;

        private boolean animateTitle = true;

        private final logic game;

		private JLabel high_score;

		private JLabel howtoplaygame;
		private int highestScore;
		private boolean isSoundPlaying = false;
		private boolean musicBlockSoundPlaying = false;
	    private Clip clip;

		private JLabel music;
        public frame(logic newGame) {
            game = newGame;
//            playSound("design/music-full.wav");
            highestScore = game.getHighScoreFromFile();
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
            initImageIcons();
            initQueuePanel();
            initHoldPanel();

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
        private void initFrameBackground() {
            try {
            	ImageIcon backgroundImage = new ImageIcon(ImageIO.read(new File("design/background.png")));
            	int frameWidth = jframe.getWidth();
                int frameHeight = jframe.getHeight();
                int imageWidth = backgroundImage.getIconWidth();
                int imageHeight = backgroundImage.getIconHeight();
                double widthRatio = (double) frameWidth / imageWidth;
                double heightRatio = (double) frameHeight / imageHeight;
                double scale = Math.max(widthRatio, heightRatio);
                int newWidth = (int) (imageWidth * scale);
                int newHeight = (int) (imageHeight * scale);
                Image scaledImage = backgroundImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);           
                ImageIcon scaledBackgroundImage = new ImageIcon(scaledImage);
                backgroundLabel = new JLabel(scaledBackgroundImage);
                backgroundLabel.setBounds(0, 0, frameWidth, frameHeight);
                jframe.getContentPane().add(backgroundLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       
        
        private void initStartPanel() {
            startPanel = new JPanel();
            startPanel.setBounds(10, 77, 382, 437);
            startPanel.setOpaque(false);
            startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

            titleLabel = new JLabel("Tetris");
            titleLabel.setFont(pixelFont);
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setFont(pixelFont.deriveFont(55f));
            startPanel.add(titleLabel);
            animateTitleLabel();

            JLabel startGameLabel = new JLabel("Start");
            startGameLabel.setFont(pixelFont);
            startGameLabel.setForeground(Color.BLACK);
            startGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            startGameLabel.setFont(pixelFont.deriveFont(25f));
            startPanel.add(startGameLabel);
            
            high_score = new JLabel("High score");
            high_score.setFont(pixelFont);
            high_score.setForeground(Color.BLACK);
            high_score.setAlignmentX(Component.CENTER_ALIGNMENT);
            high_score.setFont(pixelFont.deriveFont(25f));
            startPanel.add(high_score);
            
            howtoplaygame = new JLabel("How to play");
            howtoplaygame.setFont(pixelFont);
            howtoplaygame.setForeground(Color.BLACK);
            howtoplaygame.setAlignmentX(Component.CENTER_ALIGNMENT);
            howtoplaygame.setFont(pixelFont.deriveFont(25f));
            startPanel.add(howtoplaygame);

            music = new JLabel("Music on");
            music.setFont(pixelFont);
            music.setForeground(Color.BLACK);
            music.setAlignmentX(Component.CENTER_ALIGNMENT);
            music.setFont(pixelFont.deriveFont(25f));
            startPanel.add(music);
            
            JLabel switchThemeLabel = new JLabel("Switch Theme");
            switchThemeLabel.setFont(pixelFont);
            switchThemeLabel.setForeground(Color.BLACK);
            switchThemeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            switchThemeLabel.setFont(pixelFont.deriveFont(25f));
            startPanel.add(switchThemeLabel);

            JLabel exit = new JLabel("Exit");
            exit.setFont(pixelFont);
            exit.setForeground(Color.BLACK);
            exit.setAlignmentX(Component.CENTER_ALIGNMENT);
            exit.setFont(pixelFont.deriveFont(25f));
            startPanel.add(exit);

//      	  JLabel soundStatusLabel = new JLabel("Off");
//            soundStatusLabel.setFont(pixelFont.deriveFont(20f));
//            soundStatusLabel.setForeground(Color.BLACK);
//            soundStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//            startPanel.add(soundStatusLabel);
            jframe.getContentPane().add(startPanel);

            startGameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    animateTitle = false;
                    titleLabelThread.interrupt();
                    startPanel.setVisible(false);
                    gamePanel.setVisible(true);
                    game.startGame();
                    updateQueue();
                    updateCurrentBlock(true);
                    gravity();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    startGameLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    startGameLabel.setForeground(Color.BLACK);
                }
            });
            music.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	
                      if (isSoundPlaying) {
                          music.setText("Music on");
                      } else {
                          music.setText("Music off");
                      }
                	toggleSound();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                	music.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	music.setForeground(Color.BLACK);
                }
            });
            switchThemeLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    changeBackgroundImage();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    switchThemeLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    switchThemeLabel.setForeground(Color.BLACK);
                }
            });
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                	exit.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	exit.setForeground(Color.BLACK);
                }
            });
            
            howtoplaygame.addMouseListener(new MouseAdapter() {


				@Override
                public void mouseClicked(MouseEvent e) {
					 if (isHighScoreVisible) {
				            startPanel.remove(highLabel);
				            isHighScoreVisible = false;
				            startPanel.revalidate();
				            startPanel.repaint();
				        }
					if (isTextDisplayed) {
					    startPanel.remove(imageLabel);
					    isTextDisplayed = false;
					} else {

					    ImageIcon originalIcon = new ImageIcon("design/how.png");

					    Image originalImage = originalIcon.getImage();

					    Image scaledImage = originalImage.getScaledInstance(250, 200, Image.SCALE_SMOOTH);

					    ImageIcon scaledIcon = new ImageIcon(scaledImage);

					    imageLabel = new JLabel(scaledIcon);
					    startPanel.add(imageLabel);

					    isTextDisplayed = true;
					}
					startPanel.revalidate();
					startPanel.repaint();

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    howtoplaygame.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    howtoplaygame.setForeground(Color.BLACK);
                }
            });
            high_score.addMouseListener(new MouseAdapter() {

				@Override
                public void mouseClicked(MouseEvent e) {
                	 if (isTextDisplayed) {
                         startPanel.remove(imageLabel);
                         isTextDisplayed = false;
                         startPanel.revalidate();
                         startPanel.repaint();
                     }
                	 if (isHighScoreVisible) {
                         startPanel.remove(highLabel);

                         isHighScoreVisible = false;
                     } else {
                         highLabel = new JLabel("High Score: " + highestScore);
                         highLabel.setFont(pixelFont);
                         highLabel.setForeground(Color.BLACK);
                         highLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                         highLabel.setFont(pixelFont.deriveFont(25f));
                         Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
                         highLabel.setBorder(border);
                         startPanel.add(highLabel);
                         isHighScoreVisible = true;
                     }
                     startPanel.revalidate();
                     startPanel.repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                	high_score.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	high_score.setForeground(Color.BLACK);
                }
            });
        }

        private void playSound(String filePath) {
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                if (isSoundPlaying) {
                    stopSound();
                } else {
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    isSoundPlaying = true;
                }
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
        private void stopSound() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }                isSoundPlaying = false;

        }

        private void toggleSound() {
            if (isSoundPlaying) {
                stopSound();
            } else {
                playSound("design/music-full.wav");
            }
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
            scoreLabel.setForeground(Color.BLACK);
            scorePanel.add(scoreLabel);
            gamePanel.add(scorePanel);
        }
        private void initLevelPanel() {
            JPanel levelPanel = new JPanel();
            levelPanel.setBounds(272, 50, 120, 30);
            levelPanel.setOpaque(false);
            levelPanel.setBorder(new LineBorder(Color.BLACK, 2));
            levelLabel = new JLabel("Level: 0");
            levelLabel.setForeground(Color.BLACK);
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
            linesLabel.setForeground(Color.BLACK);
            linesPanel.add(linesLabel);
            gamePanel.add(linesPanel);
        }
        private void initImageIcons() {
            try {
                iBlockIcon = new ImageIcon(ImageIO.read(new File("design/IBlock.png")));
                jBlockIcon = new ImageIcon(ImageIO.read(new File("design/JBlock.png")));
                lBlockIcon = new ImageIcon(ImageIO.read(new File("design/LBlock.png")));
                oBlockIcon = new ImageIcon(ImageIO.read(new File("design/OBlock.png")));
                sBlockIcon = new ImageIcon(ImageIO.read(new File("design/SBlock.png")));
                tBlockIcon = new ImageIcon(ImageIO.read(new File("design/TBlock.png")));
                zBlockIcon = new ImageIcon(ImageIO.read(new File("design/ZBlock.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            updateQueue();
        }
        private void initHoldPanel() {
            JLabel holdLabel = new JLabel("Hold Block (H)");
            holdLabel.setForeground(Color.BLACK);
            holdLabel.setFont(pixelFont.deriveFont(15f));
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
            pauseCoverLabel.setFont(pixelFont.deriveFont(65f));
            pauseCoverLabel.setForeground(Color.WHITE);
            pauseCoverPanel.add(pauseCoverLabel);

            JPanel pausePanel = new JPanel();
            pausePanel.setForeground(Color.BLACK);
            pausePanel.setBounds(272, 430, 120, 30);
            pausePanel.setOpaque(false);
            pausePanel.setBorder(new LineBorder(Color.BLACK, 2));

            JLabel pauseLabel = new JLabel("Pause (P)");
            pauseLabel.setForeground(Color.BLACK);
            pauseLabel.setFont(pixelFont.deriveFont(19f));
            pausePanel.add(pauseLabel);
            pausePanel.setLayout(new BoxLayout(pausePanel, BoxLayout.Y_AXIS));

            pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            pauseLabel.setAlignmentY(Component.CENTER_ALIGNMENT);


            gamePanel.add(pausePanel);

            pausePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pauseGame();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    pauseLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    pauseLabel.setForeground(Color.BLACK);
                }
            });
        }
        private void initGameOverMessage() {
            gameOverPanel = new JPanel();
            gameOverPanel.setBounds(10, 110, 382, 200);
            gameOverPanel.setOpaque(false);
            gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));

            JLabel gameOverLabel = new JLabel("Game Over");
            gameOverLabel.setFont(pixelFont.deriveFont(55f));
            gameOverLabel.setForeground(Color.BLACK);
            gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(gameOverLabel);

            highScoreLabel = new JLabel("High Score: 0");
            highScoreLabel.setFont(pixelFont.deriveFont(25f));
            highScoreLabel.setForeground(Color.BLACK);
            highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(highScoreLabel);

            gameOverScoreLabel = new JLabel("Score: 0");
            gameOverScoreLabel.setForeground(Color.BLACK);
            gameOverScoreLabel.setFont(pixelFont.deriveFont(25f));
            gameOverScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(gameOverScoreLabel);

            JLabel playAgainLabel = new JLabel("Play Again?");
            playAgainLabel.setForeground(Color.BLACK);
            playAgainLabel.setFont(pixelFont.deriveFont(25f));
            playAgainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gameOverPanel.add(playAgainLabel);

            JLabel exit = new JLabel("Exit");
            exit.setFont(pixelFont);
            exit.setForeground(Color.BLACK);
            exit.setAlignmentX(Component.CENTER_ALIGNMENT);
            exit.setFont(pixelFont.deriveFont(25f));
            gameOverPanel.add(exit);
            
            playAgainLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    resetGame();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    playAgainLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    playAgainLabel.setForeground(Color.BLACK);
                }
                
            });

            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.exit(0);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                	exit.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	exit.setForeground(Color.BLACK);
                }
            });
            gameOverPanel.setVisible(false);
            jframe.getContentPane().add(gameOverPanel);
        }
        private void updateQueue() {
            Thread queueThread = new Thread(() -> {
                for (int i = 0; i < 3; i++) {
                    switch (game.getBlockQueue()[i]) {
                        case 0 -> {
                            queuePicLabels[i].setIcon(iBlockIcon);
                            queuePicLabels[i].setBounds(10, 22 + (i * 60), 100, 25);
                            queuePanel.add(queuePicLabels[i]);
                        }
                        case 1 -> {
                            queuePicLabels[i].setIcon(jBlockIcon);
                            queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                            queuePanel.add(queuePicLabels[i]);
                        }
                        case 2 -> {
                            queuePicLabels[i].setIcon(lBlockIcon);
                            queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                            queuePanel.add(queuePicLabels[i]);
                        }
                        case 3 -> {
                            queuePicLabels[i].setIcon(oBlockIcon);
                            queuePicLabels[i].setBounds(35, 10 + (i * 60), 50, 50);
                            queuePanel.add(queuePicLabels[i]);
                        }
                        case 4 -> {
                            queuePicLabels[i].setIcon(sBlockIcon);
                            queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                            queuePanel.add(queuePicLabels[i]);
                        }
                        case 5 -> {
                            queuePicLabels[i].setIcon(tBlockIcon);
                            queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                            queuePanel.add(queuePicLabels[i]);
                        }
                        case 6 -> {
                            queuePicLabels[i].setIcon(zBlockIcon);
                            queuePicLabels[i].setBounds(22, 10 + (i * 60), 75, 50);
                            queuePanel.add(queuePicLabels[i]);
                        }
                    }
                }
            });
            queueThread.start();
        }
        private void changeBackgroundImage() {
            try {
                if (currentBackground == 0) {
                    backgroundLabel.setIcon(new ImageIcon(ImageIO.read(new File("design/background-2.jpg"))));
                    currentBackground = 1;
                } else if (currentBackground == 1) {
                    backgroundLabel.setIcon(new ImageIcon(ImageIO.read(new File("design/background.png"))));
                    currentBackground = 0;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void updateHoldImage() {
            if (game.getCurrentHoldBlock() != -1) {
                ImageIcon icon = switch (game.getCurrentHoldBlock()) {
                    case 0 -> iBlockIcon;
                    case 1 -> jBlockIcon;
                    case 2 -> lBlockIcon;
                    case 3 -> oBlockIcon;
                    case 4 -> sBlockIcon;
                    case 5 -> tBlockIcon;
                    case 6 -> zBlockIcon;
                    default -> null;
                };
                holdImgLabel.setIcon(icon);
            } else {
                holdImgLabel.setIcon(null);
            }
        }
        private void updateScoreLabel() {
            scoreLabel.setText("Score: " + game.getScore());
        }
        private void updateLevelLabel() {
            levelLabel.setText("Level: " + game.getLevel());
        }
        private void updateLinesLabel() {
            linesLabel.setText("Lines: " + game.getTotalLines());
        }
        private void updateCurrentBlock(boolean isNewBlock) {
            if (!isNewBlock) {
                for (int i = 0; i < 4; i++) {
                    gameGrid[game.getPreviousBlockPos()[i][0]][game.getPreviousBlockPos()[i][1]].setBackground(Color.WHITE);
                    gameGrid[game.getPreviousBlockPos()[i][0]][game.getPreviousBlockPos()[i][1]].setBorder(null);
                    gameGrid[game.getPreviousBlockPos()[i][0]][game.getPreviousBlockPos()[i][1]].setOpaque(false);
                }
            }
            for (int i = 0; i < 4; i++) {
                gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setOpaque(true);
                gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBackground(game.getCurrentBlockColor());
                gameGrid[game.getCurrentBlockPos()[i][0]][game.getCurrentBlockPos()[i][1]].setBorder(new LineBorder(Color.BLACK));
            }
        }
        private final KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 37 ) {
                    game.moveSide(0);
                    updateCurrentBlock(false);
                } else if (e.getKeyCode() == 39 ) {
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
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 40 || e.getKeyCode() == 83) {
                    game.setFastFall(false);
                }
            }
        };
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
                        //Copy array
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
                        if (!game.checkIfGameOver()) {
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
            gameOverScoreLabel.setText("Score: " + game.getScore());
            highScoreLabel.setText("High Score: " + game.getHighScoreFromFile());
            gameOverPanel.setVisible(true);
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
//        public class SoundManager {
//            public static void playSound(String filePath) {
//                try {
//                    File soundFile = new File(filePath);
//                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
//                    Clip clip = AudioSystem.getClip();
//                    clip.open(audioIn);
//                    clip.start();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        private void SoundManager(String filePath) {
            try {
                File soundFile = new File(filePath);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void removeRows(ArrayList<Integer> rowsToRemove) {
        	int removedRowCount = 0;
            for (Integer i : rowsToRemove) {
                int green = 250;
                int blue = 185;
                for (int j = 0; j < 10; j++) {
                    gameGrid[j][i].setBackground(new Color(250, green, blue));
                    green -= 10;
                    blue -= 10;
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException ignored) {}
                }
            }
            for (Integer i: rowsToRemove) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < game.getSetBlocks().size(); k++) {
                        if (Arrays.equals(new int[]{j, i}, game.getSetBlocks().get(k))) {
                        	if (!musicBlockSoundPlaying) { // Kiểm tra nếu âm thanh chưa được phát
                                SoundManager("design/music-block.wav");
                                musicBlockSoundPlaying = true; // Đặt biến này thành true để chỉ ra rằng âm thanh đã được phát
                            }
                            game.removeFromSetBlocks(k);
                        }
                        gameGrid[j][i].setBackground(Color.WHITE);
                        gameGrid[j][i].setBorder(null);
                        gameGrid[j][i].setOpaque(false);
                    }
                }
                // Tăng biến đếm sau mỗi lần xoá dòng
                removedRowCount++;
                // Kiểm tra nếu đã xoá ít nhất 3 dòng thì phát âm thanh mới
                if (removedRowCount >= 2) {
                    SoundManager("design/3Lines.wav");
                    // Đặt lại biến đếm về 0 để đếm lại từ đầu
                    removedRowCount = 0;
                    if (musicBlockSoundPlaying) {
                        musicBlockSoundPlaying = false;
                        // Thêm đoạn code để tắt âm thanh "music-block.wav" ở đây
                    }
                }
            }
            Collections.reverse(rowsToRemove);
            for (Integer i: rowsToRemove) {
                Color[][] gridColors = new Color[10][i];
                for (int j = 0; j < i; j++) {
                    for (int k = 0; k < 10; k++) {
                        gridColors[k][j] = gameGrid[k][j].getBackground();
                        gameGrid[k][j].setBackground(Color.WHITE);
                        gameGrid[k][j].setBorder(null);
                        gameGrid[k][j].setOpaque(false);
                    }
                }
                for (int j = 0; j < i; j++) {
                    for (int k = 0; k < 10; k++) {
                        if (gridColors[k][j] != Color.WHITE) {
                            gameGrid[k][j + 1].setBackground(gridColors[k][j]);
                            gameGrid[k][j + 1].setBorder(new LineBorder(Color.BLACK));
                            gameGrid[k][j + 1].setOpaque(true);
                        }
                    }
                }
                game.clearSetBlocks();
                for (int j = 0; j < 20; j++) {
                    for (int k = 0; k < 10; k++) {
                        if (gameGrid[k][j].getBackground() != Color.WHITE) {
                            game.addToSetBlocks(new int[]{k, j});
                        }
                    }
                }
            }
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
