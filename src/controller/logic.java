package controller;

import java.awt.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import model.block;

public class logic {

    private block currentBlock;
//    private int[] blockQueue;
    private final int[][] previousBlockPos;
    private int fallDelay = 500;
    private int currentHoldBlock = -1;
    private int score = 0;
    private int level = 0;
    private int clearedLines = 0;
    private int requiredLineClears = 10;
    private int totalLines = 0;
    private boolean heldThisTurn = false;
    private boolean fastFall = false;
    private boolean running = false;
    private final ArrayList<int[]> setBlocks;
	private BufferedReader bufferedReader;
	private BlockQueue blockQueue;
    public logic() {
    	blockQueue = new BlockQueue();
        setBlocks = new ArrayList<>();
        previousBlockPos = new int[4][2];
    }
    public void startGame() {
        running = true;
        updateFallDelay();
        nextBlock();
    }
    public void resetGame() {
        currentHoldBlock = -1;
        score = 0;
        level = 0;
        clearedLines = 0;
        requiredLineClears = 10;
        totalLines = 0;
        heldThisTurn = false;
        blockQueue.initBlockQueue();
        setBlocks.clear();
        startGame();
    }
    public void nextBlock() {
        currentBlock = new block(blockQueue.getNextBlock());
    }
    public void updatePreviousBlockPos() {
        for (int i = 0; i < 4; i++) 
            previousBlockPos[i] = Arrays.copyOf(currentBlock.getBlockLocation()[i], currentBlock.getBlockLocation()[i].length);
    }
    public boolean isTouchingBottomOrBlock() {
        for (int i = 0; i < 4; i++) {
            if (currentBlock.getBlockLocation()[i][1] == 19) return true;
             else 
                for (int[] setBlock : setBlocks) 
                    if (Arrays.equals(new int[]{currentBlock.getBlockLocation()[i][0], currentBlock.getBlockLocation()[i][1] + 1}, setBlock)) 
                        return true;

        }
        return false;
    }
    public void moveBlockDown() {currentBlock.moveBlockDown();}
    public void addCurrentToSetBlock() {
        for (int i = 0; i < 4; i++) 
            setBlocks.add(new int[]{currentBlock.getBlockLocation()[i][0], currentBlock.getBlockLocation()[i][1]});
    }
    public ArrayList<Integer> checkForFullRows() {
        ArrayList<Integer> rowsToRemove = new ArrayList<>();
        for (int i = 19; i > -1; i--) {
            int count = 0;
            for (int j = 0; j < 10; j++) 
                for (int[] setBlock : setBlocks) 
                    if (Arrays.equals(new int[]{j, i}, setBlock)) count++;
                    
            if (count == 10) rowsToRemove.add(i);
        }
        if (rowsToRemove.size() > 0) updateScoreAndLevel(rowsToRemove.size());
        return rowsToRemove;
    }
    public void updateScoreAndLevel(int rowToRemoveCount) {
        switch (rowToRemoveCount) {
            case 1 -> score += 100 * (level + 1);
            case 2 -> score += 300 * (level + 1);
            case 3 -> score += 500 * (level + 1);
            case 4 -> score += 800 * (level + 1);
        }
        totalLines += rowToRemoveCount;
        clearedLines += rowToRemoveCount;
        if (clearedLines >= requiredLineClears) {
        	clearedLines = clearedLines - requiredLineClears;
            increaseLevel();
        }
    }

    public void increaseLevel() {
        level++;
        if (level < 10) {
            fallDelay -= 100;
        } else if (level == 10) {
            fallDelay -= 50;
        } else {
            fallDelay -= 10;
        }
        requiredLineClears = (level + 1) * 10;
    }

    public void updateFallDelay() {
        if (level < 10) {
            fallDelay = 500 - (level * 50);
        } else if (level == 10) {
            fallDelay = 40;
        } else {
            fallDelay = 10;
        }
    }
    public boolean checkIfGameOver() {
        for (int i = 0; i < 4; i++) {
            if (currentBlock.getBlockLocation()[i][1] == 0) {
                running = false;
                return true;
            }
        }
        return false;
    }
    public void moveSide(int dir) {
        if (!isTouchingSideOrBlock(dir)) {
            updatePreviousBlockPos();
            if (dir == 0) currentBlock.moveBlockLeft();
            else if (dir == 1) currentBlock.moveBlockRight();
        }
    }
    public boolean isTouchingSideOrBlock(int dir) {
        for (int i = 0; i < 4; i++) {
            if (dir == 0) {
                if (currentBlock.getBlockLocation()[i][0] == 0) return true;
                else 
                    for (int[] setBlock : setBlocks) 
                        if (Arrays.equals(new int[]{currentBlock.getBlockLocation()[i][0] - 1, currentBlock.getBlockLocation()[i][1]}, setBlock))
                            return true;

            } else if (dir == 1) {
                if (currentBlock.getBlockLocation()[i][0] == 9) return true;
                 else 
                    for (int[] setBlock : setBlocks) 
                        if (Arrays.equals(new int[]{currentBlock.getBlockLocation()[i][0] + 1, currentBlock.getBlockLocation()[i][1]}, setBlock)) 
                            return true;
            }
        }
        return false;
    }
    public void rotateBlock() {
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
            updatePreviousBlockPos();
            currentBlock.setNewLocation(rotateLoc);
            currentBlock.setBlockRotation(newBlockRotation);
        }
    }
    public int getHighScoreFromFile() {
        int highScore = 0;
        try {
            File highScoreFile = new File("design/highscore.txt");
            FileReader fileReader = new FileReader(highScoreFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                highScore = Integer.parseInt(buffer);
            }
        } catch (IOException | NumberFormatException e) {e.printStackTrace();}
        return highScore;
    }
    
    public void writeHighScoreToFile(int highScore) {
        try {
            File highScoreFile = new File("design/highscore.txt");
            FileWriter fileWriter = new FileWriter(highScoreFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(highScore + "");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {e.printStackTrace();}
    }
    public void checkIfHighScore() {
        if (score > getHighScoreFromFile()) writeHighScoreToFile(score);
    }
    public boolean isHeldThisTurn() {return heldThisTurn;}
    public int[][] getCurrentBlockPos() {return currentBlock.getBlockLocation();}
    public Color getCurrentBlockColor() {return currentBlock.getBlockColor();}
    public int[][] getPreviousBlockPos() {return previousBlockPos;}
    public ArrayList<int[]> getSetBlocks() {return setBlocks;}
    public int getFallDelay() {return fallDelay;}
    public boolean isFastFall() {return fastFall;}
    public boolean isRunning() {return running;}
    public int[] getBlockQueue() {return blockQueue.getBlockQueue();}
    public int getScore() {return score;}
    public int getLevel() {return level;}
    public int getTotalLines() {return totalLines;}
    public int getCurrentHoldBlock() {return currentHoldBlock;}
    public int getCurrentBlockType() {return currentBlock.getBlockType();}
    public void setHeldThisTurn(boolean heldThisTurn) {this.heldThisTurn = heldThisTurn;}
    public void setRunning(boolean running) {this.running = running;}
    public void newCurrentBlock(int blockType) {currentBlock = new block(blockType);}
    public void setCurrentHoldBlock(int currentHoldBlock) {this.currentHoldBlock = currentHoldBlock;}
    public void setFastFall(boolean fastFall) {this.fastFall = fastFall;}
    public void addToSetBlocks(int[] blockToAdd) {setBlocks.add(blockToAdd);}
    public void removeFromSetBlocks(int index) {setBlocks.remove(index);}
    public void clearSetBlocks() {setBlocks.clear();}
    public void setLevel(int level) {this.level = level;}
}