package controller;

public class GameRules {
	public static int[] updateScoreAndLevel(int score, int level, int clearedLines, int rowsToRemoveCount, int totalLines) {
	    int newScore = score;
	    int newLevel = level;
	    int newClearedLines = clearedLines;
	    int newTotalLines = totalLines;

	    switch (rowsToRemoveCount) {
	        case 1 -> newScore += 100 * (level + 1);
	        case 2 -> newScore += 300 * (level + 1);
	        case 3 -> newScore += 500 * (level + 1);
	        case 4 -> newScore += 800 * (level + 1);
	    }

	    newTotalLines += rowsToRemoveCount;
	    newClearedLines += rowsToRemoveCount;
	    if (newClearedLines >= (newLevel + 1) * 10) {
	        newClearedLines = newClearedLines - (newLevel + 1) * 10;
	        newLevel++;
	    }

	    return new int[]{newScore, newLevel, newClearedLines, newTotalLines};
	}

    public static int calculateFallDelay(int level) {
        if (level < 10) {
            return 500 - (level * 50);
        } else if (level == 10) {
            return 40;
        } else {
            return 10;
        }
    }
}