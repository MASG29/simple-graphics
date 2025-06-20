package com.codeforall.simplegraphics.graphics;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents an ellipse shape that can be drawn, filled, moved, and resized.
 * The ellipse is defined by its bounding box, which determines its x/y position, width, and height.
 * This class integrates with the {@link Canvas} to visually render itself.
 *
 * Implements:
 * <ul>
 *   <li>{@link Shape} – for rendering and bounding box info</li>
 *   <li>{@link Colorable} – for setting its color</li>
 *   <li>{@link Fillable} – for supporting fill operations</li>
 *   <li>{@link Movable} – for translation and scaling</li>
 * </ul>
 */
public class Ellipse implements Shape, Colorable, Fillable, Movable {

    private Color color = Color.BLACK;
    private boolean filled = false;
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * Constructs an ellipse.
     *
     * @param x      the leftmost x-coordinate
     * @param y      the topmost y-coordinate
     * @param width  the width of the bounding box
     * @param height the height of the bounding box
     */
    public Ellipse(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the leftmost x-position of this ellipse.
     *
     * @return the leftmost x-position
     */
    @Override
    public int getX() {
        return (int) Math.round(x);
    }

    /**
     * Gets the topmost y-position of this ellipse.
     *
     * @return the topmost y-position
     */
    @Override
    public int getY() {
        return (int) Math.round(y);
    }


    /**
     * Gets the width of the bounding box.
     *
     * @return the width
     */
    @Override
    public int getWidth() {
        return (int) Math.round(width);
    }

    /**
     * Gets the height of the bounding box.
     *
     * @return the height
     */
    @Override
    public int getHeight() {
        return (int) Math.round(height);
    }

    /**
     * Moves this ellipse by a given amount.
     *
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    @Override
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
        Canvas.getCanvas().repaint();
    }

    /**
     * Resizes this ellipse both horizontally and vertically.
     *
     * @param dw the amount by which to resize the width on each side
     * @param dh the amount by which to resize the height on each side
     */
    @Override
    public void grow(double dw, double dh) {
        width += 2 * dw;
        height += 2 * dh;
        x -= dw;
        y -= dh;
        Canvas.getCanvas().repaint();
    }

    /**
     * Sets the color of this ellipse.
     *
     * @param newColor the new color
     */
    @Override
    public void setColor(Color newColor) {
        color = newColor;
        Canvas.getCanvas().repaint();
    }

    /**
     * Draws this ellipse on the canvas.
     */
    @Override
    public void draw() {
        filled = false;
        Canvas.getCanvas().show(this);
    }

    /**
     * Deletes this ellipse from the canvas
     */
    @Override
    public void delete() {
        Canvas.getCanvas().hide(this);
    }

    /**
     * Fills this ellipse.
     */
    @Override
    public void fill() {
        filled = true;
        Canvas.getCanvas().show(this);
    }

    /**
     * Returns a string representation of this ellipse.
     *
     * @return a string describing the ellipse's position and dimensions
     */
    @Override
    public String toString() {
        return "Ellipse[x=" + getX() + ",y=" + getY() + ",width=" + getWidth() + ",height=" + getHeight() + "]";
    }

    /**
     * Paints this ellipse using the given Graphics2D context.
     * This is called by the canvas to render the shape.
     *
     * @param g2D the Graphics2D context to draw with
     */
    @Override
    public void paintShape(Graphics2D g2D) {
        Ellipse2D.Double ellipse = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight());
        g2D.setColor(new java.awt.Color((int) color.getRed(), (int) color.getGreen(), (int) color.getBlue()));

        if (filled) {
            g2D.fill(ellipse);

        } else {
            g2D.draw(ellipse);
        }
    }
}
