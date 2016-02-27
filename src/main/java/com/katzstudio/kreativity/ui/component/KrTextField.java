package com.katzstudio.kreativity.ui.component;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.katzstudio.kreativity.ui.AlignmentTool;
import com.katzstudio.kreativity.ui.FontMetrics;
import com.katzstudio.kreativity.ui.KrAlignment;
import com.katzstudio.kreativity.ui.KrColor;
import com.katzstudio.kreativity.ui.KrPadding;
import com.katzstudio.kreativity.ui.KreativitySkin;
import com.katzstudio.kreativity.ui.event.KrKeyEvent;
import com.katzstudio.kreativity.ui.event.KrMouseEvent;
import com.katzstudio.kreativity.ui.render.KrColorBrush;
import com.katzstudio.kreativity.ui.render.KrDrawableBrush;
import com.katzstudio.kreativity.ui.render.KrPen;
import com.katzstudio.kreativity.ui.render.KrRenderer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.katzstudio.kreativity.ui.FontMetrics.metrics;

/**
 * The {@link KrTextField} class provides a widget that can display and edit plain text.
 */
public class KrTextField extends KrWidget {

    public static final int CARET_HEIGHT = 14;

    public static final int CARET_TOP_OFFSET = 3;

    @Getter @Setter private Style style;

    private final TextDocument textDocument;

    private int textOffset;

    public KrTextField() {
        textDocument = new TextDocument();
        setStyle(KreativitySkin.instance().getTextFieldStyle());
        setPadding(new KrPadding(1, 0, 4, 0));
    }

    @Override
    protected boolean mousePressedEvent(KrMouseEvent event) {
        super.mousePressedEvent(event);
        return requestFocus();
    }

    @Override
    protected boolean keyPressedEvent(KrKeyEvent event) {
        super.keyPressedEvent(event);

        System.out.println("event.getValue() = " + event.getValue());

        if (!event.getValue().equals("")) {
            textDocument.insertText(event.getValue());
        }

        if (event.getKeycode() == Input.Keys.BACKSPACE) {
            textDocument.deleteCharBeforeCaret();
        }

        if (event.getKeycode() == Input.Keys.FORWARD_DEL) {
            textDocument.deleteCharAfterCaret();
        }

        if (event.getKeycode() == Input.Keys.LEFT) {
            if (event.isCtrlDown()) {
                textDocument.moveCaretNextWord();
            } else {
                textDocument.moveCaretLeft();
            }
        }

        if (event.getKeycode() == Input.Keys.RIGHT) {
            if (event.isCtrlDown()) {
                textDocument.moveCaretPreviousWord();
            } else {
                textDocument.moveCaretRight();
            }
        }

        if (event.getKeycode() == Input.Keys.HOME) {
            textDocument.moveCaretHome();
        }

        if (event.getKeycode() == Input.Keys.END) {
            textDocument.moveCaretEnd();
        }

        return true;
    }

    @Override
    public Vector2 getSelfPreferredSize() {
        Rectangle textBounds = metrics(style.font).bounds(textDocument.getText());
        return expandSizeWithPadding(textBounds.getSize(new Vector2()), getPadding());
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {
        renderer.beginClip(getX(), getY(), getWidth(), getHeight());

        Drawable background = style.backgroundNormal;
        if (isFocused()) {
            background = style.backgroundFocused;
        }

        renderer.setBrush(new KrDrawableBrush(background));
        renderer.fillRect(getX(), getY(), getWidth(), getHeight());

        computeTextOffset();

        FontMetrics metrics = metrics(style.font);
        String text = textDocument.getText();
        Rectangle textBounds = metrics.bounds(text);
        Vector2 textPosition = AlignmentTool.alignRectangles(textBounds, getGeometry(), KrAlignment.MIDDLE_LEFT);
        textPosition.x += getPadding().left + textOffset;
        textPosition.y -= 1;

        // render selection
//        if (textDocument.hasSelection()) {
        if (true) {
            Rectangle selectionRect = getSelectionRect();
            renderer.setBrush(new KrColorBrush(KrColor.rgb(0x363d42)));
            renderer.fillRect(selectionRect);
        }

        // render text
        renderer.drawText(text, textPosition);

        // render caret
        int caretPosition = textDocument.getCaretPosition();
        float caretX = textPosition.x + metrics.bounds(text.substring(0, caretPosition)).getWidth() + 1;
        renderer.setPen(new KrPen(1, style.caretColor));
        renderer.drawLine(caretX, getY() + CARET_TOP_OFFSET, caretX, getY() + CARET_TOP_OFFSET + CARET_HEIGHT);

        renderer.endClip();
    }

    private Rectangle getSelectionRect() {
        // TODO(alex): proper implementation pls
//        float selectionStartX = textPosition.x + metrics.bounds(text.substring(0, textDocument.getSelectionBegin())).getWidth();
//        float selectionEndX = textPosition.y + metrics.bounds(text.substring(0, textDocument.selectionEnd)).getWidth();

        return new Rectangle(getX(), getY() + CARET_TOP_OFFSET, 45, CARET_HEIGHT);
    }

    private void computeTextOffset() {
        Rectangle visibleTextArea; // to be determined
        float caretXPosition = getX() + textOffset + metrics(style.font).bounds(textDocument.getTextBeforeCaret()).width;
    }

    public static class TextDocument {

        @Getter private String text = "";

        @Getter private int caretPosition = 0;

        @Getter private int selectionBegin = 0;

        @Getter private int selectionEnd = 0;

        private boolean isSelecting = false;

        public void setText(String text) {
            this.text = text;
            this.caretPosition = 0;
        }

        public void insertText(char charater) {
            insertText(String.valueOf(charater));
        }

        public void insertText(String text) {
            deleteSelection();
            this.text = this.text.substring(0, caretPosition) + text + this.text.substring(caretPosition);
            caretPosition += text.length();
        }

        public void deleteSelection() {
            if (!hasSelection()) {
                return;
            }

            text = text.substring(0, selectionBegin) + text.substring(selectionEnd);
            caretPosition = selectionBegin;
            clearSelection();
        }

        public boolean hasSelection() {
            return selectionBegin != selectionEnd;
        }

        public void clearSelection() {
            selectionBegin = selectionEnd = 0;
        }

        public void deleteCharBeforeCaret() {
            if (hasSelection()) {
                deleteSelection();
                return;
            }

            if (caretPosition > 0) {
                text = text.substring(0, caretPosition - 1) + text.substring(caretPosition);
                caretPosition = caretPosition - 1;
            }
        }

        public void deleteCharAfterCaret() {
            if (hasSelection()) {
                deleteSelection();
                return;
            }

            if (caretPosition < text.length()) {
                text = text.substring(0, caretPosition) + text.substring(caretPosition + 1);
            }
        }

        public void moveCaretLeft() {
            caretPosition = Math.max(0, caretPosition - 1);
            syncSelectionFromCursor();
        }

        public void moveCaretRight() {
            caretPosition = Math.min(text.length(), caretPosition + 1);
            syncSelectionFromCursor();
        }

        public void moveCaretHome() {
            caretPosition = 0;
            syncSelectionFromCursor();
        }

        public void moveCaretEnd() {
            caretPosition = text.length();
            syncSelectionFromCursor();
        }

        public void moveCaretNextWord() {
            caretPosition = findNextWord(caretPosition, text);
            syncSelectionFromCursor();
        }

        public void moveCaretPreviousWord() {
            caretPosition = findPreviousWord(caretPosition, text);
            syncSelectionFromCursor();
        }

        public void beginSelection() {
            selectionBegin = caretPosition;
            isSelecting = true;
        }

        public void endSelection() {
            isSelecting = false;
        }

        public void setSelection(int begin, int end) {
            int min = Math.min(begin, end);
            int max = Math.max(begin, end);

            begin = Math.max(0, min);
            end = Math.min(text.length(), max);

            selectionBegin = begin;
            selectionEnd = end;
            caretPosition = selectionEnd;
        }

        /**
         * Called whenever the caret position changes, to make sure the end of the selection is at
         * the caret position. If no selection is taking place, this method does nothing.
         */
        private void syncSelectionFromCursor() {
            if (isSelecting) {
                selectionEnd = caretPosition;
            }
        }

        public void setCaretPosition(int caretPosition) {
            this.caretPosition = Math.max(0, Math.min(caretPosition, text.length()));
        }

        public String getTextBeforeCaret() {
            return text.substring(0, caretPosition);
        }

        public void undo() {
        }

        public void redo() {
        }

        private static int findNextWord(int startPosition, String text) {
            boolean foundWhitespace = false;
            boolean done = false;
            while (startPosition < text.length() && !done) {
                if (text.charAt(startPosition) != ' ' && foundWhitespace) {
                    done = true;
                } else {
                    foundWhitespace = text.charAt(startPosition) == ' ';
                    startPosition += 1;
                }
            }
            return startPosition;
        }

        private static int findPreviousWord(int startPosition, String text) {
            boolean foundNonWhitespace = false;
            boolean done = false;
            while (startPosition > 0 && !done) {
                if (text.charAt(startPosition - 1) == ' ' && foundNonWhitespace) {
                    done = true;
                } else {
                    foundNonWhitespace = text.charAt(startPosition - 1) != ' ';
                    startPosition -= 1;
                }
            }
            return startPosition;
        }
    }

    @AllArgsConstructor
    public static class Style {

        public Drawable backgroundNormal;

        public Drawable backgroundHovered;

        public Drawable backgroundFocused;

        public BitmapFont font;

        public Color foregroundColor;

        public Color caretColor;
    }
}
