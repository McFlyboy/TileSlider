package com.nyhammer.tp;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Timer{
	private double startTime;
	public Timer(){
		startTime = getAbsoulteTime();
	}
	public double getTime(){
		return getAbsoulteTime() - startTime;
	}
	public static double getAbsoulteTime(){
		return glfwGetTime();
	}
}