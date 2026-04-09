package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Color;

/**
 * Defines the different types of enemies with their associated visual color,
 * movement speed, health points, and damage values.
 */
public enum EnemyType {

    NORMAL(Color.RED, 1.5, 30, 10),
    FAST(Color.ORANGE, 3.0, 15, 5),
    TANK(Color.DARK_GRAY, 0.8, 80, 20),
    BOSS(Color.MAGENTA, 1.0, 200, 30);

    private final Color color;
    private final double speed;
    private final int health;
    private final int damage;

    EnemyType(Color color, double speed, int health, int damage) {
        this.color = color;
        this.speed = speed;
        this.health = health;
        this.damage = damage;
    }

    /**
     * Gets the color associated with this enemy type.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the movement speed of this enemy type.
     *
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the starting health of this enemy type.
     *
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the damage this enemy type deals on contact.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }
}
