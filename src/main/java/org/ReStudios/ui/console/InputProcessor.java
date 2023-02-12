package org.ReStudios.ui.console;

public interface InputProcessor {
    /**
     * Called when user pressed Enter
     * @param userInput full user input
     */
    void accept(String userInput);

    /**
     * Called when user pressed any key
     * @param character pressed key
     */
    void acceptSingleInput(char character);
}
