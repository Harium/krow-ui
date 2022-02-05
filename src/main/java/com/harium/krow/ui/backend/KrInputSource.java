package com.harium.krow.ui.backend;

import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.event.KrKeyEvent;
import com.harium.krow.ui.event.KrMouseEvent;
import com.harium.krow.ui.event.KrScrollEvent;

/**
 * The input source abstracts the underlying IO backend and offers
 * support for registering event listeners, as well as polling for
 * some states.
 */
public interface KrInputSource {

    boolean isAltDown();

    boolean isCtrlDown();

    boolean isShiftDown();

    boolean isDragging();

    Vector2 getMousePosition();

    void addEventListener(KrInputEventListener listener);

    void removeEventListener(KrInputEventListener listener);

    interface KrInputEventListener {
        void mouseMoved(KrMouseEvent event);

        void mousePressed(KrMouseEvent event);

        void mouseReleased(KrMouseEvent event);

        void mouseDoubleClicked(KrMouseEvent event);

        void keyPressed(KrKeyEvent event);

        void keyReleased(KrKeyEvent event);

        void scrolledEvent(KrScrollEvent event);
    }
}
