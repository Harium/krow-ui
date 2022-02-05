package com.harium.krow.ui.component;

import com.harium.krow.ui.event.KrEnterEvent;
import com.harium.krow.ui.event.KrExitEvent;
import com.harium.krow.ui.event.KrMouseEvent;
import com.harium.krow.ui.model.KrValueModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * A toggle button is a button who remains pressed after clicking (is checked). Clicking the button again
 * unchecks the button, bringing it to its original state.
 */
public class KrToggleButton extends KrButton {

    private final List<KrToggleButtonListener> listeners = new ArrayList<>();

    @Getter @Setter KrValueModel<Boolean> model = new KrValueModel.KrAbstractValueModel<>(false);

    public KrToggleButton(String text) {
        super(text);
    }

    @Override
    protected void mousePressedEvent(KrMouseEvent event) {
        super.mousePressedEvent(event);
        setState(State.ARMED);
        event.accept();
    }

    @Override
    protected void mouseReleasedEvent(KrMouseEvent event) {
        setChecked(!isChecked());
        if (!isChecked()) {
            setState(State.HOVERED);
        }
        notifyClicked();
        notifyMouseReleased(event);
        event.accept();
    }

    @Override
    protected void enterEvent(KrEnterEvent event) {
        super.enterEvent(event);
        if (isChecked()) {
            setState(State.ARMED);
        } else {
            setState(State.HOVERED);
        }
        event.accept();
    }

    @Override
    protected void exitEvent(KrExitEvent event) {
        super.exitEvent(event);
        if (isChecked()) {
            setState(State.ARMED);
        } else {
            setState(State.NORMAL);
        }
        event.accept();
    }

    public Boolean isChecked() {
        return model.getValue();
    }

    public void setChecked(boolean isChecked) {
        if (isChecked() != isChecked) {
            model.setValue(isChecked);

            if (isChecked) {
                setState(State.ARMED);
            } else {
                setState(State.NORMAL);
            }

            notifyToggled();
        }
    }

    public void addToggleListener(KrToggleButtonListener listener) {
        listeners.add(listener);
    }

    public void removeToggleListener(KrToggleButtonListener listener) {
        listeners.remove(listener);
    }

    protected void notifyToggled() {
        listeners.forEach(listener -> listener.toggled(isChecked()));
    }

    public interface KrToggleButtonListener {
        void toggled(boolean isChecked);
    }
}
