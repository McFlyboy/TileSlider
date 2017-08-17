package com.nyhammer.tp.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Texture{
	private int width;
	private int height;
	private int colorComp;
	private int textureID;
	public Texture(String filename){
		this(filename, false);
	}
	public Texture(String filename, boolean linear){
		ByteBuffer img = loadImage(filename);
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		int filter = linear ? GL_LINEAR : GL_NEAREST;
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, img);
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getColorComp(){
		return colorComp;
	}
	public int getTextureID(){
		return textureID;
	}
	private ByteBuffer loadImage(String filename){
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer colorCompBuffer = BufferUtils.createIntBuffer(1);
		stbi_set_flip_vertically_on_load(true);
		ByteBuffer img = stbi_load("assets/textures/" + filename, widthBuffer, heightBuffer, colorCompBuffer, 4);
		if(img == null){
			throw new RuntimeException(String.format("Could not load the texture: %s\n%s", filename, stbi_failure_reason()));
		}
		width = widthBuffer.get();
		height = heightBuffer.get();
		colorComp = colorCompBuffer.get();
		return img;
	}
	public void dispose(){
		glDeleteTextures(textureID);
	}
}