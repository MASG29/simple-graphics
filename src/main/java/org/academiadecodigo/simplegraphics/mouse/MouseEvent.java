package org.academiadecodigo.simplegraphics.mouse;

/**
 * Mouse event containing x and y coordinates and the mouse event type
 */
public class MouseEvent {

    private double x;
    private double y;
    private MouseEventType eventType;

    public MouseEvent(double x, double y, MouseEventType mouseEventType) {
        this.x = x;
        this.y = y;
        this.eventType = mouseEventType;
    }

    /**
     * Gets the X coordinate where the mouse clicked
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the Y coordinate where the mouse cliecked
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the mouse event.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the mouse event.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets the type of this mouse event.
     *
     * @return the {@link MouseEventType} for this event
     */
    public MouseEventType getEventType() {
        return eventType;
    }

    /**
     * Returns a string representation of the mouse event, including coordinates and event type.
     *
     * @return a string describing the event
     */
    @Override
    public String toString() {
        return "MouseEvent{x=" + x + ", y=" + y + ", eventType=" + eventType + '}';
    }
}
