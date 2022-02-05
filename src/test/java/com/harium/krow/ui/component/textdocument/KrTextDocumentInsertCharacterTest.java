package com.harium.krow.ui.component.textdocument;

import com.harium.krow.ui.component.KrTextDocument;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for {@link KrTextDocument} related to inserting text
 */
@RunWith(Parameterized.class)
@RequiredArgsConstructor
public class KrTextDocumentInsertCharacterTest {

    private final String originalText;

    private final int caretPosition;

    private final String textToInsert;

    private final String expectedResult;

    @Test
    public void testInsertCharacter() throws Exception {
        KrTextDocument document = new KrTextDocument();
        document.setText(originalText);
        document.setCaretPosition(caretPosition);
        document.insertText(textToInsert);

        assertThat(document.getText(), is(expectedResult));
    }

    @Parameterized.Parameters(name = "Insert \"{2}\" in \"{0}\" at {1} = \"{3}\"")
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"", 0, "", ""},
                {"0123456789", 0, "x", "x0123456789"},
                {"0123456789", -10, "x", "x0123456789"},
                {"0123456789", 1, "x", "0x123456789"},
                {"0123456789", 1, "", "0123456789"},
                {"0123456789", 5, "x", "01234x56789"},
                {"0123456789", 9, "x", "012345678x9"},
                {"0123456789", 10, "x", "0123456789x"},
                {"0123456789", 100, "x", "0123456789x"},
                {"0123456789", 1, "test", "0test123456789"},
                {"", 0, "x", "x"},
                {"", 0, "Test", "Test"},
                {"", -10, "Test", "Test"},
                {"", 10, "Test", "Test"},
        });
    }
}
