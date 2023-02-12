package org.ReStudios.ui.console;

/**
 * Spoiler record class
 */
@SuppressWarnings("unused")
public class Spoiler {
    /**
     * Spoiler text
     */
    final String beforeText;

    /**
     * Spoiler content
     */
    final String afterText;

    /**
     * Start spoiler position
     */
    final int start;

    /**
     * End spoiler position
     */
    final int end;

    /**
     * Constructor
     * @param beforeText visible text
     * @param afterText text after click
     * @param start start position
     * @param end end position
     */
    Spoiler(String beforeText, String afterText, int start, int end) {
        this.beforeText = beforeText;
        this.afterText = afterText;
        this.start = start;
        this.end = end;
    }

    /**
     * Get spoiler text
     * @return spoiler text
     */
    public String getBeforeText() {
        return beforeText;
    }

    /**
     * Get spoiler content text
     * @return spoiler content text
     */
    public String getAfterText() {
        return afterText;
    }

    /**
     * Get start location
     * @return start index
     */
    public int getStart() {
        return start;
    }

    /**
     * Get end location
     * @return end index
     */
    public int getEnd() {
        return end;
    }
}
