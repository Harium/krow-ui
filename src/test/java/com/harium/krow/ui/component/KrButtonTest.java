package com.harium.krow.ui.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.TestObjectFactory;
import com.harium.krow.ui.TestUtils;
import com.harium.krow.ui.event.KrEnterEvent;
import com.harium.krow.ui.event.KrExitEvent;
import com.harium.krow.ui.event.KrMouseEvent;
import com.harium.krow.ui.render.KrRenderer;
import com.harium.krow.ui.style.KrButtonStyle;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link KrButton}
 */
public class KrButtonTest {

    private KrButton button;

    private KrButtonStyle buttonStyle;

    private KrRenderer renderer;

    @Before
    public void setUp() {
        TestUtils.initializeToolkit();

        buttonStyle = TestObjectFactory.createButtonStyle();
        when(KrToolkit.getDefaultToolkit().getSkin().getStyle(KrButton.class)).thenReturn(buttonStyle);

        button = new KrButton("button");
        renderer = mock(KrRenderer.class);
    }

    @Test
    public void testDrawSimple() throws Exception {
        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundNormal);
        verify(renderer).drawTextWithShadow(eq("button"), any(Vector2.class), eq(Vector2.Zero), eq(Color.BLACK));
    }

    @Test
    public void testDrawPressed() throws Exception {
        button.handle(new KrMouseEvent(KrMouseEvent.Type.PRESSED, KrMouseEvent.Button.LEFT, null, null));
        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundArmed);
    }

    @Test
    public void testDrawHovered() throws Exception {
        button.handle(new KrEnterEvent());
        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundNormal);
    }

    @Test
    public void testDrawAfterExit() {
        button.handle(new KrEnterEvent());
        button.handle(new KrMouseEvent(KrMouseEvent.Type.PRESSED, KrMouseEvent.Button.LEFT, null, null));
        button.handle(new KrExitEvent());

        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundNormal);
    }

    @Test
    public void testDrawAfterClick() throws Exception {
        button.handle(new KrEnterEvent());
        button.handle(new KrMouseEvent(KrMouseEvent.Type.PRESSED, KrMouseEvent.Button.LEFT, null, null));
        button.handle(new KrMouseEvent(KrMouseEvent.Type.RELEASED, KrMouseEvent.Button.LEFT, null, null));

        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundNormal);
    }

    @Test
    public void testClickEvent() throws Exception {
        KrButton.KrButtonListener listener = mock(KrButton.KrButtonListener.class);
        button.addListener(listener);
        button.handle(new KrMouseEvent(KrMouseEvent.Type.PRESSED, KrMouseEvent.Button.LEFT, null, null));
        button.handle(new KrMouseEvent(KrMouseEvent.Type.RELEASED, KrMouseEvent.Button.LEFT, null, null));
        verify(listener).clicked();
    }

    @Test
    public void testMouseLeaveWhileArmed() throws Exception {
        button.handle(new KrMouseEvent(KrMouseEvent.Type.PRESSED, KrMouseEvent.Button.LEFT, null, null));
        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundArmed);
        reset(renderer);

        button.handle(new KrExitEvent());
        button.drawSelf(renderer);
        verifyRendererCalledWithDrawable(buttonStyle.backgroundNormal);
    }

    public void verifyRendererCalledWithDrawable(Drawable drawable) {
        verify(renderer).setBrush(eq(drawable));
        verify(renderer).fillRect(any(Float.class), any(Float.class), any(Float.class), any(Float.class));
    }
}
