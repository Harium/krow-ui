package com.katzstudio.kreativity.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Clipboard;
import com.katzstudio.kreativity.ui.component.*;
import com.katzstudio.kreativity.ui.render.KrRenderer;

import static org.mockito.Mockito.mock;

/**
 * Factory for various test objects
 */
public class TestObjectFactory {

    private TestObjectFactory() {
    }

    public static BitmapFont createBitmapFont() {
        return mock(BitmapFont.class);
    }

    public static KrPanel.Style createPanelStyle() {
        return new KrPanel.Style(mock(Drawable.class));
    }

    public static KrLabel.Style createLabelStyle() {
        return new KrLabel.Style(mock(Drawable.class),
                createBitmapFont(),
                Color.BLACK);
    }

    public static KrButton.Style createButtonStyle() {
        return new KrButton.Style(mock(Drawable.class),
                mock(Drawable.class),
                mock(Drawable.class),
                createBitmapFont(),
                Color.BLACK,
                Vector2.Zero,
                Color.BLACK);
    }

    public static KrTextField.Style createTextFieldStyle() {
        return new KrTextField.Style(
                mock(Drawable.class),
                mock(Drawable.class),
                mock(Drawable.class),
                createBitmapFont(),
                Color.BLACK,
                Color.BLACK,
                Color.BLACK);
    }

    public static KrCheckbox.Style createCheckBoxStyle() {
        return new KrCheckbox.Style(
                mock(Drawable.class),
                mock(Drawable.class));
    }

    public static KrCanvas createCanvas() {
        return new KrCanvas(mock(KrRenderer.class), mock(Clipboard.class), 100, 100);
    }

    public static KrWidget createWidget(String name, int x, int y, int width, int height) {
        KrWidget widgetA = new KrWidget(name);
        widgetA.setGeometry(x, y, width, height);
        return widgetA;
    }
}
