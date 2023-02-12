package org.ReStudios.ui.console;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Mouse manager
 */
public class MouseManager extends MouseAdapter {
    /**
     * Component instance
     */
    private final ConsoleComponent component;

    /**
     * Constructor
     * @param component Component instance
     */
    MouseManager(ConsoleComponent component) {
        this.component = component;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = component.getMousePosition();
        int pos = component.viewToModel(p);
        ArrayList<Spoiler> toRemove = new ArrayList<>();
        for (Spoiler spoiler : component.spoilers) {
            if (pos >= spoiler.start && pos <= spoiler.end) {
                e.consume();
                int caret = component.getCaretPosition();
                component.doc.replace(spoiler.start, spoiler.end, spoiler.afterText, component.spoilerAfterStyle);
                int add = spoiler.afterText.length()-spoiler.beforeText.length();
                component.limit += add;
                component.setCaretPosition(caret);

                toRemove.add(spoiler);
            }
        }
        component.spoilers.removeAll(toRemove);
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = component.getMousePosition();
        int pos = component.viewToModel(p);
        for (Spoiler spoiler : component.spoilers) {
            if (pos >= spoiler.start && pos <= spoiler.end) {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            }
        }
        if(component.spoilers.size() == 0){
            component.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        }
    }
}
