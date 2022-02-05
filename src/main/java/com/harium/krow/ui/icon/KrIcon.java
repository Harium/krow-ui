package com.harium.krow.ui.icon;

import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.render.KrRenderer;

/**
 * An icon is a small graphical object used to
 * represent an action / state / etc.
 */
public interface KrIcon {
    Vector2 getSize();

    void draw(KrRenderer renderer, int x, int y);
}
