package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Ellipse;

/**
 * Represents a pickup item displayed as an {@link Ellipse} on the canvas.
 * When the player collects a pickup, its effect is applied based on the
 * {@link PickupType}.
 */
public class Pickup {

    private static final int PICKUP_SIZE = 20;

    private double x;
    private double y;
    private boolean alive;
    private PickupType pickupType;
    private int value;
    private Ellipse shape;

    /**
     * Constructs a pickup of the given type at the given position.
     *
     * @param x          the x-coordinate
     * @param y          the y-coordinate
     * @param pickupType the type of pickup
     */
    public Pickup(double x, double y, PickupType pickupType) {
        this.x = x;
        this.y = y;
        this.pickupType = pickupType;
        this.value = pickupType.getValue();
        this.alive = true;

        shape = new Ellipse(x, y, PICKUP_SIZE, PICKUP_SIZE);
        shape.setColor(pickupType.getColor());
        shape.fill();
    }

    /**
     * Applies the pickup effect to the given player.
     *
     * @param player the player to apply the effect to
     */
    public void apply(Player player) {
        switch (pickupType) {
            case HEALTH:
                player.heal(value);
                break;
            case SPEED:
                player.increaseSpeed(value);
                break;
            case DAMAGE:
                player.increaseDamage(value);
                break;
        }
    }

    /**
     * Destroys this pickup, removing it from the canvas.
     */
    public void destroy() {
        alive = false;
        shape.delete();
    }

    /**
     * Gets the x-coordinate of this pickup.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return shape.getX();
    }

    /**
     * Gets the y-coordinate of this pickup.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return shape.getY();
    }

    /**
     * Gets the width of this pickup.
     *
     * @return the width
     */
    public int getWidth() {
        return shape.getWidth();
    }

    /**
     * Gets the height of this pickup.
     *
     * @return the height
     */
    public int getHeight() {
        return shape.getHeight();
    }

    /**
     * Checks whether this pickup is still on the canvas.
     *
     * @return true if alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Gets the type of this pickup.
     *
     * @return the pickup type
     */
    public PickupType getPickupType() {
        return pickupType;
    }
}
