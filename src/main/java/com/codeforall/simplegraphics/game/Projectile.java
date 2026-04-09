package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Color;

/**
 * Represents a projectile fired by the player that travels in a fixed direction
 * and deals damage to enemies on contact. The projectile has a limited lifetime
 * and is automatically destroyed when it expires or goes off-screen.
 */
public class Projectile extends GameObject {

    private static final int PROJECTILE_SIZE = 6;
    private static final double PROJECTILE_SPEED = 7.0;
    private static final int DEFAULT_LIFETIME = 60;

    private double dx;
    private double dy;
    private int damage;
    private int lifetime;
    private int canvasWidth;
    private int canvasHeight;

    /**
     * Constructs a projectile at the given position moving in the specified direction.
     *
     * @param x            the initial x-coordinate
     * @param y            the initial y-coordinate
     * @param dx           the normalized x-direction
     * @param dy           the normalized y-direction
     * @param damage       the damage dealt on hit
     * @param canvasWidth  the canvas width for bounds checking
     * @param canvasHeight the canvas height for bounds checking
     */
    public Projectile(double x, double y, double dx, double dy, int damage,
                      int canvasWidth, int canvasHeight) {
        super(x, y, PROJECTILE_SIZE, PROJECTILE_SIZE, PROJECTILE_SPEED, Color.YELLOW);
        this.dx = dx;
        this.dy = dy;
        this.damage = damage;
        this.lifetime = DEFAULT_LIFETIME;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    /**
     * Moves the projectile in its direction and decrements its lifetime.
     * Destroys the projectile if the lifetime expires or it goes off-screen.
     */
    @Override
    public void update() {
        if (!alive) {
            return;
        }

        move(dx * speed, dy * speed);
        lifetime--;

        if (lifetime <= 0 || x < -PROJECTILE_SIZE || x > canvasWidth
                || y < -PROJECTILE_SIZE || y > canvasHeight) {
            destroy();
        }
    }

    /**
     * Gets the damage this projectile deals.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }
}
