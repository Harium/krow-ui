package com.harium.krow.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.harium.krow.ui.animation.KrAnimations;
import com.harium.krow.ui.backend.KrBackend;
import com.harium.krow.ui.backend.KrInputSource;
import com.harium.krow.ui.render.KrRenderer;
import com.harium.krow.ui.util.KrUpdateListener;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@link KrToolkit} offers functionality that is global to the UI framework
 * such as changing the cursor, or creating drawables.
 */
public class KrToolkit {

    private static KrToolkit INSTANCE;

    private final KrBackend backend;

    private final List<KrUpdateListener> updateListeners = new CopyOnWriteArrayList<>();

    private final KrAnimations animations;

    private KrCanvas canvas;

    @Getter private KrSkin skin;

    public static KrToolkit getDefaultToolkit() {
        return INSTANCE;
    }

    public static void initialize(KrBackend backend) {
        INSTANCE = new KrToolkit(backend);
    }

    public static void initialize(KrBackend backend, KrSkin skin) {
        INSTANCE = new KrToolkit(backend, skin);
    }

    private KrToolkit(KrBackend backend) {
        this.backend = backend;
        this.skin = new KrSkin(this);
        this.animations = new KrAnimations();
    }

    private KrToolkit(KrBackend backend, KrSkin skin) {
        this.backend = backend;
        this.skin = skin;
        this.animations = new KrAnimations();
    }

    public KrCanvas getCanvas() {
        if (canvas == null) {
            canvas = new KrCanvas(getInputSource(), getRenderer(), backend.getScreenWidth(), backend.getScreenHeight());
        }
        return canvas;
    }

    public KrFontMetrics fontMetrics() {
        return backend.getFontMetrics();
    }

    public void setCursor(KrCursor cursor) {
        backend.setCursor(cursor);
    }

    public KrCursor getCursor() {
        return backend.getCursor();
    }

    public void writeToClipboard(String value) {
        backend.writeToClipboard(value);
    }

    public String readFromClipboard() {
        return backend.readFromClipboard();
    }

    public Drawable getDrawable(Color color) {
        return backend.createColorDrawable(color);
    }

    public KrInputSource getInputSource() {
        return backend.getInputSource();
    }

    public KrRenderer getRenderer() {
        return backend.getRenderer();
    }

    public static KrAnimations animations() {
        return getDefaultToolkit().animations;
    }

    public void registerUpdateListener(KrUpdateListener updateListener) {
        updateListeners.add(updateListener);
    }

    public void unregisterUpdateListener(KrUpdateListener updateListener) {
        updateListeners.remove(updateListener);
    }

    public void update(float deltaSeconds) {
        if (canvas != null) {
            canvas.update(deltaSeconds);
        }
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < updateListeners.size(); ++i) {
            updateListeners.get(i).update(deltaSeconds);
        }
        animations.update(deltaSeconds);
    }
}
