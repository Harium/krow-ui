package com.harium.krow.ui.layout;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.component.KrWidget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@link KrCardLayout} lays all widgets on top of each other, on separate 'cards'.
 * <p>
 * The currently visible card can be changed.
 */
public class KrCardLayout implements KrLayout {

    private final Map<Object, KrWidget> cards = new HashMap<>();

    private final Map<KrWidget, Object> widgetToCardMap = new HashMap<>();

    private final List<KrWidget> widgets = new ArrayList<>();

    private Object visibleCard;

    public void setCard(Object card) {
        if (visibleCard != card) {
            visibleCard = card;

            for (KrWidget widget : cards.values()) {
                widget.setVisible(false);
            }
            cards.get(visibleCard).setVisible(true);
        }
    }

    public Object getCard() {
        return visibleCard;
    }

    @Override
    public void setGeometry(Rectangle geometry) {
        for (KrWidget card : cards.values()) {
            card.setGeometry(geometry);
        }
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

        cards.put(layoutConstraint, child);
        widgetToCardMap.put(child, layoutConstraint);

        if (visibleCard == null) {
            visibleCard = layoutConstraint;
        } else {
            child.setVisible(false);
        }
    }

    @Override
    public void removeWidget(KrWidget child) {
        widgets.remove(child);
        cards.remove(widgetToCardMap.get(child));
        widgetToCardMap.remove(child);
    }
}
