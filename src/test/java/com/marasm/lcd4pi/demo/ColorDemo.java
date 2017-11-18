package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.Color;
import com.marasm.lcd4pi.ILCD;

public class ColorDemo implements LCDTest {

	@Override
	public String getName() {
		return "Backlight";
	}

	@Override
	public void run(ILCD lcd) throws IOException {
		lcd.clear();
		lcd.setText("Color changes:");
		Util.sleep(1000);
		for (Color c : Color.values()) {
			lcd.setText(1, "Color: " + c.toString() + "      ");
			lcd.setBacklight(c);
			Util.sleep(1000);
		}
		lcd.setBacklight(Color.ON);
		lcd.clear();
		lcd.setText("Backlight Test:\nDone!");	
	}

}
