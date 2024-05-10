package controller;

import java.io.*;

public class FileOperations {
    public static int getHighScoreFromFile() {
        int highScore = 0;
        try {
            File highScoreFile = new File("design/highscore.txt");
            FileReader fileReader = new FileReader(highScoreFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                highScore = Integer.parseInt(buffer);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return highScore;
    }

    public static void writeHighScoreToFile(int highScore) {
        try {
            File highScoreFile = new File("design/highscore.txt");
            FileWriter fileWriter = new FileWriter(highScoreFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(highScore + "");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}