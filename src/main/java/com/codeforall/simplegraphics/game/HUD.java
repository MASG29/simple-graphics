package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.graphics.Text;

/**
 * Displays the heads-up display including health bar, score, and wave number.
 * Uses {@link Text} for labels and {@link Rectangle} for the health bar.
 */
public class HUD {

    private static final int BAR_X = 10;
    private static final int BAR_Y = 60;
    private static final int BAR_WIDTH = 200;
    private static final int BAR_HEIGHT = 15;

    private Text healthText;
    private Text scoreText;
    private Text waveText;
    private Rectangle healthBar;
    private Rectangle healthBarBg;

    /**
     * Initializes and draws all HUD elements on the canvas.
     *
     * @param health    the initial health value
     * @param maxHealth the maximum health value
     */
    public void init(int health, int maxHealth) {
        healthText = new Text(BAR_X, 10, "HP: " + health + "/" + maxHealth);
        healthText.draw();

        scoreText = new Text(BAR_X, 30, "Score: 0");
        scoreText.draw();

        waveText = new Text(BAR_X, 50, "Wave: 1");
        waveText.draw();

        healthBarBg = new Rectangle(BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT);
        healthBarBg.setColor(Color.LIGHT_GRAY);
        healthBarBg.fill();

        healthBar = new Rectangle(BAR_X, BAR_Y, BAR_WIDTH, BAR_HEIGHT);
        healthBar.setColor(Color.GREEN);
        healthBar.fill();
    }

    /**
     * Updates the health bar width and text to reflect the current health.
     * Since {@link Rectangle} has no setWidth, the bar is deleted and recreated.
     *
     * @param current   the current health
     * @param maxHealth the maximum health
     */
    public void updateHealth(int current, int maxHealth) {
        healthText.setText("HP: " + current + "/" + maxHealth);

        healthBar.delete();
        int barWidth = (int) ((double) current / maxHealth * BAR_WIDTH);
        if (barWidth < 0) {
            barWidth = 0;
        }
        healthBar = new Rectangle(BAR_X, BAR_Y, barWidth, BAR_HEIGHT);

        if (current > maxHealth / 2) {
            healthBar.setColor(Color.GREEN);
        } else if (current > maxHealth / 4) {
            healthBar.setColor(Color.YELLOW);
        } else {
            healthBar.setColor(Color.RED);
        }
        healthBar.fill();
    }

    /**
     * Updates the score display.
     *
     * @param score the current score
     */
    public void updateScore(int score) {
        scoreText.setText("Score: " + score);
    }

    /**
     * Updates the wave display.
     *
     * @param wave the current wave number
     */
    public void updateWave(int wave) {
        waveText.setText("Wave: " + wave);
    }

    /**
     * Displays a game over message in the center of the canvas.
     *
     * @param canvasWidth  the canvas width
     * @param canvasHeight the canvas height
     * @param finalScore   the final score
     */
    public void showGameOver(int canvasWidth, int canvasHeight, int finalScore) {
        Text gameOverText = new Text(canvasWidth / 2.0 - 60, canvasHeight / 2.0 - 20, "GAME OVER");
        gameOverText.setColor(Color.RED);
        gameOverText.draw();

        Text finalScoreText = new Text(canvasWidth / 2.0 - 50, canvasHeight / 2.0 + 10, "Score: " + finalScore);
        finalScoreText.draw();
    }
}
