package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Rectangle;

/**
 * Abstract base class for all game entities that have a position, size,
 * speed, and visual representation on the canvas using a {@link Rectangle}.
 */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double speed;
    protected boolean alive;
    protected Rectangle shape;

    /**
     * Constructs a game object with the given position, size, speed, and color.
     *
     * @param x      the initial x-coordinate
     * @param y      the initial y-coordinate
     * @param width  the width
     * @param height the height
     * @param speed  the movement speed
     * @param color  the fill color
     */
    protected GameObject(double x, double y, int width, int height, double speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.alive = true;

        shape = new Rectangle(x, y, width, height);
        shape.setColor(color);
        shape.fill();
    }

    /**
     * Moves this game object by the given relative amounts and updates
     * the underlying shape via {@code translate()}.
     *
     * @param dx the amount to move in the x-direction
     * @param dy the amount to move in the y-direction
     */
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
        shape.translate(dx, dy);
    }

    /**
     * Gets the current x-coordinate from the underlying shape.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return shape.getX();
    }

    /**
     * Gets the current y-coordinate from the underlying shape.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return shape.getY();
    }

    /**
     * Gets the width of this game object.
     *
     * @return the width
     */
    public int getWidth() {
        return shape.getWidth();
    }

    /**
     * Gets the height of this game object.
     *
     * @return the height
     */
    public int getHeight() {
        return shape.getHeight();
    }

    /**
     * Checks whether this game object is still alive.
     *
     * @return true if alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Destroys this game object by marking it as dead and removing
     * its shape from the canvas.
     */
    public void destroy() {
        alive = false;
        shape.delete();
    }

    /**
     * Checks whether this game object collides with another using AABB.
     *
     * @param other the other game object
     * @return true if bounding boxes overlap
     */
    public boolean collidesWith(GameObject other) {
        return CollisionDetector.checkCollision(this, other);
    }

    /**
     * Called each frame to update this game object's state.
     */
    public abstract void update();
}
