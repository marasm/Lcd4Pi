package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.LCD;

public class DisplayDemo implements LCDTest {

	@Override
	public String getName() {
		return "Display";
	}

	@Override
	public void run(LCD lcd) throws IOException {
		lcd.clear();
		lcd.setText("Turning off/on\ndisplay 10 times!");
		Util.sleep(1000);
		for (int i = 0; i < 10; i++) {
			lcd.setDisplayEnabled(false);
			Util.sleep(200);
			lcd.setDisplayEnabled(true);
			Util.sleep(400);
		}
		lcd.clear();
		lcd.setText("Display Test:\nDone!");
	}

}
