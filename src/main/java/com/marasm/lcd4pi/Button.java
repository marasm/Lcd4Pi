package com.marasm.lcd4pi;

import java.util.HashSet;
import java.util.Set;

public enum Button {
	SELECT(0), RIGHT(1), DOWN(2), UP(3), LEFT(4);

	// Port expander input pin definition
	private final int pin;

	Button(int pin) {
		this.pin = pin;
	}

	/**
	 * The pin corresponding to the button.
	 * 
	 * @return the pin of the button.
	 */
	public int getPin() {
		return pin;
	}

	/**
	 * Checks if a button is pressed, given an input mask.
	 * 
	 * @param mask
	 *            the input mask.
	 * @return true if the button is pressed, false otherwise.
	 * 
	 * @see RealLCD#buttonsPressedBitmask()
	 */
	public boolean isButtonPressed(int mask) {
		return ((mask >> getPin()) & 1) > 0;
	}

	/**
	 * Returns a set of the buttons that are pressed, according to the input
	 * mask.
	 * 
	 * @param mask
	 *            the input mask.
	 * @return a set of the buttons pressed.
	 * 
	 * @see RealLCD#buttonsPressedBitmask()
	 */
	public static Set<Button> getButtonsPressed(int mask) {
		Set<Button> buttons = new HashSet<Button>();
		for (Button button : values()) {
			if (button.isButtonPressed(mask)) {
				buttons.add(button);
			}
		}
		return buttons;
	}
}