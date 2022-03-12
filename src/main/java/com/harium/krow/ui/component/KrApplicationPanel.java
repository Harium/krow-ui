package com.harium.krow.ui.component;

import com.badlogic.gdx.ApplicationAdapter;
import com.harium.krow.ui.event.KrFocusEvent;
import com.harium.krow.ui.render.KrRenderer;

public class KrApplicationPanel extends KrWidget {

    private final KrApplicationAdapter application;

    public KrApplicationPanel(KrApplicationAdapter application) {
        this.application = application;
        this.application.parent = this;
        application.create();
        setSize(calculatePreferredSize());
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        application.render();
    }

    public void setSize(float w, float h) {
        super.setSize(w, h);
        application.resize((int) getWidth(), (int) getHeight());
    }

    @Override
    public void dispose() {
        super.dispose();
        application.dispose();
    }

    @Override
    protected void focusGainedEvent(KrFocusEvent event) {
        super.focusGainedEvent(event);
        application.resume();
    }

    @Override
    protected void focusLostEvent(KrFocusEvent event) {
        super.focusLostEvent(event);
        application.pause();
    }

    public abstract static class KrApplicationAdapter extends ApplicationAdapter {
        protected KrWidget parent;
    }
}
