package org.ReStudios.ui.console;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
@SuppressWarnings("unused")
public class ConsoleComponent extends JTextPane  {

    /**
     * Fonts for user input & system input
     */
    RFont userInput, systemInput;

    /**
     * Fonts for spoilers
     */
    RFont spoilerBefore, spoilerAfter;

    /**
     * Custom styled document for comfort
     */
    final ConsoleDocument doc;

    /**
     * AWT styles for user & system inputs
     */
    final Style userStyle, systemStyle;

    /**
     * AWT styles for spoilers
     */
    final Style spoilerBeforeStyle, spoilerAfterStyle;

    /**
     * do not create a new line when the user presses Enter
     */
    boolean ignoreNewLine;

    /**
     * User input handler
     */
    InputProcessor inputProcessor;

    /**
     * a position in the form of a character index up to which the
     * user cannot change anything. then everything after this index
     * is subject to the user, and everything before is static text
     */
    int limit;

    /**
     * Spoiler list
     */
    final ArrayList<Spoiler> spoilers;

    /**
     * Constructor
     * @param userInput Font for user input
     * @param systemInput Font for system text
     * @param spoilerBefore Font for spoiler before clicking
     * @param spoilerAfter Font fot spoiler after clicking
     */
    public ConsoleComponent(RFont userInput, RFont systemInput, RFont spoilerBefore, RFont spoilerAfter) {
        // set custom style document. for comfort
        setStyledDocument(doc = new ConsoleDocument(this));

        // set class variables
        this.userInput = userInput;
        this.systemInput = systemInput;
        this.spoilerBefore = spoilerBefore;
        this.spoilerAfter = spoilerAfter;

        // creating styles
        this.userStyle = doc.addStyle("user", null);
        this.systemStyle = doc.addStyle("sys", null);
        this.spoilerBeforeStyle = doc.addStyle("spoilerbefore", null);
        this.spoilerAfterStyle = doc.addStyle("spoilderafter", null);

        // apply fonts for styles
        updateStyles();

        // default values
        ignoreNewLine = false;
        spoilers = new ArrayList<>();
        limit = 0;
        inputProcessor = new IgnoreInputProcessor();

        // setup events etc.
        setupEventsAndManagers();
    }

    /**
     * Constructor with default parameters
     */
    public ConsoleComponent() {
        this(new RFont(new Font("Arial", Font.PLAIN, 16), Color.MAGENTA, new Color(0,0,0,0)),
                new RFont(new Font("Arial", Font.PLAIN, 16), Color.BLACK, new Color(0,0,0,0)),
                new RFont(new Font("Arial", Font.PLAIN, 16), Color.BLACK, new Color(150,150,150)),
                new RFont(new Font("Arial", Font.PLAIN, 16), Color.BLACK, new Color(0,0,0,0))
        );
    }


    boolean accessToPaste(){
        // if the cursor position is less than the minimum allowed position
        if(getCaretPosition() < limit){
            return false;
        }

        // if anything is highlighted
        if(getSelectedText() !=null){
            // if both the position of the beginning of the selection and the
            // end are within the boundaries allowed for change, then we are
            // allowed to replace the text
            return getSelectionStart() >= limit && getSelectionEnd() >= limit;
        }
        return true;
    }
    private void setupEventsAndManagers() {
        // text component keyboard listener
        addKeyListener(new KeyManager(this));

        // mouse listener (spoiler hover & spoiler functional)
        MouseAdapter ma = new MouseManager(this);
        addMouseMotionListener(ma);
        addMouseListener(ma);

        // transfer data (permission and prohibition to insert text in certain places)
        setTransferHandler(new TextTransferHandler(this));
    }

    /**
     * everything is the same as a regular spoiler, only it creates a new
     * line. then the beige user will be forced to write on a new line
     * @param before Text before clicking
     * @param after Text after clicking
     */
    public void writeClickableSpoilerNewLine(String before, String after){
        // write spoiler
        writeClickableSpoiler(before, after);

        // create new line
        writeSystemText("\n");
    }

    /**
     * output spoiler to console. spoiler - text with a special font and
     * color, after clicking on which it is replaced by another text, which
     * is also with its own font and color
     * @param before Text before clicking
     * @param after Text after clicking
     */
    public void writeClickableSpoiler(String before, String after){
        // creating spoiler record
        Spoiler spoiler = new Spoiler(before, after, doc.getLength(), doc.getLength() + before.length());
        try {
            // inserting spoiler to text component
            doc.insertString(doc.getLength(), before, getSpoilerBeforeStyle());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        // saving spoiler to list
        spoilers.add(spoiler);
    }

    /**
     * print the system text to the console. system text - text, with a separate font and colors, which cannot be erased by the user
     * @param message text
     */
    public void writeSystemText(String message){
        try {
            // inserting system text
            doc.insertString(doc.getLength(), message,getSystemStyle());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

        // prevent the user from changing this text
        limit = doc.getLength();

        // update caret position
        setCaretPosition(limit);
    }
    private void updateStyles(){
        // apply
        userInput.support(userStyle);
        systemInput.support(systemStyle);
        spoilerBefore.support(spoilerBeforeStyle);
        spoilerAfter.support(spoilerAfterStyle);
    }

    /*
    getters & setters
     */
    Style getUserStyle(){
        return userStyle;
    }
    private Style getSystemStyle(){
        return systemStyle;
    }
    public Style getSpoilerBeforeStyle() {
        return spoilerBeforeStyle;
    }

    public Style getSpoilerAfterStyle() {
        return spoilerAfterStyle;
    }

    public ConsoleComponent setUserInput(RFont userInput) {
        this.userInput = userInput;
        updateStyles();
        return this;
    }

    public ConsoleComponent setSystemInput(RFont systemInput) {
        this.systemInput = systemInput;
        updateStyles();
        return this;
    }

    public ConsoleComponent setSpoilerBefore(RFont spoilerBefore) {
        this.spoilerBefore = spoilerBefore;
        updateStyles();
        return this;
    }

    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public ConsoleComponent setInputProcessor(InputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
        return this;
    }

    public boolean isIgnoreNewLine() {
        return ignoreNewLine;
    }

    public ConsoleComponent setIgnoreNewLine(boolean ignoreNewLine) {
        this.ignoreNewLine = ignoreNewLine;
        return this;
    }

    public ConsoleComponent setSpoilerAfter(RFont spoilerAfter) {
        this.spoilerAfter = spoilerAfter;
        updateStyles();
        return this;
    }
}
