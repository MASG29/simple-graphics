package org.academiadecodigo.simplegraphics.keyboard;

import org.academiadecodigo.simplegraphics.graphics.Canvas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * Instantiate a Keyboard for obtaining key handling capability
 */
public class Keyboard implements KeyListener {

    private KeyboardHandler handler;
    private List<KeyboardEvent> keyboardEvents;

    public Keyboard(KeyboardHandler handler) {
        Canvas.getInstance().addKeyListener(this);

        this.handler = handler;
        keyboardEvents = new ArrayList<>();
    }

    /**
     * Add a new Keyboard event listener
     *
     * @param keyboardEvent the event to add
     */
    public void addEventListener(KeyboardEvent keyboardEvent) {
        keyboardEvents.add(keyboardEvent);
    }

    /**
     * Remove an existing Keyboard event listener
     *
     * @param keyboardEvent the event to remove
     */
    public void removeEventListener(KeyboardEvent keyboardEvent) {
        keyboardEvents.remove(keyboardEvent);
    }

    /**
     * @param e the event
     * @see KeyListener#keyTyped(KeyEvent)
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * @param e the event
     * @see KeyboardHandler#keyPressed(KeyboardEvent)
     */
    @Override
    public void keyPressed(KeyEvent e) {

        if (handler == null) {
            return;
        }

        Iterator<KeyboardEvent> iterator = keyboardEvents.iterator();

        while (iterator.hasNext()) {
            KeyboardEvent event = iterator.next();

            if (event.getKeyboardEventType() == KeyboardEventType.KEY_PRESSED &&
                    event.getKey() == e.getKeyCode()) {
                handler.keyPressed(event);
            }
        }
    }

    /**
     * @param e the event
     * @see KeyboardHandler#keyReleased(KeyboardEvent)
     */
    @Override
    public void keyReleased(KeyEvent e) {

        if (handler == null) {
            return;
        }

        Iterator<KeyboardEvent> iterator = keyboardEvents.iterator();

        while (iterator.hasNext()) {
            KeyboardEvent event = iterator.next();

            if (event.getKeyboardEventType() == KeyboardEventType.KEY_RELEASED &&
                    event.getKey() == e.getKeyCode()) {
                handler.keyReleased(event);
            }
        }
    }

    /**
     * Returns the current {@link KeyboardHandler} assigned to handle keyboard events.
     *
     * @return the keyboard handler
     */
    public KeyboardHandler getHandler() {
        return handler;
    }

    /**
     * Sets the {@link KeyboardHandler} that will be used to handle keyboard events.
     *
     * @param handler the keyboard handler to set
     */
    public void setHandler(KeyboardHandler handler) {
        this.handler = handler;
    }

    /**
     * Returns the list of registered {@link KeyboardEvent}s that this class listens for.
     *
     * @return the list of keyboard events
     */
    public List<KeyboardEvent> getKeyboardEvents() {
        return keyboardEvents;
    }

    /**
     * Sets the list of {@link KeyboardEvent}s that this class should listen for.
     *
     * @param keyboardEvents the list of keyboard events to set
     */
    public void setKeyboardEvents(List<KeyboardEvent> keyboardEvents) {
        this.keyboardEvents = keyboardEvents;
    }
}
