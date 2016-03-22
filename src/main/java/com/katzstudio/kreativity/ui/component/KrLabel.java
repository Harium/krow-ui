package com.katzstudio.kreativity.ui.component;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.katzstudio.kreativity.ui.*;
import com.katzstudio.kreativity.ui.render.KrDrawableBrush;
import com.katzstudio.kreativity.ui.render.KrPen;
import com.katzstudio.kreativity.ui.render.KrRenderer;
import com.katzstudio.kreativity.ui.style.KrLabelStyle;
import lombok.Getter;
import lombok.Setter;

import static com.katzstudio.kreativity.ui.KrRectangles.rectangles;
import static com.katzstudio.kreativity.ui.KrToolkit.getDefaultToolkit;

/**
 * A simple label component
 */
public class KrLabel extends KrWidget<KrLabelStyle> {

    @Getter @Setter private String text;

    @Setter @Getter private KrAlignment textAlignment;

    public KrLabel(String text) {
        this.text = text;
        this.textAlignment = KrAlignment.MIDDLE_LEFT;

        setStyle(KrSkin.instance().getLabelStyle());
        setPadding(new KrPadding(3, 3));
        setSize(calculatePreferredSize());
    }

    @Override
    public void ensureUniqueStyle() {
        if (style == KrSkin.instance().getLabelStyle()) {
            style = new KrLabelStyle(style);
        }
    }

    @Override
    public Vector2 calculatePreferredSize() {
        return rectangles(getTextBounds()).expand(getPadding()).size();
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        boolean componentClip = renderer.beginClip(0, 0, getWidth(), getHeight());

        renderer.setBrush(new KrDrawableBrush(style.background));
        renderer.fillRect(0, 0, getWidth(), getHeight());

        Vector2 textBounds = getTextBounds();
        Rectangle alignmentReference = rectangles(new Rectangle(0, 0, getWidth(), getHeight())).shrink(getPadding()).value();
        Vector2 textPosition = KrAlignmentTool.alignRectangles(new Rectangle(0, 0, textBounds.x, textBounds.y), alignmentReference, getTextAlignment());
        renderer.setPen(new KrPen(1, style.foregroundColor));
        renderer.setFont(style.font);
        renderer.drawText(text, textPosition);

        if (componentClip) {
            renderer.endClip();
        }
    }

    protected Vector2 getTextBounds() {
        KrFontMetrics metrics = getDefaultToolkit().fontMetrics();
        return metrics.bounds(style.font, text).getSize(new Vector2());
    }

    @Override
    public KrLabelStyle getStyle() {
        return style;
    }

    @Override
    public String toString() {
        return toStringBuilder().type("KrLabel").toString();
    }

}
