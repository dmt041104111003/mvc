package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class RowMananger {
    public static void removeRows(JPanel[][] gameGrid, logic game, ArrayList<Integer> rowsToRemove) {
        int removedRowCount = 0;
        for (Integer i : rowsToRemove) {
            int green = 250, blue = 185;
            for (int j = 0; j < 10; j++) {
                gameGrid[j][i].setBackground(new Color(250, green, blue));
                green -= 10; blue -= 10;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException ignored) {}
            }
        }
        for (Integer i : rowsToRemove) {
            boolean musicBlockSoundPlaying = false;
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < game.getSetBlocks().size(); k++) {
                    if (Arrays.equals(new int[]{j, i}, game.getSetBlocks().get(k))) {
                        if (!musicBlockSoundPlaying) {
                            Sound.playBlockSound("design/music-block.wav");
                            musicBlockSoundPlaying = true;
                        }
                        game.removeFromSetBlocks(k);
                    }
                    gameGrid[j][i].setBackground(Color.WHITE);
                    gameGrid[j][i].setBorder(null);
                    gameGrid[j][i].setOpaque(false);
                }
            }
            removedRowCount++;
            if (removedRowCount >= 2) {
                Sound.playBlockSound("design/3Lines.wav");
                removedRowCount = 0;
            }
        }

        Collections.reverse(rowsToRemove);
        for (Integer i : rowsToRemove) {
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
}