package com.harium.krow.ui.style;

import com.harium.krow.ui.component.KrLabel;
import lombok.NoArgsConstructor;

/**
 * Specialized style class for {@link KrLabel} widgets.
 */
@NoArgsConstructor
public final class KrLabelStyle extends KrWidgetStyle {

    public KrLabelStyle(KrWidgetStyle other) {
        super(other);
    }

    public KrLabelStyle(KrLabelStyle other) {
        super(other);
    }

    public KrLabelStyle copy() {
        return new KrLabelStyle(this);
    }
}
