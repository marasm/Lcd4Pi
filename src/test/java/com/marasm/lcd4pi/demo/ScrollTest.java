package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.LCD;
import com.marasm.lcd4pi.RealLCD.Direction;

public class ScrollTest implements LCDTest {

	@Override
	public String getName() {
		return "Scroller";
	}

	@Override
	public void run(LCD lcd) throws IOException {
		String message = "Running scroller. Be patient!\nBouncing this scroller once.";
		lcd.setText(message);
		for (int i = 0; i < 24; i++) {
			Util.sleep(100);
			lcd.scrollDisplay(Direction.LEFT);
		}
		for (int i = 0; i < 24; i++) {
			Util.sleep(100);
			lcd.scrollDisplay(Direction.RIGHT);
		}
		lcd.setText(1, "Done!             ");
	}
}
