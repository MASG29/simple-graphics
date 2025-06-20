package com.codeforall.simplegraphics.keyboard;

import java.awt.event.KeyEvent;

/**
 * Interface to be implemented by all classes that wish to receive keyboard events
 * @see KeyboardEvent
 */
public interface KeyboardHandler {

    /**
     * @see java.awt.event.KeyListener#keyPressed(KeyEvent)
     * @param e the event
     */
    void keyPressed(KeyboardEvent e);

    /**
     * @see java.awt.event.KeyListener#keyReleased(KeyEvent)
     * @param e the event
     */
    void keyReleased(KeyboardEvent e);
}
