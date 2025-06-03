package com.codeforall.simplegraphics.graphics;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The {@code Rectangle} class represents a drawable and optionally filled rectangle
 * that can be displayed on a canvas. This class allows manipulation of the rectangle's
 * size, position, and color, and provides basic drawing and transformation capabilities.
 *
 * Implements:
 *  <ul>
 *      <li>{@link com.codeforall.simplegraphics.graphics.Shape} – for rendering and bounding box info</li>
 *      <li>{@link Colorable} – for setting its color</li>
 *      <li>{@link Fillable} – for supporting fill operations</li>
 *      <li>{@link Movable} – for translation and scaling</li>
 *  </ul>
 *
 * <strong>Note:</strong> This class is distinct from {@code java.awt.Rectangle}
 * and is intended for use within the simplegraphics library.
 */
public class Rectangle implements Shape, Colorable, Fillable, Movable {

    private Color color = Color.BLACK;
    private boolean filled = false;
    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * Constructs an empty rectangle.
     */
    public Rectangle() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    /**
     * Constructs a rectangle.
     *
     * @param x      the leftmost x-coordinate
     * @param y      the topmost y-coordinate
     * @param width  the width
     * @param height the height
     */
    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the leftmost x-position of this rectangle.
     *
     * @return the leftmost x-position
     */
    @Override
    public int getX() {
        return (int) Math.round(x);
    }

    /**
     * Gets the topmost y-position of this rectangle.
     *
     * @return the topmost y-position
     */
    @Override
    public int getY() {
        return (int) Math.round(y);
    }

    /**
     * Gets the width of this rectangle.
     *
     * @return the width
     */
    @Override
    public int getWidth() {
        return (int) Math.round(width);
    }

    /**
     * Gets the height of this rectangle.
     *
     * @return the height
     */
    @Override
    public int getHeight() {
        return (int) Math.round(height);
    }

    /**
     * Moves this rectangle by a given amount.
     *
     * @param dx the amount by which to move in x-direction
     * @param dy the amount by which to move in y-direction
     */
    @Override
    public void translate(double dx, double dy) {
        x += dx;
        y += dy;
        Canvas.getInstance().repaint();
    }

    /**
     * Resizes this rectangle both horizontally and vertically.
     *
     * @param dw the amount by which to resize the width on each side
     * @param dh the amount by which to resize the height on each side
     */
    public void grow(double dw, double dh) {
        width += 2 * dw;
        height += 2 * dh;
        x -= dw;
        y -= dh;
        Canvas.getInstance().repaint();
    }

    /**
     * Sets the color of this rectangle.
     *
     * @param newColor the new color
     */
    @Override
    public void setColor(Color newColor) {
        color = newColor;
        Canvas.getInstance().repaint();
    }

    /**
     * Draws this rectangle.
     */
    @Override
    public void draw() {
        filled = false;
        Canvas.getInstance().show(this);
    }

    /**
     * Deletes this rectangle
     */
    @Override
    public void delete() {
        Canvas.getInstance().hide(this);
    }

    /**
     * Fills this rectangle.
     */
    @Override
    public void fill() {
        filled = true;
        Canvas.getInstance().show(this);
    }

    /**
     * Returns a string representation of the rectangle, including both endpoints.
     *
     * @return a string describing the rectangle's coordinates
     */
    @Override
    public String toString() {
        return "Rectangle[x=" + getX() + ",y=" + getY() + ",width=" + getWidth() + ",height=" + getHeight() + "]";
    }

    /**
     * Paints the rectangle using the specified Graphics2D context.
     *
     * @param g2D the graphics context to paint with
     */
    @Override
    public void paintShape(Graphics2D g2D) {
        Rectangle2D.Double rect = new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
        g2D.setColor(new java.awt.Color((int) color.getRed(), (int) color.getGreen(), (int) color.getBlue()));

        if (filled) {
            g2D.fill(rect);

        } else {
            g2D.draw(rect);
        }
    }
}
