package view;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import controller.logic;

public class ScoreManager {
    private static final String SCORE_FILE = "design/scores.txt";
    private static List<JLabel> scoreLabels = new ArrayList<>();

    public static void saveScore(String name, int score) {
        List<Score> scores = loadScores();
        scores.add(new Score(name, score));
        Collections.sort(scores, Collections.reverseOrder());
        saveScores(scores);
        displayScores(null);
    }

    public static List<Score> loadScores() {
        List<Score> scores = new ArrayList<>();
        try {
            File scoreFile = new File(SCORE_FILE);
            if (!scoreFile.exists()) {
                scoreFile.createNewFile();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        scores.add(new Score(name, score));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

    private static void saveScores(List<Score> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            for (Score score : scores) {
                writer.write(score.getName() + "|" + score.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Score implements Comparable<Score> {
        private final String name;
        private final int score;

        public Score(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Score other) {
            return Integer.compare(other.score, this.score);
        }
    }

    public static void updateScoreLabel(JLabel scoreLabel, logic game) {
        scoreLabel.setText("Score: " + game.getScore());
    }

    public static void updateLevelLabel(JLabel levelLabel, logic game) {
        levelLabel.setText("Level: " + game.getLevel());
    }

    public static void updateLinesLabel(JLabel linesLabel, logic game) {
        linesLabel.setText("Lines: " + game.getTotalLines());
    }

    public static void initScorePanel(frame frameInstance) {
        JPanel scorePanel = new JPanel();
        scorePanel.setBounds(272, 10, 120, 35);
        scorePanel.setOpaque(false);
        scorePanel.setBorder(new LineBorder(new Color(173, 216, 230), 2));
        frameInstance.scoreLabel = new JLabel("Score: 0");
        frameInstance.scoreLabel.setFont(frameInstance.getPixelFont().deriveFont(14f));
        frameInstance.scoreLabel.setForeground(new Color(173, 216, 230));
        scorePanel.add(frameInstance.scoreLabel);
        frameInstance.gamePanel.add(scorePanel);
    }

    public static void displayScores(JFrame frame) {
        for (JLabel label : scoreLabels) {
            if (frame != null) {
                frame.getContentPane().remove(label);
            }
        }
        scoreLabels.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            int lineCount = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    JLabel scoreLabel = new JLabel(lineCount + ". " + name + ": " + score);
                    scoreLabel.setBounds(10, 20 * lineCount, 200, 20);
                    if (frame != null) {
                        frame.getContentPane().add(scoreLabel);
                    }
                    scoreLabels.add(scoreLabel);
                    lineCount++;
                }
            }
            if (frame != null) {
                frame.revalidate();
                frame.repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}