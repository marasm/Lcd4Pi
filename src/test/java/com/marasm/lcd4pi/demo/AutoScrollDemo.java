package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.ILCD;

public class AutoScrollDemo implements LCDTest {

	@Override
	public String getName() {
		return "AutoScroll";
	}

	@Override
	public void run(ILCD lcd) throws IOException {
		lcd.clear();
		lcd.setText("AutoScroll off:\n");
		lcd.setCursorPosition(1, 0);
		lcd.setAutoScrollEnabled(false);
		for (int i = 0; i < 24; i++) {
			Util.sleep(200);
			lcd.setText(1, "" + (i % 10));
		}

		lcd.clear();
		lcd.setText("AutoScroll on:\n");
		lcd.setAutoScrollEnabled(true);
		lcd.setCursorPosition(1, 14);
		for (int i = 0; i < 24; i++) {
			Util.sleep(200);
			lcd.setText(1, "" + (i % 10));
		}
		lcd.setAutoScrollEnabled(false);
		lcd.setCursorPosition(0, 0);
		lcd.clear();
		lcd.setText("AutoScroll:\nDone!");
	}

}
