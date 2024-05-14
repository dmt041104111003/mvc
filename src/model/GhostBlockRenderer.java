package model;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import controller.logic;

public class GhostBlockRenderer {
    private logic game;
    private int[][] previousGhostBlockPos;
    private int[][] ghostBlockPos;

    public GhostBlockRenderer(logic game) {
        this.game = game;
    }

    public void drawGhostFrame(Graphics2D g2d, JPanel[][] gameGrid) {
        int[][] currentBlockPos = game.getCurrentBlockPos();
        ghostBlockPos = new int[4][2];
        for (int i = 0; i < 4; i++) {
            ghostBlockPos[i][0] = currentBlockPos[i][0];
            ghostBlockPos[i][1] = currentBlockPos[i][1];
        }

        boolean canMove = true;
        while (canMove) {
            canMove = false;
            for (int i = 0; i < 4; i++) {
                if (ghostBlockPos[i][1] < 19 && !game.isOccupied(ghostBlockPos[i][0], ghostBlockPos[i][1] + 1)) {
                    canMove = true;
                    break;
                }
            }
            if (canMove) {
                for (int i = 0; i < 4; i++) {
                    ghostBlockPos[i][1]++;
                }
                if (!game.canMove(ghostBlockPos)) {
                    canMove = false;
                    for (int i = 0; i < 4; i++) {
                        ghostBlockPos[i][1]--;
                    }
                    game.rotateBlockP(ghostBlockPos);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            int x = ghostBlockPos[i][0] * 25;
            int y = (ghostBlockPos[i][1]) * 25;
            g2d.setColor(new Color(173, 216, 230, 100));
            g2d.fillRect(x, y, 25, 25);
        }
    }
}