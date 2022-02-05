package com.harium.krow.ui.component;

import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.KrCursor;
import com.harium.krow.ui.KrRectangles;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.event.KrMouseEvent;
import com.harium.krow.ui.model.KrValueModel;
import com.harium.krow.ui.render.KrRenderer;
import com.harium.krow.ui.style.KrCheckboxStyle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.harium.krow.ui.KrRectangles.rectangles;

/**
 * A simple checkbox component that can be either checked or unchecked.
 */
public class KrCheckbox extends KrWidget {

    private static final int CHECKBOX_WIDTH = 14;

    private static final int CHECKBOX_HEIGHT = 15;

    private static final int TEXT_SPACING = 4;

    @Getter @Setter private KrValueModel<Boolean> model = new KrValueModel.KrAbstractValueModel<>(false);

    private final List<ValueListener> valueListeners = new ArrayList<>();

    public KrCheckbox() {
        setDefaultStyle(KrToolkit.getDefaultToolkit().getSkin().getStyle(KrCheckbox.class));
        setSize(calculatePreferredSize());
        setCursor(KrCursor.HAND);
    }

    public void setChecked(boolean checked) {
        if (checked != model.getValue()) {
            model.setValue(checked);
            notifyValueChanged(checked);
        }
    }

    @SuppressWarnings("unused")
    public void addValueListener(ValueListener valueListener) {
        valueListeners.add(valueListener);
    }

    @SuppressWarnings("unused")
    public void removeValueListener(ValueListener valueListener) {
        valueListeners.remove(valueListener);
    }

    private void notifyValueChanged(boolean newValue) {
        valueListeners.forEach(listener -> listener.valueChanged(newValue));
    }

    @Override
    public Vector2 calculatePreferredSize() {
        float textWidth = text.getBounds().width;
        int spacing = textWidth > 0 ? TEXT_SPACING : 0;
        return KrRectangles.rectangles(text.getBounds()).expand(getPadding()).expand(14 + spacing, 0).minHeight(15).size();
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        float checkboxX = getPadding().left;
        float checkboxY = (getHeight() - CHECKBOX_HEIGHT) / 2;

        KrCheckboxStyle style = (KrCheckboxStyle) getStyle();

        renderer.setBrush(style.checkboxBackground);
        renderer.fillRect(checkboxX, checkboxY, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);

        if (model.getValue()) {
            renderer.setBrush(style.mark);
            renderer.fillRect(checkboxX, checkboxY, CHECKBOX_WIDTH, CHECKBOX_HEIGHT);
        }

        float textX = checkboxX + CHECKBOX_WIDTH + TEXT_SPACING;
        float textY = (getHeight() - text.getBounds().getHeight()) / 2;
        renderer.setPen(1, getForeground());
        renderer.drawText(text.getString(), textX, textY);
    }

    @Override
    protected void mousePressedEvent(KrMouseEvent event) {
        super.mousePressedEvent(event);

        if (event.getButton() == KrMouseEvent.Button.LEFT) {
            setChecked(!model.getValue());
            event.accept();
        }
    }

    @Override
    public String toString() {
        return toStringBuilder().type("KrCheckbox").toString();
    }

    public interface ValueListener {
        void valueChanged(boolean newValue);
    }
}
