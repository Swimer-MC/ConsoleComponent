package org.ReStudios.ui.console;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.awt.Font;

/**
 * Font with foreground & background colors
 */
@SuppressWarnings("unused")
public class RFont {
    /**
     * Font
     */
    private final Font font;

    /**
     * Text color
     */
    private final Color foreground;

    /**
     * Background text color
     */
    private final Color background;

    /**
     * Constructor
     * @param font AWT Font
     * @param foreground text color
     * @param background background text color
     */
    public RFont(Font font, Color foreground, Color background) {
        this.font = font;
        this.foreground = foreground;
        this.background = background;
    }

    /**
     * Get font
     * @return AWT Font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Get text color
     * @return foreground awt color
     */
    public Color getForeground() {
        return foreground;
    }

    /**
     * get background text color
     * @return background awt color
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Pass properties to text style for text components
     * @param style style
     * @see javax.swing.text.Style
     */
    public void support(MutableAttributeSet style){
        StyleConstants.setFontFamily(style, font.getFamily());
        StyleConstants.setFontSize(style, font.getSize());
        StyleConstants.setItalic(style, (font.getStyle() & Font.ITALIC) != 0);
        StyleConstants.setBold(style, (font.getStyle() & Font.BOLD) != 0);
        StyleConstants.setForeground(style, foreground);
        StyleConstants.setBackground(style, background);
    }

}