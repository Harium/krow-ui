package com.harium.krow.ui.style;

import com.badlogic.gdx.graphics.Color;
import com.harium.krow.ui.component.KrTableView;

/**
 * Specialized style class for {@link KrTableView} widgets.
 */
public class KrItemViewStyle extends KrWidgetStyle {

    public Color gridColor;

    public boolean gridVisible;

    public KrItemViewStyle(KrWidgetStyle other) {
        super(other);
    }

    public KrItemViewStyle(KrItemViewStyle other) {
        super(other);
        this.gridColor = other.gridColor;
        this.gridVisible = other.gridVisible;
    }

    public KrItemViewStyle copy() {
        return new KrItemViewStyle(this);
    }
}
