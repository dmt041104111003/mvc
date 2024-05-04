package controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connect.connectMySQL;

public class score {
    private static final String SCORE_FILE = "design/scores.txt";
    private static final String SCORE_FILE_ = "design/select.txt";
    private static List<JLabel> scoreLabels = new ArrayList<>();
    private static List<Score> scores = new ArrayList<>();
    private static boolean isScoresLoaded = false;

    public static void saveScore(String name, int score) {
        if (!isScoresLoaded) {
            loadScores();
            isScoresLoaded = true;
        }
        scores.add(new Score(name, score));
        saveScores();
        displayScores(null);
    }

    public static void saveScore(String name, int score, int level) {
        if (!isScoresLoaded) {
            loadScores();
            isScoresLoaded = true;
        }
        scores.add(new Score(name, score, level));
        saveScores();
        displayScores(null);
    }

    public static void loadScores() {
        Set<String> scoreSets = new HashSet<>();
        loadFromFile(SCORE_FILE, scoreSets);
        loadFromFile(SCORE_FILE_, scoreSets);
    }
    public static void reloadScores() {
        Set<String> scoreSets = new HashSet<>();
        for (Score score : scores) 
            scoreSets.add(score.getName() + "|" + score.getScore() + "|" + score.getLevel());
        loadFromFile(SCORE_FILE, scoreSets);
        loadFromFile(SCORE_FILE_, scoreSets);
    }
    private static void loadFromFile(String fileName, Set<String> scoreSets) {
        try {
            File scoreFile = new File(fileName);
            if (!scoreFile.exists()) scoreFile.createNewFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) {
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        String scoreKey = name + "|" + score;
                        if (!scoreSets.contains(scoreKey)) {
                            scoreSets.add(scoreKey);
                            scores.add(new Score(name, score));
                        }
                    } else if (parts.length == 3) {
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        int level = Integer.parseInt(parts[2]);
                        String scoreKey = name + "|" + score + "|" + level;
                        if (!scoreSets.contains(scoreKey)) {
                            scoreSets.add(scoreKey);
                            scores.add(new Score(name, score, level));
                        }
                    }
                }
            }
        } catch (IOException e) {e.printStackTrace();}
    }
    private static void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, false))) {
            for (Score score : scores) 
                if (score.getLevel() == 0) {
                    writer.write(score.getName() + "|" + score.getScore());
                    writer.newLine();
                }
        } catch (IOException e) {e.printStackTrace();}
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE_, false))) {
            for (Score score : scores) 
                if (score.getLevel() != 0) {
                    writer.write(score.getName() + "|" + score.getScore() + "|" + score.getLevel());
                    writer.newLine();
                }
        } catch (IOException e) {e.printStackTrace();}
    }

    static class Score implements Comparable<Score> {
        private final String name;
        private final int score;
        private final int level;
        public Score(String name, int score) {
            this.name = name;
            this.score = score;
            this.level = 0;
        }
        public Score(String name, int score, int level) {
            this.name = name;
            this.score = score;
            this.level = level;
        }
        public String getName() {return name;}
        public int getScore() {return score;}
        public int getLevel() {return level;}
        @Override
        public int compareTo(Score other) {return Integer.compare(other.score, this.score);}
    }

    public static void displayScores(JFrame frame) {
        for (JLabel label : scoreLabels) 
            if (frame != null) frame.getContentPane().remove(label);
        scoreLabels.clear();
        loadScores(); 
        int lineCount = 1;
        for (Score score : scores) {
            JLabel scoreLabel;
            if (score.getLevel() == 0) {
                scoreLabel = new JLabel(lineCount + ". " + score.getName() + ": " + score.getScore());
                scoreLabel.setBounds(10, 20 * lineCount, 200, 20);
            } else {
                scoreLabel = new JLabel(lineCount + ". " + score.getName() + ": " + score.getScore() + " (Level: " + score.getLevel() + ")");
                scoreLabel.setBounds(10, 20 * lineCount, 300, 20);
            }
            if (frame != null) frame.getContentPane().add(scoreLabel);
            scoreLabels.add(scoreLabel);
            lineCount++;
        }
        if (frame != null) {
            frame.revalidate();
            frame.repaint();
        }
    }
    
    public static void saveUserToDatabase(String name, int score) {
        try {
            Connection conn = connectMySQL.getConnection();
            if (conn != null) {
                String query = "INSERT INTO user (name_user, score_user) VALUES (?, ?)";
                PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setInt(2, score);

                statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    System.out.println("Inserted user ID: " + userId);
                    System.out.println("Name: " + name + ", Score: " + score);
                }

                statement.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}