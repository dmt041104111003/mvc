package model;


import java.awt.*;

public class block {

    private final int blockType;
    private int blockRotation;
    private int[][] blockLocation;
    private Color blockColor;

    public block(int newBlockType) {
        blockType = newBlockType;
        blockRotation = 0;
        setNewBlockLocationAndColor(newBlockType);
    }

    private void setNewBlockLocationAndColor(int newBlockType) {
        blockLocation = new int[4][2];
        switch (newBlockType) {
            case 0 -> {
                blockLocation[0] = new int[]{3, 0};
                blockLocation[1] = new int[]{4, 0};
                blockLocation[2] = new int[]{5, 0};
                blockLocation[3] = new int[]{6, 0};
                blockColor = new Color(146, 30, 70);
            }
            case 1 -> {
                blockLocation[0] = new int[]{3, 0};
                blockLocation[1] = new int[]{3, 1};
                blockLocation[2] = new int[]{4, 1};
                blockLocation[3] = new int[]{5, 1};
                blockColor = new Color(46, 105, 60);
            }
            case 2 -> {
                blockLocation[0] = new int[]{6, 0};
                blockLocation[1] = new int[]{4, 1};
                blockLocation[2] = new int[]{5, 1};
                blockLocation[3] = new int[]{6, 1};
                blockColor = new Color(36, 75, 107);
            }
            case 3 -> {
                blockLocation[0] = new int[]{4, 0};
                blockLocation[1] = new int[]{5, 0};
                blockLocation[2] = new int[]{4, 1};
                blockLocation[3] = new int[]{5, 1};
                blockColor = new Color(136, 104, 125);
            }
            case 4 -> {
                blockLocation[0] = new int[]{5, 0};
                blockLocation[1] = new int[]{6, 0};
                blockLocation[2] = new int[]{4, 1};
                blockLocation[3] = new int[]{5, 1};
                blockColor = new Color(82, 40, 38);
            }
            case 5 -> {
                blockLocation[0] = new int[]{4, 0};
                blockLocation[1] = new int[]{3, 1};
                blockLocation[2] = new int[]{4, 1};
                blockLocation[3] = new int[]{5, 1};
                blockColor = new Color(24, 89, 1);
            }
            case 6 -> {
                blockLocation[0] = new int[]{4, 0};
                blockLocation[1] = new int[]{5, 0};
                blockLocation[2] = new int[]{5, 1};
                blockLocation[3] = new int[]{6, 1};
                blockColor = new Color(181, 30, 33);
            }
        }
    }
    public void moveBlockDown() {
        for (int i = 0; i < 4; i++) {
            blockLocation[i][1]++;
        }
    }
    public void moveBlockLeft() {
        for (int i = 0; i < 4; i++) {
            blockLocation[i][0]--;
        }
    }

    public void moveBlockRight() {
        for (int i = 0; i < 4; i++) {
            blockLocation[i][0]++;
        }
    }
    public void setNewLocation(int[][] newLocation) {
        for (int i = 0; i < 4; i++) {
            blockLocation[i][0] = newLocation[i][0];
            blockLocation[i][1] = newLocation[i][1];
        }
    }

    public void setBlockRotation(int blockRotation) {
        this.blockRotation = blockRotation;
    }

    public int getBlockType() {
        return blockType;
    }

    public int getBlockRotation() {
        return blockRotation;
    }

    public int[][] getBlockLocation() {
        return blockLocation;
    }

    public Color getBlockColor() {
        return blockColor;
    }

}