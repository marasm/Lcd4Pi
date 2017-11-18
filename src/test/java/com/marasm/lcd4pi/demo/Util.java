package com.marasm.lcd4pi.demo;

public class Util {
	final static void sleep(int sleep) {
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			// Don't care
		}
	}
}
