package com.harium.krow.ui.backend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.harium.krow.ui.KrCursor;
import com.harium.krow.ui.KrFontMetrics;
import com.harium.krow.ui.render.KrRenderer;

/**
 * The {@link KrBackend} interface offers access to components that are bound to
 * the low level API of the platform.
 */
public interface KrBackend {

    KrRenderer getRenderer();

    KrInputSource getInputSource();

    KrFontMetrics getFontMetrics();

    void setCursor(KrCursor cursor);

    KrCursor getCursor();

    void writeToClipboard(String value);

    String readFromClipboard();

    Drawable createColorDrawable(Color color);

    int getScreenWidth();

    int getScreenHeight();
}
