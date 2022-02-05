package com.harium.krow.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.harium.krow.ui.backend.KrInputSource;
import com.harium.krow.ui.component.KrPanel;
import com.harium.krow.ui.component.KrWidget;
import com.harium.krow.ui.event.KrEnterEvent;
import com.harium.krow.ui.event.KrEvent;
import com.harium.krow.ui.event.KrExitEvent;
import com.harium.krow.ui.event.KrFocusEvent;
import com.harium.krow.ui.event.KrKeyEvent;
import com.harium.krow.ui.event.KrMouseEvent;
import com.harium.krow.ui.event.KrScrollEvent;
import com.harium.krow.ui.render.KrRenderer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Input.Keys.TAB;
import static com.harium.krow.ui.KrToolkit.getDefaultToolkit;

/**
 * Top level container for UI elements. Delegates input events to top level components.
 */
public class KrCanvas implements KrInputSource.KrInputEventListener {

    @Getter private final KrPanel rootPanel;

    @Getter private final KrPanel overlayPanel;

    @Getter private final KrPanel tooltipPanel;

    private final KrRenderer renderer;

    @Getter private float width;

    @Getter private float height;

    @Getter private KrWidget keyboardFocusHolder;

    @Getter private KrWidget mouseFocusHolder;

    private KrWidget currentlyHoveredWidget = null;

    @Getter private final KrFocusManager focusManager;

    private final List<KrInputListener> listeners = new ArrayList<>();

    @Getter private KrTooltipManager tooltipManager;

    @Getter private KrCursorManager cursorManager;

    private KrInputSource input;

    private ArrayList<KrWidget> widgets = new ArrayList<>();

    KrCanvas(KrInputSource input, KrRenderer renderer, float width, float height) {

        this.input = input;

        this.renderer = renderer;

        rootPanel = new KrPanel();
        rootPanel.setName("canvas.root");
        rootPanel.setCanvas(this);
        rootPanel.setBackground(getDefaultToolkit().getDrawable(KrColor.TRANSPARENT));

        overlayPanel = new KrPanel();
        overlayPanel.setName("canvas.overlay");
        overlayPanel.setCanvas(this);
        overlayPanel.setBackground(getDefaultToolkit().getDrawable(KrColor.TRANSPARENT));

        tooltipPanel = new KrPanel();
        tooltipPanel.setName("canvas.tooltip");
        tooltipPanel.setCanvas(this);
        tooltipPanel.setBackground(getDefaultToolkit().getDrawable(KrColor.TRANSPARENT));

        focusManager = new KrFocusManager(rootPanel);

        tooltipManager = new KrTooltipManager(this);

        cursorManager = new KrCursorManager(this);

        rootPanel.addWidgetListener(new KrWidget.KrWidgetListener.KrAbstractWidgetListener() {
            @Override
            public void invalidated() {
                focusManager.refresh();
            }
        });

        setSize(width, height);

        input.addEventListener(this);
    }

    /**
     * Updates the size of the canvas. This is usually the size of the window.
     *
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        rootPanel.setSize(width, height);
        overlayPanel.setSize(width, height);
        renderer.setViewportSize(width, height);
    }

    /**
     * Call every frame to allow components to update themselves.
     *
     * @param deltaSeconds the time, in seconds, since the last update
     */
    public void update(float deltaSeconds) {
        widgets.clear();
        widgets.add(rootPanel);
        widgets.add(overlayPanel);
        widgets.add(tooltipPanel);
        int index = 0;
        while (index < widgets.size()) {
            KrWidget widget = widgets.get(index);
            widget.update(deltaSeconds);
            for (int i = 0; i < widget.getChildCount(); ++i) {
                widgets.add(widget.getChild(i));
            }
            index += 1;
        }

        tooltipManager.update(deltaSeconds);
    }

    /**
     * Draws the UI.
     */
    public void draw() {
        renderer.beginFrame();
        renderer.setFont(getDefaultToolkit().getSkin().getDefaultFont());
        renderer.setPen(1, getDefaultToolkit().getSkin().getColor(KrSkin.ColorKey.FOREGROUND));

        rootPanel.draw(renderer);
        overlayPanel.draw(renderer);
        tooltipPanel.draw(renderer);

        renderer.endFrame();
    }


    @Override
    public void mouseMoved(KrMouseEvent event) {
        KrWidget hoveredWidget = findWidgetAt(event.getScreenPosition());

        if (!input.isDragging() && hoveredWidget != currentlyHoveredWidget) {
            if (currentlyHoveredWidget != null) {
                dispatchEventWithoutBubbling(currentlyHoveredWidget, new KrExitEvent());
                dispatchEventWithoutBubbling(hoveredWidget, new KrEnterEvent());
            }

            currentlyHoveredWidget = hoveredWidget;
        } else {
            dispatchEvent(currentlyHoveredWidget, event);
        }
    }

    @Override
    public void mousePressed(KrMouseEvent event) {
        mouseFocusHolder = findWidgetAt(event.getScreenPosition());
        if (mouseFocusHolder != keyboardFocusHolder) {
            requestFocus(mouseFocusHolder);
        }

        dispatchEvent(mouseFocusHolder, event);
    }

    @Override
    public void mouseReleased(KrMouseEvent event) {
        dispatchEvent(mouseFocusHolder, event);
    }

    @Override
    public void mouseDoubleClicked(KrMouseEvent event) {
        dispatchEvent(mouseFocusHolder, event);
    }

    @Override
    public void keyPressed(KrKeyEvent event) {
        if (keyboardFocusHolder == null) {
            return;
        }

        if (event.getKeycode() == TAB && !keyboardFocusHolder.acceptsTabInput()) {
            if (input.isShiftDown()) {
                focusPrevious();
            } else {
                focusNext();
            }
            event.accept();
            return;
        }

        dispatchEvent(keyboardFocusHolder, event);
    }

    @Override
    public void keyReleased(KrKeyEvent event) {
        dispatchEvent(keyboardFocusHolder, event);
    }

    @Override
    public void scrolledEvent(KrScrollEvent event) {
        KrWidget destinationWidget = findWidgetAt(Gdx.input.getX(), Gdx.input.getY());
        dispatchEvent(destinationWidget, event);
    }

    public interface KrInputListener {
        void eventDispatched(KrWidget widget, KrEvent event);
    }

    private void dispatchEvent(KrWidget widget, KrEvent event) {
        try {

            if (widget == null) {
                return;
            }

            notifyEventDispatched(widget, event);

            widget.handle(event);

            while (!event.handled() && widget.getParent() != null) {
                widget = widget.getParent();
                widget.handle(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean dispatchEventWithoutBubbling(KrWidget widget, KrEvent event) {
        if (widget == null) {
            return false;
        }

        notifyEventDispatched(widget, event);
        widget.handle(event);

        return event.handled();
    }

    public KrWidget findWidgetAt(Vector2 screenPosition) {
        return findWidgetAt(screenPosition.x, screenPosition.y);
    }

    public KrWidget findWidgetAt(float x, float y) {
        KrWidget widget = findWidgetAt(overlayPanel, x, y);
        if (widget != overlayPanel) {
            return widget;
        }

        return findWidgetAt(rootPanel, x, y);
    }

    public static KrWidget findWidgetAt(KrWidget root, Vector2 screenPosition) {
        return findWidgetAt(root, screenPosition.x, screenPosition.y);
    }

    /**
     * Finds the topmost widget (a leaf in the hierarchy) whose geometry contains the requested screen coordinates.
     *
     * @param root the root of the widget hierarchy
     * @param x    the requested x position relative to the root widget
     * @param y    the requested y position relative to the root widget
     * @return the topmost widget that contains the requested coordinates
     */
    public static KrWidget findWidgetAt(KrWidget root, float x, float y) {
        ArrayList<KrWidget> childList = root.getChildren();
        for (int i = childList.size() - 1; i >= 0; --i) {
            KrWidget child = childList.get(i);
            if (getScreenGeometry(child).contains(x, y) && child.isVisible()) {
                return findWidgetAt(child, x, y);
            }
        }

        return root;
    }

    public static boolean isAncestor(KrWidget child, KrWidget ancestor) {
        if (child == ancestor) {
            return true;
        }

        if (child == null || ancestor == null) {
            return false;
        }

        KrWidget parent = child.getParent();

        while (parent != null) {
            if (parent == ancestor) {
                return true;
            }
            parent = parent.getParent();
        }

        return false;
    }

    /**
     * Returns the screen geometry of the widget. This accounts for the widget hierarchy and the relative
     * positions of the parents.
     *
     * @param widget the widget whose geometry are queried
     * @return the screen-space geometry of the queried widget
     */
    public static Rectangle getScreenGeometry(KrWidget widget) {
        Rectangle widgetGeometry = new Rectangle(widget.getX(), widget.getY(), widget.getWidth(), widget.getHeight());

        while (widget.getParent() != null) {
            widget = widget.getParent();
            widgetGeometry.setX(widget.getX() + widgetGeometry.x);
            widgetGeometry.setY(widget.getY() + widgetGeometry.y);
        }

        return widgetGeometry;
    }

    /**
     * Converts a point location from the local space of a widget to the screen space
     *
     * @param point  a position local to the widget
     * @param widget the widget which contains the requested point
     * @return the point's position in screen space
     */
    public static Vector2 convertPointToScreen(Vector2 point, KrWidget widget) {
        Rectangle widgetGeometry = getScreenGeometry(widget);
        return widgetGeometry.getPosition(new Vector2()).add(point);
    }

    public boolean clearFocus() {
        return requestFocus(null);
    }

    public boolean requestFocus(KrWidget widget) {
        if (widget != null && widget.getCanvas() != this) {
            throw new IllegalArgumentException("Cannot focus a widget that doesn't belong to this canvas.");
        }

        KrWidget oldFocusHolder = keyboardFocusHolder;
        KrWidget newFocusHolder = widget;

        if (keyboardFocusHolder != widget) {
            if (keyboardFocusHolder != null) {
                dispatchEventWithoutBubbling(keyboardFocusHolder, new KrFocusEvent(KrFocusEvent.Type.FOCUS_LOST, oldFocusHolder, newFocusHolder));
            }

            if (widget != null) {
                dispatchEventWithoutBubbling(widget, new KrFocusEvent(KrFocusEvent.Type.FOCUS_GAINED, oldFocusHolder, newFocusHolder));
            }
            keyboardFocusHolder = widget;
            return true;
        }

        return false;
    }

    public void focusNext() {
        requestFocus(focusManager.nextFocusable(keyboardFocusHolder));
    }

    public void focusPrevious() {
        requestFocus(focusManager.previousFocusable(keyboardFocusHolder));
    }

    public void addInputListener(KrInputListener listener) {
        listeners.add(listener);
    }

    public void removeListener(KrInputListener listener) {
        listeners.remove(listener);
    }

    protected void notifyEventDispatched(KrWidget widget, KrEvent event) {
        listeners.forEach(l -> l.eventDispatched(widget, event));
    }
}
