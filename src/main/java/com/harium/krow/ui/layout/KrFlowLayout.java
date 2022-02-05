package com.harium.krow.ui.layout;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.KrOrientation;
import com.harium.krow.ui.component.KrWidget;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A flow layout is used to arrange widgets in a row or a column.
 * <p>
 * A vertical flow layout distributes all widgets in a single column.
 * All widgets have the same width, but preferred heights are taken
 * into consideration.
 * <p>
 * A horizontal flow layout distributes all widgets in a single row.
 * All widgets have the same height, but preferred widths are taken
 * into consideration.
 */
@RequiredArgsConstructor
public class KrFlowLayout implements KrLayout {

    private final KrOrientation orientation;

    private final int horizontalPadding;

    private final int verticalPadding;

    private final List<KrWidget> widgets = new ArrayList<>();

    @SuppressWarnings("unused")
    public KrFlowLayout() {
        this(KrOrientation.HORIZONTAL, 0, 0);
    }

    public KrFlowLayout(KrOrientation direction) {
        this(direction, 0, 0);
    }

    @Override
    public void setGeometry(Rectangle geometry) {
        if (widgets.isEmpty()) {
            return;
        }

        float cellX = horizontalPadding + geometry.x;
        float cellY = verticalPadding + geometry.y;
        float cellHeight = 0;
        float cellWidth = 0;

        if (orientation == KrOrientation.HORIZONTAL) {
            cellHeight = geometry.getHeight() - 2 * verticalPadding;
        } else {
            cellWidth = geometry.getWidth() - 2 * horizontalPadding;
        }

        for (KrWidget widget : widgets) {
            if (orientation == KrOrientation.HORIZONTAL) {
                cellWidth = widget.getPreferredWidth();
            } else {
                cellHeight = widget.getPreferredHeight();
            }

            layoutInsideCell(widget, new Rectangle(cellX, cellY, cellWidth, cellHeight));

            if (orientation == KrOrientation.HORIZONTAL) {
                cellX += cellWidth + horizontalPadding;
            } else {
                cellY += cellHeight + verticalPadding;
            }
        }
    }

    private void layoutInsideCell(KrWidget widget, Rectangle geometry) {
        widget.setGeometry(geometry);
    }

    @Override
    public Vector2 getMinSize() {
        return getSize(KrWidget::getMinSize);
    }

    @Override
    public Vector2 getMaxSize() {
        return new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
    }

    @Override
    public Vector2 getPreferredSize() {
        return getSize(KrWidget::getPreferredSize);
    }

    private Vector2 getSize(Function<KrWidget, Vector2> widgetSizeFunction) {
        if (widgets.size() == 0) {
            return new Vector2(0, 0);
        }

        float totalHorizontalPadding = horizontalPadding * (getCols() + 1);
        float totalVerticalPadding = verticalPadding * (getRows() + 1);

        float widgetHorizontalSize;
        float widgetVerticalSize;

        if (orientation == KrOrientation.HORIZONTAL) {
            widgetHorizontalSize = widgets.stream().map(widgetSizeFunction).map(v -> v.x).reduce(Float::sum).orElse(0.0f);
            widgetVerticalSize = widgets.stream().map(widgetSizeFunction).map(v -> v.y).max(Float::compare).orElse(0.0f);
        } else {
            widgetHorizontalSize = widgets.stream().map(widgetSizeFunction).map(v -> v.x).max(Float::compare).orElse(0.0f);
            widgetVerticalSize = widgets.stream().map(widgetSizeFunction).map(v -> v.y).reduce(Float::sum).orElse(0.0f);
        }

        return new Vector2(totalHorizontalPadding + widgetHorizontalSize, totalVerticalPadding + widgetVerticalSize);
    }

    private int getRows() {
        return orientation == KrOrientation.VERTICAL ? widgets.size() : 1;
    }

    private int getCols() {
        return orientation == KrOrientation.HORIZONTAL ? widgets.size() : 1;
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
