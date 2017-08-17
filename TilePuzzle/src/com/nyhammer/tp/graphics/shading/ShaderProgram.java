package com.nyhammer.tp.graphics.shading;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.nyhammer.tp.math.Vector2f;
import com.nyhammer.tp.ui.GameWindow;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public abstract class ShaderProgram{
	private final int vertexShaderID;
	private final int fragmentShaderID;
	private final int programID;
	public ShaderProgram(String vshFilename, String fshFilename){
		vertexShaderID = loadShader(vshFilename, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fshFilename, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttribs();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getUniformLocations();
	}
	public void start(){
		glUseProgram(programID);
	}
	public static void stop(){
		glUseProgram(0);
	}
	protected void loadInt(int location, int i){
		glUniform1i(location, i);
	}
	protected void loadBoolean(int location, boolean b){
		glUniform1i(location, b ? GL_TRUE : GL_FALSE);
	}
	protected void loadFloat(int location, float f){
		glUniform1f(location, f);
	}
	protected void loadVector2f(int location, Vector2f vec){
		glUniform2f(location, vec.x, vec.y);
	}
	protected abstract void getUniformLocations();
	protected int getUniformLocation(String varName){
		return glGetUniformLocation(programID, varName);
	}
	protected abstract void bindAttribs();
	protected void bindAttrib(int index, String attribName){
		glBindAttribLocation(programID, index, attribName);
	}
	private int loadShader(String filename, int type){
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream("/shaders/" + filename)));
			String line;
			while((line = reader.readLine()) != null){
				shaderSource.append(line).append("\n");
			}
		}
		catch(FileNotFoundException e){
			System.err.println("Could not find the shaderfile: " + filename);
			e.printStackTrace();
			GameWindow.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.err.println("Failed to read from shaderfile: " + filename);
			GameWindow.close();
		}
		try{
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.err.println("Failed to close reader after reading from shaderfile: " + filename);
			GameWindow.close();
		}
		catch(NullPointerException e){
			e.printStackTrace();
			System.err.println("Failed to close reader. Reader seems to not have been initialized properly!");
			GameWindow.close();
		}
		int shader = glCreateShader(type);
		glShaderSource(shader, shaderSource);
		glCompileShader(shader);
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE){
			System.err.println("Error in shader-code in file: " + filename);
			System.err.println(glGetShaderInfoLog(shader, 500));
			GameWindow.close();
		}
		return shader;
	}
	public void dispose(){
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
}