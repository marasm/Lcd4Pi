package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.LCD;

public interface LCDTest {
	public String getName();
	public void run(LCD lcd) throws IOException;
}
