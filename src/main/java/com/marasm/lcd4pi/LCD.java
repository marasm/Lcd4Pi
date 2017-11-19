package com.marasm.lcd4pi;

import java.io.IOException;

import com.marasm.lcd4pi.RealLCD.Direction;
import com.marasm.logger.AppLogger;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.pi4j.system.SystemInfo;

public interface LCD {

	void setText(String s) throws IOException;

	void setText(int row, String string) throws IOException;

	void setCursorPosition(int row, int column) throws IOException;

	void stop() throws IOException;

	void clear() throws IOException;

	void home() throws IOException;

	void setCursorEnabled(boolean enable) throws IOException;

	boolean isCursorEnabled();

	void setDisplayEnabled(boolean enable) throws IOException;

	boolean isDisplayEnabled();

	void setBlinkEnabled(boolean enable) throws IOException;

	boolean isBlinkEnabled();

	void setBacklight(Color color) throws IOException;

	Color getBacklight() throws IOException;

	void scrollDisplay(Direction direction) throws IOException;

	void setTextFlowDirection(Direction direction) throws IOException;

	void setAutoScrollEnabled(boolean enable) throws IOException;

	boolean isAutoScrollEnabled();

	int buttonsPressedBitmask() throws IOException;
	
	
	public static boolean isRunningOnPi()
	{
	  String hardware = null;
    try
    {
      hardware = SystemInfo.getHardware();
      AppLogger.debug("Hardware: " + hardware);
    }
    catch(Exception e)
    {
      AppLogger.warn("Error getting hardware! Assuming that this is not a PI");
    }
    return hardware != null && hardware.contains("BCM"); //BCM2708...
	}
	
	
	public static LCD getInstance() throws IOException, UnsupportedBusNumberException
	{
	  if (isRunningOnPi())
	    return new RealLCD();
	  else
	    return new MockupLCD();
	}
	
	public static LCD getInstance(int inBus, int inAddr) 
	  throws IOException, UnsupportedBusNumberException
	{
	  if (isRunningOnPi())
	    return new RealLCD(inBus, inAddr);
	  else
	    return new MockupLCD(inBus, inAddr);
	}

}