package com.harium.krow.ui.component;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.KrFontAwesomeGlyph;
import com.harium.krow.ui.KrRectangles;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.render.KrRenderer;
import lombok.Getter;
import lombok.Setter;

import static com.harium.krow.ui.KrRectangles.rectangles;

/**
 * A transparent panel that renders an icon.
 */
public class KrIconPanel extends KrWidget {

    @Getter @Setter private KrFontAwesomeGlyph iconGlyph;

    private final BitmapFont fontAwesome;

    @Getter @Setter private int iconOffsetX = 0;

    @Getter @Setter private int iconOffsetY = 0;

    public KrIconPanel(KrFontAwesomeGlyph iconGlyph) {
        this.iconGlyph = iconGlyph;
        this.fontAwesome = KrToolkit.getDefaultToolkit().getSkin().getFontAwesome();
        setDefaultStyle(KrToolkit.getDefaultToolkit().getSkin().getStyle(KrIconPanel.class));
        setSize(calculatePreferredSize());
    }

    @Override
    public Vector2 calculatePreferredSize() {
        Rectangle bounds = KrToolkit.getDefaultToolkit().fontMetrics().bounds(fontAwesome, iconGlyph.getRepresentation(), tmpRect);
        return KrRectangles.rectangles(bounds).expand(getPadding()).size();
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        super.drawSelf(renderer);
        Rectangle bounds = KrToolkit.getDefaultToolkit().fontMetrics().bounds(fontAwesome, iconGlyph.getRepresentation(), tmpRect);
        renderer.setFont(KrToolkit.getDefaultToolkit().getSkin().getFontAwesome());
        renderer.drawText(iconGlyph.getRepresentation(), iconOffsetX + (getWidth() - bounds.width) / 2, iconOffsetY + (getHeight() - bounds.height) / 2);
    }
}
