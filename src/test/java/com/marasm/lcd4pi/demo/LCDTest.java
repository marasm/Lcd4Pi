package com.marasm.lcd4pi.demo;

import java.io.IOException;

import com.marasm.lcd4pi.ILCD;

public interface LCDTest {
	public String getName();
	public void run(ILCD lcd) throws IOException;
}
