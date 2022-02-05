package com.harium.krow.ui.event.listener;

import com.harium.krow.ui.event.KrFocusEvent;

/**
 * Listener for focus events.
 */
public interface KrFocusListener {

    void focusGained(KrFocusEvent event);

    void focusLost(KrFocusEvent event);
}
