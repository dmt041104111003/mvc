package view;

import javax.swing.JOptionPane;

import controller.logic;
import controller.score;
public class GameOverViewManager {
    private frame mainFrame;
    private logic game;
    public GameOverViewManager(frame mainFrame, logic game) {
        this.mainFrame = mainFrame;
        this.game = game;
    }
    public void showGameOverMessage() {
        mainFrame.getgamePanel().setVisible(false);
        mainFrame.getgameOverPanel().setVisible(true);
        mainFrame.getgameOverScoreLabel().setText("Score: " + game.getScore());
        mainFrame.gethighScoreLabel().setText("High Score: " + game.getHighScoreFromFile());
        String name = JOptionPane.showInputDialog(mainFrame.getjframe(), "Enter your name:", "Game Over", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.isEmpty()) {
        	score.saveScore(name, game.getScore());
    	    score.saveUserToDatabase(name, game.getScore());

        }
         
    }

    public void showWinGamePanel() {
        mainFrame.gameOverAnimation();
        mainFrame.getgamePanel().setVisible(false);
        mainFrame.getwinGamePanel().setVisible(true);
        mainFrame.getfinalScoreLabel1().setText("Final Score: " + game.getScore());
        mainFrame.getwinGamePanel().add(mainFrame.getfinalScoreLabel1());
        String name = JOptionPane.showInputDialog(mainFrame.getjframe(), "Enter your name:", "Game Won", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.isEmpty()) score.saveScore(name, game.getScore());
        JOptionPane.showMessageDialog(mainFrame.getjframe(), "Congratulations! You've reached the maximum score of " + mainFrame.MAX() + "!");
    }

    public void showSelectOverPanel() {
         mainFrame.gameOverAnimation();
    	 mainFrame.getgamePanel().setVisible(false);
    	 mainFrame.getselectOverPanel().setVisible(true);
         mainFrame.getfinalScoreLabel2().setText("Final Score: " + game.getScore());
         String name = JOptionPane.showInputDialog(mainFrame.getjframe(), "Enter your name:", "Select Over", JOptionPane.PLAIN_MESSAGE);
         if (name != null && !name.isEmpty()) 
        	 {score.saveScore(name, game.getScore(),game.getLevel());
     	    score.saveUserToDatabase(name, game.getScore(),game.getLevel());

        	 }
     }
}