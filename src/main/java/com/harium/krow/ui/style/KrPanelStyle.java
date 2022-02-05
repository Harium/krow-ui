package com.harium.krow.ui.style;

import com.harium.krow.ui.component.KrPanel;
import lombok.NoArgsConstructor;

/**
 * Specialized style class for {@link KrPanel} widgets.
 */
@NoArgsConstructor
public final class KrPanelStyle extends KrWidgetStyle {

    public KrPanelStyle(KrWidgetStyle other) {
        super(other);
    }

    public KrPanelStyle(KrPanelStyle other) {
        super(other);
    }

    public KrPanelStyle copy() {
        return new KrPanelStyle(this);
    }
}
