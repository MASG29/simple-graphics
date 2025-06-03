package org.academiadecodigo.simplegraphics.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.academiadecodigo.simplegraphics.graphics.Canvas;

/**
 * Instantiate a Mouse for obtaining mouse handling capability
 */
public class Mouse implements MouseListener, MouseMotionListener {

    private MouseHandler handler;
    private List<MouseEventType> mouseEventTypes;

    public Mouse(MouseHandler handler) {
        Canvas.getInstance().addMouseListener(this);
        Canvas.getInstance().addMouseMotionListener(this);

        this.handler = handler;
        this.mouseEventTypes = new ArrayList();
    }

    /**
     * Add a new Mouse event listener
     *
     * @param mouseEventType the event type to add
     */
    public void addEventListener(MouseEventType mouseEventType) {
        mouseEventTypes.add(mouseEventType);
    }

    /**
     * Remove an existing Mouse event listener
     *
     * @param mouseEventType the event to remove
     */
    public void removeEventListener(MouseEventType mouseEventType) {
        mouseEventTypes.remove(mouseEventType);
    }

    /**
     * @param e the event to be processed
     * @see MouseListener#mouseClicked(MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (handler != null) {
            Iterator iterator = mouseEventTypes.iterator();

            while (iterator.hasNext()) {
                MouseEventType eventType = (MouseEventType) iterator.next();

                if (eventType == MouseEventType.MOUSE_CLICKED) {
                    handler.mouseClicked(new org.academiadecodigo.simplegraphics.mouse.MouseEvent(e.getX(), e.getY(), MouseEventType.MOUSE_CLICKED));
                }
            }
        }
    }

    /**
     * @param e the event to be processed
     * @see MouseMotionListener#mouseMoved(MouseEvent)
     */
    @Override
    public void mouseMoved(MouseEvent e) {

        if (handler != null) {
            Iterator iterator = mouseEventTypes.iterator();

            while (iterator.hasNext()) {
                MouseEventType eventType = (MouseEventType) iterator.next();

                if (eventType == MouseEventType.MOUSE_MOVED) {
                    handler.mouseMoved(new org.academiadecodigo.simplegraphics.mouse.MouseEvent(e.getX(), e.getY(), MouseEventType.MOUSE_MOVED));
                }
            }
        }
    }

    /**
     * @param e the event to be processed
     * @see MouseListener#mousePressed(MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * @param e the event to be processed
     * @see MouseListener#mouseReleased(MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * @param e the event to be processed
     * @see MouseListener#mouseEntered(MouseEvent)
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * @param e the event to be processed
     * @see MouseListener#mouseExited(MouseEvent)
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * @param e the event to be processed
     * @see MouseMotionListener#mouseDragged(MouseEvent)
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

    /**
     * Returns the current {@link MouseHandler} assigned to handle mouse events.
     *
     * @return the mouse handler
     */
    public MouseHandler getHandler() {
        return handler;
    }

    /**
     * Sets the {@link MouseHandler} that will be used to handle mouse events.
     *
     * @param handler the mouse handler to set
     */
    public void setHandler(MouseHandler handler) {
        this.handler = handler;
    }

    /**
     * Returns the list of registered {@link MouseEventType}s that this class listens for.
     *
     * @return the list of mouse event types
     */
    public List<MouseEventType> getMouseEventTypes() {
        return mouseEventTypes;
    }

    /**
     * Sets the list of {@link MouseEventType}s that this class should listen for.
     *
     * @param mouseEventTypes the list of mouse event types to set
     */
    public void setMouseEventTypes(List<MouseEventType> mouseEventTypes) {
        this.mouseEventTypes = mouseEventTypes;
    }
}
