package com.codeforall.simplegraphics.game;

import com.codeforall.simplegraphics.graphics.Color;

/**
 * Defines the different types of pickups with their associated visual color
 * and default effect value.
 */
public enum PickupType {

    HEALTH(Color.GREEN, 25),
    SPEED(Color.CYAN, 1),
    DAMAGE(Color.YELLOW, 5);

    private final Color color;
    private final int value;

    PickupType(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Gets the color associated with this pickup type.
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the default effect value of this pickup type.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
