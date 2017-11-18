package com.marasm.lcd4pi;

public enum Color {
	OFF(0x00), RED(0x01), GREEN(0x02), BLUE(0x04), YELLOW(RED.getValue()
			+ GREEN.getValue()), TEAL(GREEN.getValue() + BLUE.getValue()), VIOLET(
			RED.getValue() + BLUE.getValue()), WHITE(RED.getValue()
			+ GREEN.getValue() + BLUE.getValue()), ON(WHITE.getValue());

	private final int value;

	Color(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Color getByValue(int colorValue) {
		for (Color c : values()) {
			if (c.getValue() == colorValue) {
				return c;
			}
		}
		return WHITE;
	}
}