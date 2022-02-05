package com.harium.krow.ui.event.listener;

import com.harium.krow.ui.event.KrEnterEvent;
import com.harium.krow.ui.event.KrMouseEvent;
import com.harium.krow.ui.event.KrExitEvent;
import com.harium.krow.ui.event.KrScrollEvent;

/**
 * Listener for mouse events.
 */
public interface KrMouseListener {

    void scrolled(KrScrollEvent event);

    void mouseMoved(KrMouseEvent event);

    void mousePressed(KrMouseEvent event);

    void mouseDoubleClicked(KrMouseEvent event);

    void mouseReleased(KrMouseEvent event);

    void enter(KrEnterEvent event);

    void exit(KrExitEvent event);

    abstract class KrMouseAdapter implements KrMouseListener {
        @Override
        public void scrolled(KrScrollEvent event) {
        }

        @Override
        public void mouseMoved(KrMouseEvent event) {
        }

        @Override
        public void mousePressed(KrMouseEvent event) {
        }

        @Override
        public void mouseDoubleClicked(KrMouseEvent event) {
        }

        @Override
        public void mouseReleased(KrMouseEvent event) {
        }

        @Override
        public void enter(KrEnterEvent event) {
        }

        @Override
        public void exit(KrExitEvent event) {
        }
    }
}
