package com.mygdx.game;

import java.io.Serializable;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int MAX_SCORES = 5;
    private long[] highScores;

    private long tentativeScore;

    public GameData() {
        highScores = new long[MAX_SCORES];
    }

    // sets up an empty high scores table
    public void init() {
        for (int i = 0; i < MAX_SCORES; i++) {
            highScores[i] = 0;
        }
    }

    public long[] getHighScores() {
        return highScores;
    }


    public long getTentativeScore() {
        return tentativeScore;
    }

    public void setTenativeScore(long i) {
        tentativeScore = i;
    }

    public boolean isHighScore(long score) {
        return score > highScores[MAX_SCORES - 1];
    }

    public void addHighScore(long newScore) {
        if (isHighScore(newScore)) {
            highScores[MAX_SCORES - 1] = newScore;
            sortHighScores();
        }
    }

    public void sortHighScores() {
        for (int i = 0; i < MAX_SCORES; i++) {
            long score = highScores[i];
            int j;
            for (j = i - 1;
                 j >= 0 && highScores[j] < score;
                 j--) {
                highScores[j + 1] = highScores[j];
            }
            highScores[j + 1] = score;
        }
    }

}

















