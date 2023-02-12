package org.ReStudios.ui.console;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;

/**
 * Custom styled document
 */
public class ConsoleDocument extends DefaultStyledDocument {

    /**
     * Component instance variable
     */
    private final ConsoleComponent component;

    /**
     * Constructor
     * @param component ConsoleComponent instance
     */
    ConsoleDocument(ConsoleComponent component) {
        this.component = component;
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        if(offs >= component.limit){
            super.remove(offs, len);
        }
    }

    /**
     * Replace text in range to new with style
     * @param start from
     * @param end to
     * @param toReplace new string
     * @param style style
     */
    public void replace(int start, int end, String toReplace, Style style){
        try {
            super.remove(start, end-start);
            insertString(start, toReplace, style);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

}