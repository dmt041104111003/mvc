package controller;

import java.util.Arrays;
import java.util.List;

import model.block;

public class BlockOperations {
    public static boolean isTouchingBottomOrBlock(block currentBlock, List<int[]> setBlocks) {
        for (int i = 0; i < 4; i++) {
            if (currentBlock.getBlockLocation()[i][1] == 19) return true;
            else {
                for (int[] setBlock : setBlocks) {
                    if (Arrays.equals(new int[]{currentBlock.getBlockLocation()[i][0], currentBlock.getBlockLocation()[i][1] + 1}, setBlock)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isTouchingSideOrBlock(block currentBlock, List<int[]> setBlocks, int dir) {
        for (int i = 0; i < 4; i++) {
            if (dir == 0) {
                if (currentBlock.getBlockLocation()[i][0] == 0) return true;
                else {
                    for (int[] setBlock : setBlocks) {
                        if (Arrays.equals(new int[]{currentBlock.getBlockLocation()[i][0] - 1, currentBlock.getBlockLocation()[i][1]}, setBlock)) {
                            return true;
                        }
                    }
                }
            } else if (dir == 1) {
                if (currentBlock.getBlockLocation()[i][0] == 9) return true;
                else {
                    for (int[] setBlock : setBlocks) {
                        if (Arrays.equals(new int[]{currentBlock.getBlockLocation()[i][0] + 1, currentBlock.getBlockLocation()[i][1]}, setBlock)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void rotateBlock(block currentBlock, List<int[]> setBlocks) {
        int[][] rotateLoc = new int[4][2];
        int newBlockRotation = 0;
        switch (currentBlock.getBlockType()) {
        case 0 -> {
            if (currentBlock.getBlockRotation() == 0) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] + 2;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] - 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] - 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] + 1;
                newBlockRotation = 1;
            } else if (currentBlock.getBlockRotation() == 1) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] - 2;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] + 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] + 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] - 1;
            }
        }
        case 1 -> {
            if (currentBlock.getBlockRotation() == 0) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] + 2;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] - 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] + 1;
                newBlockRotation = 1;
            } else if (currentBlock.getBlockRotation() == 1) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] + 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] - 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] - 1;
                newBlockRotation = 2;
            } else if (currentBlock.getBlockRotation() == 2) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] - 2;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] + 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] - 1;
                newBlockRotation = 3;
            } else if (currentBlock.getBlockRotation() == 3) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] - 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] + 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] + 1;
            }
        }
        case 2 -> {
            if (currentBlock.getBlockRotation() == 0) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] + 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] - 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] - 1;
                newBlockRotation = 1;
            } else if (currentBlock.getBlockRotation() == 1) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] - 2;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] + 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] + 1;
                newBlockRotation = 2;
            } else if (currentBlock.getBlockRotation() == 2) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] - 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] - 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] + 1;
                newBlockRotation = 3;
            } else if (currentBlock.getBlockRotation() == 3) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] + 2;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] + 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] - 1;
            }
        }
        case 4 -> {
            if (currentBlock.getBlockRotation() == 0) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0];
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0] + 2;
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1] + 1;
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0];
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1];
                newBlockRotation = 1;
            } else if (currentBlock.getBlockRotation() == 1) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0];
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0] - 2;
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1] - 1;
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0];
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1];
            }
        }
        case 5 -> {
            if (currentBlock.getBlockRotation() == 0) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] + 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0];
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1];
                newBlockRotation = 1;
            } else if (currentBlock.getBlockRotation() == 1) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] - 1;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] + 1;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0];
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1];
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0];
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1];
                newBlockRotation = 2;
            } else if (currentBlock.getBlockRotation() == 2) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0];
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1];
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0];
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1];
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] - 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] - 1;
                newBlockRotation = 3;
            } else if (currentBlock.getBlockRotation() == 3) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] + 1;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] - 1;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1] - 1;
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0] + 1;
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1] + 1;
            }
        }
        case 6 -> {
            if (currentBlock.getBlockRotation() == 0) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] + 1;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] + 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] + 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1];
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0];
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1];
                newBlockRotation = 1;
            } else if (currentBlock.getBlockRotation() == 1) {
                rotateLoc[0][0] = currentBlock.getBlockLocation()[0][0] - 1;
                rotateLoc[0][1] = currentBlock.getBlockLocation()[0][1] - 2;
                rotateLoc[1][0] = currentBlock.getBlockLocation()[1][0] - 1;
                rotateLoc[1][1] = currentBlock.getBlockLocation()[1][1];
                rotateLoc[2][0] = currentBlock.getBlockLocation()[2][0];
                rotateLoc[2][1] = currentBlock.getBlockLocation()[2][1];
                rotateLoc[3][0] = currentBlock.getBlockLocation()[3][0];
                rotateLoc[3][1] = currentBlock.getBlockLocation()[3][1];
            }
        }
        }
        
        boolean flag = false;
        for (int[] setBlock : setBlocks) {
            for (int j = 0; j < 4; j++) {
                if (Arrays.equals(new int[]{rotateLoc[j][0], rotateLoc[j][1]}, setBlock)) {
                    flag = true;
                    break;
                }
            }
        }
        for (int j = 0; j < 4; j++) {
            if (rotateLoc[j][0] < 0 || rotateLoc[j][0] > 9) flag = true;
            else if (rotateLoc[j][1] < 0 || rotateLoc[j][1] > 19) flag = true;
        }
        if (currentBlock.getBlockType() == 3) flag = true;
        if (!flag) {
            currentBlock.setNewLocation(rotateLoc);
            currentBlock.setBlockRotation(newBlockRotation);
        }
    }
    public static void updatePreviousBlockPos(block currentBlock, int[][] previousBlockPos) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(currentBlock.getBlockLocation()[i], 0, previousBlockPos[i], 0, 2);
        }
    }
    
}