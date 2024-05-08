package controller;

import java.util.concurrent.ThreadLocalRandom;

public class BlockQueue {
    private int[] blockQueue;

    public BlockQueue() {
        initBlockQueue();
    }

    public void initBlockQueue() {
        blockQueue = new int[3];
        for (int i = 0; i < 3; i++)
            blockQueue[i] = ThreadLocalRandom.current().nextInt(0, 7);
    }

    private void shuffleAndAddToQueue() {
        blockQueue[0] = blockQueue[1];
        blockQueue[1] = blockQueue[2];
        blockQueue[2] = ThreadLocalRandom.current().nextInt(0, 7);
    }

    public int getNextBlock() {
        int nextBlock = blockQueue[0];
        shuffleAndAddToQueue();
        return nextBlock;
    }

    public int[] getBlockQueue() {
        return blockQueue;
    }
}