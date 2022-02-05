package com.harium.krow.ui.component;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.harium.krow.ui.KrPadding;
import com.harium.krow.ui.KrRectangles;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.KrAlignment;
import com.harium.krow.ui.KrAlignmentTool;
import com.harium.krow.ui.render.KrRenderer;
import lombok.Getter;
import lombok.Setter;

import static com.harium.krow.ui.KrRectangles.rectangles;

/**
 * A simple label component
 */
public class KrLabel extends KrWidget {

    @Setter @Getter private KrAlignment textAlignment;

    public KrLabel(String text) {
        setText(text);
        this.textAlignment = KrAlignment.MIDDLE_LEFT;

        setDefaultStyle(KrToolkit.getDefaultToolkit().getSkin().getStyle(KrLabel.class));
        setPadding(new KrPadding(3, 3));
        setSize(calculatePreferredSize());
    }

    @Override
    public Vector2 calculatePreferredSize() {
        return KrRectangles.rectangles(text.getBounds()).expand(getPadding()).size();
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        boolean componentClip = renderer.beginClip(0, 0, getWidth(), getHeight());

        renderer.setBrush(getStyle().background);
        renderer.fillRect(0, 0, getWidth(), getHeight());

        text.getBounds(tmpRect);
        Rectangle alignmentReference = KrRectangles.rectangles(0.0f, 0.0f, getWidth(), getHeight()).shrink(getPadding()).value();
        Vector2 textPosition = KrAlignmentTool.alignRectangles(tmpRect, alignmentReference, getTextAlignment());
        renderer.setPen(1, getStyle().foregroundColor);
        renderer.setFont(getStyle().font);
        renderer.drawText(text.getString(), textPosition);

        if (componentClip) {
            renderer.endClip();
        }

        Pools.free(textPosition);
        Pools.free(alignmentReference);
    }

    @Override
    public String toString() {
        return toStringBuilder().type("KrLabel").toString();
    }

}
