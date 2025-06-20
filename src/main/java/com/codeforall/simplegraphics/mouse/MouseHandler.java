package com.codeforall.simplegraphics.mouse;

/**
 * Interface to be implemented by all classes that want to receive mouse events
 *
 * @see MouseEvent
 */
public interface MouseHandler {

    /**
     * @param e the event
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    void mouseClicked(MouseEvent e);

    /**
     * @param e the event
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    void mouseMoved(MouseEvent e);
}
