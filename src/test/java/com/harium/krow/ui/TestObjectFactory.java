package com.harium.krow.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.harium.krow.ui.backend.KrInputSource;
import com.harium.krow.ui.component.KrPanel;
import com.harium.krow.ui.component.KrWidget;
import com.harium.krow.ui.render.KrRenderer;
import com.harium.krow.ui.style.KrButtonStyle;
import com.harium.krow.ui.style.KrCheckboxStyle;
import com.harium.krow.ui.style.KrComboBoxStyle;
import com.harium.krow.ui.style.KrItemViewStyle;
import com.harium.krow.ui.style.KrLabelStyle;
import com.harium.krow.ui.style.KrPanelStyle;
import com.harium.krow.ui.style.KrScrollBarStyle;
import com.harium.krow.ui.style.KrTextFieldStyle;
import com.harium.krow.ui.style.KrWidgetStyle;

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

    public static KrWidgetStyle createWidgetStyle() {
        KrWidgetStyle widgetStyle = new KrWidgetStyle();
        widgetStyle.padding = new KrPadding(0, 0, 0, 0);
        widgetStyle.cursor = KrCursor.ARROW;
        return widgetStyle;
    }

    public static KrPanelStyle createPanelStyle() {
        KrPanelStyle panelStyle = new KrPanelStyle(createWidgetStyle());
        panelStyle.background = mock(Drawable.class);
        return panelStyle;
    }

    public static KrLabelStyle createLabelStyle() {
        KrLabelStyle labelStyle = new KrLabelStyle(createWidgetStyle());
        labelStyle.background = mock(Drawable.class);
        labelStyle.font = createBitmapFont();
        labelStyle.foregroundColor = Color.BLACK;
        return labelStyle;
    }

    public static KrButtonStyle createButtonStyle() {
        KrButtonStyle buttonStyle = new KrButtonStyle(createWidgetStyle());
        buttonStyle.background = mock(Drawable.class);
        buttonStyle.backgroundNormal = mock(Drawable.class);
        buttonStyle.backgroundHovered = mock(Drawable.class);
        buttonStyle.backgroundArmed = mock(Drawable.class);
        buttonStyle.font = createBitmapFont();
        buttonStyle.foregroundColor = Color.BLACK;
        buttonStyle.textShadowColor = Color.BLACK;
        buttonStyle.textShadowOffset = Vector2.Zero;
        return buttonStyle;
    }

    public static KrTextFieldStyle createTextFieldStyle() {
        KrTextFieldStyle textFieldStyle = new KrTextFieldStyle(createWidgetStyle());
        textFieldStyle.background = mock(Drawable.class);
        textFieldStyle.backgroundNormal = mock(Drawable.class);
        textFieldStyle.backgroundFocused = mock(Drawable.class);
        textFieldStyle.backgroundHovered = mock(Drawable.class);
        textFieldStyle.font = createBitmapFont();
        textFieldStyle.foregroundColor = Color.BLACK;
        textFieldStyle.caretColor = Color.BLACK;
        textFieldStyle.textShadowColor = Color.BLACK;
        return textFieldStyle;
    }

    public static KrTextFieldStyle createSpinnerStyle() {
        return createTextFieldStyle();
    }

    public static KrCheckboxStyle createCheckBoxStyle() {
        KrCheckboxStyle checkboxStyle = new KrCheckboxStyle(createWidgetStyle());
        checkboxStyle.mark = mock(Drawable.class);
        checkboxStyle.checkboxBackground = mock(Drawable.class);
        return checkboxStyle;
    }

    public static KrScrollBarStyle createScrollBarStyle() {
        KrScrollBarStyle style = new KrScrollBarStyle(createWidgetStyle());
        style.size = 5;
        style.thumb = mock(Drawable.class);
        style.track = mock(Drawable.class);
        return style;
    }

    public static KrItemViewStyle createItemViewStyle() {
        KrItemViewStyle itemViewStyle = new KrItemViewStyle(createWidgetStyle());
        itemViewStyle.gridColor = Color.BLACK;
        return itemViewStyle;
    }

    public static KrComboBoxStyle createComboBoxStyle() {
        KrComboBoxStyle comboBoxStyle = new KrComboBoxStyle(createWidgetStyle());
        return comboBoxStyle;
    }

    public static KrCanvas createCanvas() {
        return new KrCanvas(mock(KrInputSource.class), mock(KrRenderer.class), 100, 100);
    }

    public static KrWidget createWidget(String name, int x, int y, int width, int height) {
        KrWidget widget = new KrPanel();
        widget.setName(name);
        widget.setGeometry(x, y, width, height);
        return widget;
    }
}
