package com.nyhammer.tp.ui;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.nyhammer.tp.graphics.Render;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class GameWindow{
	private static long monitor;
	private static GLFWVidMode vidmode;
	private static long window;
	private static int width, height;
	public static long getWindow(){
		return window;
	}
	public static int getMonitorWidth(){
		return vidmode.width();
	}
	public static int getMonitorHeight(){
		return vidmode.height();
	}
	public static int getMonitorRefreshRate(){
		return vidmode.refreshRate();
	}
	public static int getWidth(){
		return width;
	}
	public static int getHeight(){
		return height;
	}
	public static float getAspectRatio(){
		return (float)width / (float)height;
	}
	public static boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}
	public static void setSize(int width, int height){
		glfwSetWindowSize(window, width, height);
		GameWindow.width = width;
		GameWindow.height = height;
	}
	public static void setTitle(String title){
		glfwSetWindowTitle(window, title);
	}
	public static void setVSync(boolean vsync){
		glfwSwapInterval(vsync ? 1 : 0);
	}
	public static void create(int width, int height, String title){
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		monitor = glfwGetPrimaryMonitor();
		vidmode = glfwGetVideoMode(monitor);
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL){
			throw new RuntimeException("Could not create the window!");
		}
		GameWindow.width = width;
		GameWindow.height = height;
		center();
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
	}
	public static void center(){
		glfwSetWindowPos(window, (getMonitorWidth() - width) / 2, (getMonitorHeight() - height) / 2);
	}
	public static void update(){
		glfwSwapBuffers(window);
		glfwPollEvents();
		Render.clear();
	}
	public static void close(){
		glfwSetWindowShouldClose(window, true);
	}
	public static void destroy(){
		glfwDestroyWindow(window);
	}
}