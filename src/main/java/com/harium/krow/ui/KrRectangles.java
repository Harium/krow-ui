package com.harium.krow.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.harium.krow.ui.util.ReturnsPooledObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * The {@link KrRectangles} class offers functionality for manipulating {@link Rectangle} objects.
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class KrRectangles {

    private float x, y, w, h;

    public static KrRectangles rectangles(Rectangle rectangle) {
        return rectangles(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public static KrRectangles rectangles(Vector2 size) {
        return rectangles(0, 0, size.x, size.y);
    }

    public static KrRectangles rectangles(float width, float height) {
        return rectangles(0, 0, width, height);
    }

    public static KrRectangles rectangles(float x, float y, float width, float height) {
        return Pools.obtain(KrRectangles.class).set(x, y, width, height);
    }

    /**
     * Used by the cache to create new objects.
     */
    @SuppressWarnings("unused")
    public KrRectangles() {
        x = y = w = h = 0;
    }

    public KrRectangles set(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        return this;
    }

    public KrRectangles shrink(KrPadding padding) {
        if (padding == null) {
            return this;
        }
        x += padding.left;
        y += padding.top;
        w -= padding.getHorizontalPadding();
        h -= padding.getVerticalPadding();
        return this;
    }

    public KrRectangles expand(KrPadding padding) {
        if (padding == null) {
            return this;
        }
        x -= padding.left;
        y -= padding.top;
        w += padding.getHorizontalPadding();
        h += padding.getVerticalPadding();
        return this;
    }

    public KrRectangles expand(Vector2 size) {
        return expand((int) size.x, (int) size.y);
    }

    public KrRectangles expand(int width, int height) {
        w += width;
        h += height;
        return this;
    }

    public KrRectangles intersect(Rectangle other) {
        Segment horizontalProjection = project(horizontalProjection(x, y, w, h), horizontalProjection(other));
        Segment verticalProjection = project(verticalProjection(x, y, w, h), verticalProjection(other));

        w = horizontalProjection.getSecond() - horizontalProjection.getFirst();
        h = verticalProjection.getSecond() - verticalProjection.getFirst();

        if (w == 0 || h == 0) {
            x = 0;
            y = 0;
        } else {
            x = horizontalProjection.getFirst();
            y = verticalProjection.getFirst();
        }

        return this;
    }

    public KrRectangles minWidth(int minWidth) {
        w = Math.max(w, minWidth);
        return this;
    }

    public KrRectangles minHeight(int minHeight) {
        h = Math.max(h, minHeight);
        return this;
    }

    private static Segment verticalProjection(Rectangle rectangle) {
        return verticalProjection(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    private static Segment verticalProjection(float x, float y, float w, float h) {
        return new Segment(y, y + h);
    }

    private static Segment horizontalProjection(Rectangle rectangle) {
        return horizontalProjection(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    private static Segment horizontalProjection(float x, float y, float w, float h) {
        return new Segment(x, x + w);
    }

    private static Segment project(Segment first, Segment second) {
        if (first.getFirst() > second.getFirst()) {
            Segment tmp = first;
            first = second;
            second = tmp;
        }

        if (second.getFirst() > first.getSecond()) {
            return Segment.ZERO;
        }
        return new Segment(second.getFirst(), Math.min(second.getSecond(), first.getSecond()));
    }

    @ReturnsPooledObject
    public Rectangle value() {
        Rectangle result = Pools.obtain(Rectangle.class);
        if (w == 0 || h == 0) {
            result.set(x, y, 0, 0);
        } else {
            result.set(x, y, w, h);
        }
        Pools.free(this);
        return result;
    }

    public Rectangle value(Rectangle value) {
        if (w == 0 || h == 0) {
            value.set(x, y, 0, 0);
        } else {
            value.set(x, y, w, h);
        }
        return value;
    }

    public Vector2 size() {
        return new Vector2(w, h);
    }

    public Vector2 size(Vector2 size) {
        return size.set(w, h);
    }

    private static class Segment extends KrPair<Float, Float> {
        static final Segment ZERO = new Segment(0.0f, 0.0f);

        Segment(Float first, Float second) {
            super(first, second);
        }
    }
}
