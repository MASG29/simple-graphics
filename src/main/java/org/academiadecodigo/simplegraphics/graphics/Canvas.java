package org.academiadecodigo.simplegraphics.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Singleton Canvas class responsible for managing and displaying graphical shapes.
 */
public class Canvas {

    private static final int MIN_SIZE = 100;
    private static final int MARGIN = 10;
    private static final int LOCATION_OFFSET = 120;

    private static int MAX_X = 0;
    private static int MAX_Y = 0;
    private static Canvas instance = new Canvas();

    private ArrayList<Shape> shapes = new ArrayList<>();
    private BufferedImage background;
    private JFrame frame;
    private CanvasComponent component;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private Canvas() {
        component = new CanvasComponent();

        frame = new JFrame();
        frame.add(component);
        frame.pack();
        frame.setLocation(LOCATION_OFFSET, LOCATION_OFFSET);
        frame.setVisible(true);
    }

    /**
     * Sets the maximum width of the canvas.
     *
     * @param width maximum width
     */
    public static void setMaxX(int width) {
        MAX_X = width;
    }

    /**
     * Sets the maximum height of the canvas.
     *
     * @param height maximum height
     */
    public static void setMaxY(int height) {
        MAX_Y = height;
    }

    /**
     * Returns the singleton instance of the Canvas.
     *
     * @return Canvas instance
     */
    public static Canvas getInstance() {
        return instance;
    }


    /**
     * Pauses so that the user can see the picture before it is transformed.
     */
    public static void pause() {
        JFrame frame = getInstance().frame;

        if (frame == null) {
            return;
        }

        JOptionPane.showMessageDialog(frame, "Click Ok to continue");
    }

    /**
     * Takes a snapshot of the screen, fades it, and sets it as the background.
     */
    public static void snapshot() {
        Dimension dim = getInstance().component.getPreferredSize();
        Rectangle rect = new Rectangle(0, 0, dim.width, dim.height);
        BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, rect.width, rect.height);
        g.setColor(Color.BLACK);
        getInstance().component.paintComponent(g);

        float factor = 0.8f;
        float base = 255f * (1f - factor);
        RescaleOp op = new RescaleOp(factor, base, null);
        BufferedImage filteredImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        op.filter(image, filteredImage);
        getInstance().background = filteredImage;
        getInstance().component.repaint();
    }

    /**
     * Displays the given shape on the canvas.
     *
     * @param shape the shape to display
     */
    public void show(Shape shape) {
        if (!shapes.contains(shape)) {
            shapes.add(shape);
        }

        repaint();
    }

    /**
     * Hides the given shape from the canvas.
     *
     * @param shape the shape to hide
     */
    public void hide(Shape shape) {
        if (shapes.contains(shape)) {
            shapes.remove(shape);
        }

        repaint();
    }

    /**
     * Repaints the canvas if needed.
     */
    public void repaint() {
        if (frame == null) {
            return;
        }

        Dimension dim = component.getPreferredSize();

        if (dim.getWidth() > component.getWidth()
                || dim.getHeight() > component.getHeight()) {
            frame.pack();

        } else {
            frame.repaint();
        }
    }

    /**
     * Saves the current canvas view to disk as an image.
     *
     * @param fileName the path and fileName to save to
     */
    public void saveToDisk(String fileName) {
        Dimension dim = component.getPreferredSize();
        Rectangle rect = new Rectangle(0, 0, dim.width, dim.height);
        BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fill(rect);
        g.setColor(Color.BLACK);
        component.paintComponent(g);

        String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

        try {
            ImageIO.write(image, extension, new File(fileName));

        } catch (IOException e) {
            System.err.println("Was unable to save the image to " + fileName);
        }

        g.dispose();
    }

    /**
     * Adds a KeyListener to the canvas.
     *
     * @param handler the KeyListener to add
     */
    public void addKeyListener(KeyListener handler) {
        frame.addKeyListener(handler);
    }

    /**
     * Adds a MouseListener to the canvas.
     *
     * @param handler the MouseListener to add
     */
    public void addMouseListener(MouseListener handler) {
        frame.addMouseListener(handler);
    }

    /**
     * Adds a MouseMotionListener to the canvas.
     *
     * @param handler the MouseMotionListener to add
     */
    public void addMouseMotionListener(MouseMotionListener handler) {
        frame.addMouseMotionListener(handler);
    }

    /**
     * Inner component class responsible for custom painting of the canvas.
     */
    class CanvasComponent extends JComponent {
        CanvasComponent() {
        }

        /**
         * Paints the component by drawing the background and all visible shapes.
         *
         * @param g the Graphics context in which to paint
         */
        public void paintComponent(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);

            if (background != null) {
                g.drawImage(background, 0, 0, null);
            }

            for (Shape s : new ArrayList<>(shapes)) {
                Graphics2D g2D = (Graphics2D) g.create();
                s.paintShape(g2D);
                g2D.dispose();
            }
        }

        /**
         * Calculates the preferred size of the canvas based on the shapes and background image.
         *
         * @return the preferred Dimension of the canvas
         */
        public Dimension getPreferredSize() {
            int width = Canvas.MAX_X == 0 ? MIN_SIZE : Canvas.MAX_X;
            int height = Canvas.MAX_Y == 0 ? MIN_SIZE : Canvas.MAX_Y;

            if (background != null) {
                width = Math.max(width, background.getWidth());
                height = Math.max(width, background.getHeight());
            }

            if (Canvas.MAX_X == 0 && Canvas.MAX_Y == 0) {

                for (Shape s : shapes) {
                    width = Math.max(width, s.getX() + s.getWidth());
                    height = Math.max(height, s.getY() + s.getHeight());
                }

            }

            return new Dimension(width + MARGIN, height + MARGIN);
        }
    }
}
