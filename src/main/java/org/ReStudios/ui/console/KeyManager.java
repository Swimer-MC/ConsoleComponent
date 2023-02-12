package org.ReStudios.ui.console;

import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key manager
 */
public class KeyManager implements KeyListener {
    /**
     * Component instance
     */
    private final ConsoleComponent component;

    /**
     * Constructor
     * @param component Component instance
     */
    KeyManager(ConsoleComponent component) {
        this.component = component;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(component.getSelectedText() != null){
            int st = component.getSelectionStart();
            int nd = component.getSelectionEnd();
            if(st < component.limit || nd < component.limit){
                e.consume();
            }
        }


        if(component.getCaretPosition() < component.limit){
            component.setCaretPosition(component.doc.getLength());
        }

        if(!e.isConsumed()){
            SwingUtilities.invokeLater(() -> component.doc.setCharacterAttributes(component.limit, component.doc.getLength() - component.limit, component.getUserStyle(), false));
            component.inputProcessor.acceptSingleInput(e.getKeyChar());
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(component.getCaretPosition() <= component.limit && (e.getKeyChar() == '\b' || e.getKeyChar() == '\n')){
            e.consume();
            return;
        }
        if(component.getCaretPosition()< component.limit && e.getKeyCode() == 127) { // delete button
            e.consume();
            return; //
        }
        if(e.getKeyChar() == '\n'){
            if(component.ignoreNewLine){
                e.consume();
            }
            try {
                component.inputProcessor.accept(component.doc.getText(component.limit, component.doc.getLength()-component.limit));
                component.limit = component.doc.getLength();
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
