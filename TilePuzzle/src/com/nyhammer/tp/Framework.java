package com.nyhammer.tp;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Framework{
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 1;
	public static final int VERSION_REVISION = 0;
	public static final int VERSION_PATCH = 0;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static String getVersion(){
		StringBuilder version = new StringBuilder();
		version.append(String.format("%d.%d.%d", VERSION_MAJOR, VERSION_MINOR, VERSION_REVISION));
		String patch = String.format("_%02d", VERSION_PATCH);
		if(!patch.equals("_00")){
			version.append(patch);
		}
		version.append(PRE_VERSION_SUFFIX);
		return version.toString();
	}
	public static String getLWJGLVersion(){
		return Version.getVersion();
	}
	public static void init(){
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()){
			throw new IllegalStateException("Could not initialize GLFW!");
		}
	}
	public static void terminate(){
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}