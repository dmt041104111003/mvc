package controller;

import javax.swing.JLabel;
public class ScoreManager {
    public static void updateScoreLabel(JLabel scoreLabel, logic game) {scoreLabel.setText("Score: " + game.getScore());}
    public static void updateLevelLabel(JLabel levelLabel, logic game) {levelLabel.setText("Level: " + game.getLevel());}
    public static void updateLinesLabel(JLabel linesLabel, logic game) {linesLabel.setText("Lines: " + game.getTotalLines());}
}