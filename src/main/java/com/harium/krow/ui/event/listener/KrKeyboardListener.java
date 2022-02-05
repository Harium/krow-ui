package com.harium.krow.ui.event.listener;

import com.harium.krow.ui.event.KrKeyEvent;

/**
 * Listener for keyboard events.
 */
public interface KrKeyboardListener {

    void keyPressed(KrKeyEvent event);

    void keyReleased(KrKeyEvent event);
}
