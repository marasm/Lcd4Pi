package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.LCD;

public class CursorDemo implements LCDTest {

	@Override
	public String getName() {
		return "Cursor";
	}

	@Override
	public void run(LCD lcd) throws IOException {
		lcd.clear();
		lcd.setText("Cursor:\n");
		lcd.setCursorPosition(1, 0);
		lcd.setCursorEnabled(true);
		Util.sleep(2000);
		lcd.clear();
		lcd.setText("Blinking Cursor:\n");	
		lcd.setCursorPosition(1, 0);
		lcd.setBlinkEnabled(true);
		Util.sleep(4000);
		lcd.clear();
		lcd.setText("Moving Cursor:\n");
		lcd.setCursorPosition(1, 0);
		for (int i = 0; i < 16; i++) {
			Util.sleep(500);
			lcd.setCursorPosition(1, i);			
		}
		lcd.setBlinkEnabled(false);
		lcd.setCursorEnabled(false);
		lcd.setText(1, "Done!");
	}

}
