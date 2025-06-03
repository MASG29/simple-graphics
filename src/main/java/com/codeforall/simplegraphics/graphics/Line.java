package com.codeforall.simplegraphics.graphics;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Represents a straight line defined by two endpoints.
 * The line can be drawn, translated, resized, or deleted from a canvas.
 * It supports color customization and integrates with the {@link Canvas} for rendering.
 *
 * Implements:
 * <ul>
 *   <li>{@link Shape} – provides geometric and canvas-renderable properties</li>
 *   <li>{@link Colorable} – allows setting the drawing color</li>
 *   <li>{@link Movable} – supports translation and resizing via grow</li>
 * </ul>
 */
public class Line implements Shape, Colorable, Movable {

    private Color color = Color.BLACK;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    /**
     * Constructs a line with a given starting and ending location.
     *
     * @param startX the x-coordinate of the starting point
     * @param startY the y-coordinate of the starting point
     * @param endX the x-coordinate of the ending point
     * @param endY the y-coordinate of the ending point
     */
    public Line(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    /**
     * Gets the leftmost x-position of the line.
     *
     * @return the leftmost x-position
     */
    @Override
    public int getX() {
        return (int) Math.round(Math.min(startX, endX));
    }

    /**
     * Gets the topmost y-position of the line.
     *
     * @return the topmost y-position
     */
    @Override
    public int getY() {
        return (int) Math.round(Math.min(startY, endY));
    }

    /**
     * Gets the width of the bounding box.
     *
     * @return the width
     */
    @Override
    public int getWidth() {
        return (int) Math.round(Math.abs(endX - startX));
    }

    /**
     * Gets the height of the bounding box.
     *
     * @return the height
     */
    @Override
    public int getHeight() {
        return (int) Math.round(Math.abs(endY - startY));
    }

    /**
     * Moves this line by a given amount.
     *
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    @Override
    public void translate(double dx, double dy) {
        startX += dx;
        startY += dy;
        endX += dx;
        endY += dy;
        Canvas.getInstance().repaint();
    }

    /**
     * Resizes this line both horizontally and vertically.
     *
     * @param dw the amount by which to resize the width on each side
     * @param dh the amount by which to resize the height on each side
     */
    @Override
    public void grow(double dw, double dh) {

        if (startX <= endX) {
            startX -= dw;
            endX += dw;

        } else {
            startX += dw;
            endX -= dw;
        }

        if (startY <= endY) {
            startY -= dh;
            endY += dh;

        } else {
            startY += dh;
            endY -= dh;
        }

        Canvas.getInstance().repaint();
    }

    /**
     * Sets the color for drawing this line.
     *
     * @param newColor the new color
     */
    @Override
    public void setColor(Color newColor) {
        color = newColor;
        Canvas.getInstance().repaint();
    }

    /**
     * Shows this line on the canvas.
     */
    @Override
    public void draw() {
        Canvas.getInstance().show(this);
    }

    /**
     * Deletes this line from the canvas.
     */
    @Override
    public void delete() {
        Canvas.getInstance().hide(this);
    }

    /**
     * Returns a string representation of the line, including both endpoints.
     *
     * @return a string describing the line's coordinates
     */
    @Override
    public String toString() {
        return "Line[startX=" + startX + ",startY=" + startY + ",endX=" + endX + ",endY=" + endY + "]";
    }

    /**
     * Paints the line using the specified Graphics2D context.
     *
     * @param g2D the graphics context to paint with
     */
    @Override
    public void paintShape(Graphics2D g2D) {
        if (color != null) {
            g2D.setColor(new java.awt.Color((int) color.getRed(), (int) color.getGreen(), (int) color.getBlue()));
            Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);
            g2D.draw(line);
        }
    }
}
