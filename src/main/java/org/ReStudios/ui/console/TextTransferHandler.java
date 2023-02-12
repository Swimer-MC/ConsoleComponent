package org.ReStudios.ui.console;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Text transfer handler
 */
public class TextTransferHandler extends TransferHandler {
    /**
     * Component instance
     */
    private final ConsoleComponent component;

    /**
     * Constructor
     * @param component Component instance
     */
    TextTransferHandler(ConsoleComponent component) {
        super("text");
        this.component = component;
    }
    public void exportToClipboard(JComponent comp, Clipboard clipboard, int action) throws IllegalStateException {
        if (comp instanceof ConsoleComponent) {
            ConsoleComponent text = (ConsoleComponent)comp;
            int selStart = text.getSelectionStart();
            int selEnd = text.getSelectionEnd();
            if (selStart != selEnd) {
                try {
                    Document doc = text.getDocument();
                    String srcData = doc.getText(selStart, selEnd - selStart);
                    StringSelection contents =new StringSelection(srcData);

                    clipboard.setContents(contents, null);

                    if (action == TransferHandler.MOVE) {
                        if(selStart >= text.limit && selEnd >= text.limit)doc.remove(selStart, selEnd - selStart);
                    }
                } catch (BadLocationException ignored) {}
            }
        }
    }
    @Override
    public boolean canImport(TransferSupport support) {
        return component.accessToPaste() && support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }
    @Override
    public boolean importData(TransferSupport support) {
        if(!component.accessToPaste())return false;
        Transferable transferable = support.getTransferable();
        if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                component.replaceSelection(data);
                return true;
            } catch (UnsupportedFlavorException | IOException e) {
                return false;
            }
        }
        return false;
    }
}
