package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Color;

/**
 * Represents the player character in the game. The player moves using
 * directional flags set by keyboard input and can take damage, heal,
 * and auto-attack by spawning projectiles.
 */
public class Player extends GameObject {

    private static final int PLAYER_SIZE = 30;
    private static final int DEFAULT_MAX_HEALTH = 100;
    private static final int DEFAULT_ATTACK_COOLDOWN = 15;
    private static final double DEFAULT_SPEED = 4.0;
    private static final int DEFAULT_DAMAGE = 10;

    private int health;
    private int maxHealth;
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingLeft;
    private boolean movingRight;
    private int attackTimer;
    private int attackCooldown;
    private int damage;
    private int canvasWidth;
    private int canvasHeight;

    /**
     * Constructs a player at the given position within the given canvas bounds.
     *
     * @param x            the initial x-coordinate
     * @param y            the initial y-coordinate
     * @param canvasWidth  the width of the game canvas
     * @param canvasHeight the height of the game canvas
     */
    public Player(double x, double y, int canvasWidth, int canvasHeight) {
        super(x, y, PLAYER_SIZE, PLAYER_SIZE, DEFAULT_SPEED, Color.BLUE);
        this.health = DEFAULT_MAX_HEALTH;
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.attackTimer = 0;
        this.attackCooldown = DEFAULT_ATTACK_COOLDOWN;
        this.damage = DEFAULT_DAMAGE;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    /**
     * Updates the player position based on the current movement flags,
     * clamping to canvas bounds, and decrements the attack timer.
     */
    @Override
    public void update() {
        double dx = 0;
        double dy = 0;

        if (movingUp) {
            dy -= speed;
        }
        if (movingDown) {
            dy += speed;
        }
        if (movingLeft) {
            dx -= speed;
        }
        if (movingRight) {
            dx += speed;
        }

        // Normalize diagonal movement
        if (dx != 0 && dy != 0) {
            double factor = speed / Math.sqrt(dx * dx + dy * dy);
            dx *= factor;
            dy *= factor;
        }

        // Clamp to canvas bounds
        double newX = x + dx;
        double newY = y + dy;

        if (newX < 0) {
            dx = -x;
        } else if (newX + width > canvasWidth) {
            dx = canvasWidth - width - x;
        }

        if (newY < 0) {
            dy = -y;
        } else if (newY + height > canvasHeight) {
            dy = canvasHeight - height - y;
        }

        if (dx != 0 || dy != 0) {
            move(dx, dy);
        }

        if (attackTimer > 0) {
            attackTimer--;
        }
    }

    /**
     * Reduces the player's health by the given amount.
     * If health drops to zero or below, the player is destroyed.
     *
     * @param amount the damage to take
     */
    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            destroy();
        }
    }

    /**
     * Heals the player by the given amount, not exceeding max health.
     *
     * @param amount the amount to heal
     */
    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    /**
     * Sets whether the player is moving in a given direction.
     *
     * @param direction the direction ("up", "down", "left", "right")
     * @param moving    true to start moving, false to stop
     */
    public void setMoving(String direction, boolean moving) {
        switch (direction) {
            case "up":
                movingUp = moving;
                break;
            case "down":
                movingDown = moving;
                break;
            case "left":
                movingLeft = moving;
                break;
            case "right":
                movingRight = moving;
                break;
        }
    }

    /**
     * Checks if the player can fire and resets the attack cooldown timer.
     *
     * @return true if the attack was fired
     */
    public boolean canAttack() {
        if (attackTimer <= 0) {
            attackTimer = attackCooldown;
            return true;
        }
        return false;
    }

    /**
     * Increases the player's speed by the given amount.
     *
     * @param amount the speed increase
     */
    public void increaseSpeed(int amount) {
        speed += amount;
    }

    /**
     * Increases the player's attack damage by the given amount.
     *
     * @param amount the damage increase
     */
    public void increaseDamage(int amount) {
        damage += amount;
    }

    /**
     * Gets the current health.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the maximum health.
     *
     * @return the max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the current attack damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the center x-coordinate of the player.
     *
     * @return the center x
     */
    public double getCenterX() {
        return x + width / 2.0;
    }

    /**
     * Gets the center y-coordinate of the player.
     *
     * @return the center y
     */
    public double getCenterY() {
        return y + height / 2.0;
    }
}
