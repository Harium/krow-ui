package com.harium.krow.ui.style;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.harium.krow.ui.component.KrScrollBar;
import lombok.NoArgsConstructor;

/**
 * Specialized style class for {@link KrScrollBar} widgets.
 */
@NoArgsConstructor
public class KrScrollBarStyle extends KrWidgetStyle {

    public Drawable track;

    public Drawable thumb;

    public float size;

    public KrScrollBarStyle(KrWidgetStyle other) {
        super(other);
    }

    public KrScrollBarStyle(KrScrollBarStyle other) {
        super(other);
        this.track = other.track;
        this.thumb = other.thumb;
        this.size = other.size;
    }

    public KrScrollBarStyle copy() {
        return new KrScrollBarStyle(this);
    }
}
