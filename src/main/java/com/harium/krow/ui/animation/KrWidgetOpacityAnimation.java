package com.harium.krow.ui.animation;

import com.harium.krow.ui.component.KrWidget;

import static com.harium.krow.ui.animation.KrInterpolation.interpolate;

/**
 * Animates the opacity of a widget
 */
public class KrWidgetOpacityAnimation extends KrAnimation {

    private final KrWidget widget;

    private final float startOpacity;

    private final float endOpacity;

    public KrWidgetOpacityAnimation(KrWidget widget, float endOpacity, float duration, KrAnimationEasing.KrEaseFunction easing) {
        super(duration, easing);
        this.widget = widget;
        this.startOpacity = widget.getOpacity();
        this.endOpacity = endOpacity;
    }

    @Override
    public void doUpdate(float currentValue) {
        widget.setOpacity(interpolate(easing.apply(currentValue), startOpacity, endOpacity));
    }
}
