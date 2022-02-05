package com.harium.krow.ui.component;

import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.KrOrientation;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.math.KrRange;
import com.harium.krow.ui.render.KrRenderer;
import com.harium.krow.ui.style.KrSliderStyle;

/**
 * A slider represents a numeric value in a predefined range.
 */
public class KrSlider extends KrScrollBar {

    public KrSlider() {
        super(KrOrientation.HORIZONTAL, KrToolkit.getDefaultToolkit().getSkin().getStyle(KrSlider.class));
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        KrSliderStyle style = (KrSliderStyle) getStyle();
        renderer.setBrush(style.track);
        renderer.fillRect((int) (getThumbLength() / 2) - 2, 0, getWidth() - (int)(Math.ceil(getThumbLength() / 2)), 7);

        tmpRect.set((int)thumbPosition, 1, (int)style.thumb.getMinWidth(), (int)style.thumb.getMinHeight());
        renderer.setBrush(style.thumb);
        renderer.fillRect(tmpRect);
    }

    @Override
    public Vector2 calculatePreferredSize() {
        return new Vector2(100, 12);
    }

    @Override
    public void setValueRange(KrRange valueRange) {
        super.setValueRange(valueRange);
        thumbLength = ((KrSliderStyle) getStyle()).thumb.getMinWidth();
    }

    @Override
    protected KrRange getThumbRange() {
        KrRange krRange = new KrRange(0, getWidth() - getThumbLength());
        return krRange;
    }

    @Override
    protected float getThumbLength() {
        return ((KrSliderStyle) getStyle()).thumb.getMinWidth();
    }
}
