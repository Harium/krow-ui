package com.harium.krow.ui.component;

import com.harium.krow.ui.KrCanvas;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static com.harium.krow.ui.TestObjectFactory.createCanvas;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link KrWidget}
 */
public class KrWidgetTest {
    @Test
    public void testAddWidget() throws Exception {
        KrWidget parentWidget = new KrWidget("parent");
        KrWidget childWidget = new KrWidget("child");

        parentWidget.add(childWidget);

        assertThat(childWidget.getParent(), is(parentWidget));
        assertThat(parentWidget.getChildren().size(), is(1));
        assertThat(parentWidget.getChildren().get(0), is(childWidget));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildAlreadyParented() throws Exception {
        KrWidget root = new KrWidget();
        KrWidget firstChild = new KrWidget();
        KrWidget secondChild = new KrWidget();

        firstChild.add(secondChild);

        root.add(secondChild);
    }

    @Test
    public void testGetCanvas() throws Exception {
        KrWidget widget = new KrWidget();

        assertThat(widget.getCanvas(), CoreMatchers.is((KrCanvas) null));

        KrCanvas canvas = createCanvas();
        canvas.getRootPanel().add(widget);
        assertThat(widget.getCanvas(), is(canvas));
    }

    @Test
    public void testRemoveFocusedChild() throws Exception {
        KrCanvas canvas = createCanvas();

        KrWidget parent = new KrWidget();
        KrWidget child = new KrWidget();

        parent.add(child);
        canvas.getRootPanel().add(parent);

        assertThat(child.requestFocus(), is(true));
        assertThat(child.isFocused(), is(true));

        parent.remove(child);
        assertThat(child.isFocused(), is(false));
    }
}
