package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.LCD;

public class HelloWorldTest implements LCDTest {

	@Override
	public String getName() {
		return "Hello World";
	}

	@Override
	public void run(LCD lcd) throws IOException {
		lcd.clear();
		lcd.setText("Hello World!\nDone!");
		
	}

}
