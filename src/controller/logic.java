package controller;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.block;

public class logic {
    private block currentBlock;
    private final List<int[]> setBlocks;
    private int fallDelay;
    private int currentHoldBlock;
    private int score;
    private int level;
    private int clearedLines;
    private int totalLines;
    private boolean heldThisTurn;
    private boolean fastFall;
    private boolean running;
    private final BlockQueue blockQueue;
	private int[][] previousBlockPos;
	private int requiredLineClears;

	public logic() {
	    blockQueue = new BlockQueue();
	    setBlocks = new ArrayList<>();
	    previousBlockPos = new int[4][2];
	    for (int i = 0; i < 4; i++) {
	        previousBlockPos[i] = new int[2];
	    }
	}

    public void startGame() {
        running = true;
        fallDelay = GameRules.calculateFallDelay(level);
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

    public boolean isTouchingBottomOrBlock() {
        return BlockOperations.isTouchingBottomOrBlock(currentBlock, setBlocks);
    }

    public void moveBlockDown() {
        currentBlock.moveBlockDown();
    }

    public void addCurrentToSetBlock() {
        for (int i = 0; i < 4; i++) {
            setBlocks.add(new int[]{currentBlock.getBlockLocation()[i][0], currentBlock.getBlockLocation()[i][1]});
        }
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

        if (rowsToRemove.size() > 0) {
            int[] updatedValues = GameRules.updateScoreAndLevel(score, level, clearedLines, rowsToRemove.size(),totalLines);
            score = updatedValues[0];
            int newLevel = updatedValues[1]; 
            clearedLines = updatedValues[2];
            totalLines = updatedValues[3];
            
            if (newLevel != level) {
                level = newLevel;
                fallDelay = GameRules.calculateFallDelay(level);
            }
        }

        return rowsToRemove;
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
        if (!BlockOperations.isTouchingSideOrBlock(currentBlock, setBlocks, dir)) {
            BlockOperations.updatePreviousBlockPos(currentBlock, previousBlockPos);
            if (dir == 0) {
                currentBlock.moveBlockLeft();
            } else if (dir == 1) {
                currentBlock.moveBlockRight();
            }
        }
    }
    public boolean isGameOver() {
        for (int i = 0; i < 4; i++) {
            if (currentBlock.getBlockLocation()[i][1] == 0) {
                return true;
            }
        }
        return false;
    }
    public void rotateBlock() {
        BlockOperations.updatePreviousBlockPos(currentBlock, previousBlockPos);
        BlockOperations.rotateBlock(currentBlock, setBlocks);
    }

    public int getHighScoreFromFile() {
        return FileOperations.getHighScoreFromFile();
    }
    public int[][] getPreviousBlockPos() {
        return previousBlockPos;
    }
    public void writeHighScoreToFile(int highScore) {
        FileOperations.writeHighScoreToFile(highScore);
    }

    public void checkIfHighScore() {
        if (score > getHighScoreFromFile()) {
            writeHighScoreToFile(score);
        }
    }

    public boolean isHeldThisTurn() {
        return heldThisTurn;
    }

    public int[][] getCurrentBlockPos() {
        return currentBlock.getBlockLocation();
    }

    public Color getCurrentBlockColor() {
        return currentBlock.getBlockColor();
    }

    public List<int[]> getSetBlocks() {
        return setBlocks;
    }

    public int getFallDelay() {
        return fallDelay;
    }

    public boolean isFastFall() {
        return fastFall;
    }

    public boolean isRunning() {
        return running;
    }

    public int[] getBlockQueue() {
        return blockQueue.getBlockQueue();
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getCurrentHoldBlock() {
        return currentHoldBlock;
    }

    public int getCurrentBlockType() {
        return currentBlock.getBlockType();
    }

    public void setHeldThisTurn(boolean heldThisTurn) {
        this.heldThisTurn = heldThisTurn;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void newCurrentBlock(int blockType) {
        currentBlock = new block(blockType);
    }

    public void setCurrentHoldBlock(int currentHoldBlock) {
        this.currentHoldBlock = currentHoldBlock;
    }

    public void setFastFall(boolean fastFall) {
        this.fastFall = fastFall;
    }

    public void addToSetBlocks(int[] blockToAdd) {
        setBlocks.add(blockToAdd);
    }

    public void removeFromSetBlocks(int index) {
        setBlocks.remove(index);
    }

    public void clearSetBlocks() {
        setBlocks.clear();
    }

    public void setLevel(int level) {
        this.level = level;
    }
    public void updatePreviousBlockPos() {
        BlockOperations.updatePreviousBlockPos(currentBlock, previousBlockPos);
    }
    public boolean isOccupied(int x, int y) {
        for (int[] block : setBlocks) {
            if (block[0] == x && block[1] == y) {
                return true;
            }
        }
        return false;
    }
    public boolean canMove(int[][] newPos) {
        for (int[] pos : newPos) {
            if (pos[0] < 0 || pos[0] >= 10 || pos[1] >= 20 || isOccupied(pos[0], pos[1])) {
                return false;
            }
        }
        return true;
    }
    private int[][] currentBlockPos = new int[4][2];
	private int currentBlockType;
	private int[] blockTypes;

    public void setCurrentBlockPos(int[][] newPos) {
        for (int i = 0; i < 4; i++) {
            currentBlockPos[i][0] = newPos[i][0];
            currentBlockPos[i][1] = newPos[i][1];
        }
    }
    public void rotateBlockP(int[][] newPos) {
        int[][] rotatedPos = rotateBlockPos(getCurrentBlockPos());
        if (canMove(rotatedPos)) {
            setCurrentBlockPos(rotatedPos);
        } 
    }

    private int[][] rotateBlockPos(int[][] pos) {
        int[][] newPos = new int[4][2];
        int offsetX = pos[0][0];
        int offsetY = pos[0][1];
        
        for (int i = 0; i < 4; i++) {
            int x = pos[i][0] - offsetX;
            int y = pos[i][1] - offsetY;
            newPos[i][0] = offsetX - y;
            newPos[i][1] = offsetY + x;
        }
        
        return newPos;
    }
    public int getBlockTypeAt(int x, int y) {
        for (int[] block : currentBlockPos) {
            if (block[0] == x && block[1] == y) {
                return currentBlockType;
            }
        }
        
        for (int[] block : setBlocks) {
            if (block[0] == x && block[1] == y) {
                return blockTypes[block[2]];
            }
        }
        
        return -1; 
    }

    public block getCurrentBlock() {
        return currentBlock;
    }
    
}