package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.ILCD;
public class ExitTest implements LCDTest {

	@Override
	public String getName() {
		return "<Exit>";
	}

	@Override
	public void run(ILCD lcd) throws IOException {
		lcd.clear();
		lcd.stop();
		System.exit(0);
	}

}
