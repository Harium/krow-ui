package com.harium.krow.ui.layout;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.component.KrWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link KrStackLayout} arranges all widgets on top of each other, with the same geometry as
 * the parent.
 */
public class KrStackLayout implements KrLayout {

    private final List<KrWidget> widgets = new ArrayList<>();

    @Override
    public void setGeometry(Rectangle geometry) {
        widgets.forEach(widget -> widget.setGeometry(geometry));
    }

    @Override
    public Vector2 getMinSize() {
        float minWidth = widgets.stream().map(KrWidget::getMinWidth).max(Float::compare).orElse(0.0f);
        float minHeight = widgets.stream().map(KrWidget::getMinHeight).max(Float::compare).orElse(0.0f);
        return new Vector2(minWidth, minHeight);
    }

    @Override
    public Vector2 getMaxSize() {
        float maxWidth = widgets.stream().map(KrWidget::getMaxWidth).min(Float::compare).orElse(0.0f);
        float maxHeight = widgets.stream().map(KrWidget::getMaxHeight).min(Float::compare).orElse(0.0f);
        return new Vector2(maxWidth, maxHeight);
    }

    @Override
    public Vector2 getPreferredSize() {
        float prefWidth = widgets.stream().map(KrWidget::getPreferredWidth).max(Float::compare).orElse(0.0f);
        float prefHeight = widgets.stream().map(KrWidget::getPreferredHeight).max(Float::compare).orElse(0.0f);
        return new Vector2(prefWidth, prefHeight);
    }

    @Override
    public void addWidget(KrWidget child, Object layoutConstraint) {
        widgets.add(child);
    }

    @Override
    public void removeWidget(KrWidget child) {
        widgets.remove(child);
    }
}
