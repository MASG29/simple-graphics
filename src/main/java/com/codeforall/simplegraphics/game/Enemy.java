package com.codeforall.simplegraphics.game;

/**
 * Represents an enemy that chases the player. The enemy type determines
 * its color, speed, health, and damage dealt on contact.
 */
public class Enemy extends GameObject {

    private static final int ENEMY_SIZE = 25;

    private int health;
    private int damage;
    private EnemyType enemyType;
    private Player target;

    /**
     * Constructs an enemy of the given type at the given position that
     * will chase the specified player.
     *
     * @param x         the initial x-coordinate
     * @param y         the initial y-coordinate
     * @param enemyType the type of enemy
     * @param target    the player to chase
     */
    public Enemy(double x, double y, EnemyType enemyType, Player target) {
        super(x, y, ENEMY_SIZE, ENEMY_SIZE, enemyType.getSpeed(), enemyType.getColor());
        this.health = enemyType.getHealth();
        this.damage = enemyType.getDamage();
        this.enemyType = enemyType;
        this.target = target;
    }

    /**
     * Moves this enemy toward the player target each frame.
     */
    @Override
    public void update() {
        if (!alive || target == null || !target.isAlive()) {
            return;
        }

        double targetCenterX = target.getCenterX();
        double targetCenterY = target.getCenterY();
        double myCenterX = x + width / 2.0;
        double myCenterY = y + height / 2.0;

        double dx = targetCenterX - myCenterX;
        double dy = targetCenterY - myCenterY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            double moveX = (dx / distance) * speed;
            double moveY = (dy / distance) * speed;
            move(moveX, moveY);
        }
    }

    /**
     * Reduces this enemy's health. Destroys the enemy if health drops to zero.
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
     * Gets the damage this enemy deals on contact.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the type of this enemy.
     *
     * @return the enemy type
     */
    public EnemyType getEnemyType() {
        return enemyType;
    }
}
