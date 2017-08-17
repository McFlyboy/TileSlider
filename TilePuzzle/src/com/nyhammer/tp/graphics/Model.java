package com.nyhammer.tp.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Model{
	private int vaoID;
	private List<Integer> vboIDs = new ArrayList<Integer>();
	private int vertexVboID;
	private int indexCount;
	public Model(){
		vaoID = glGenVertexArrays();
		bind();
	}
	public int getIndexCount(){
		return indexCount;
	}
	public void bind(){
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vertexVboID);
	}
	public static void unbind(){
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	public void setFaces(int[] data){
		vertexVboID = glGenBuffers();
		IntBuffer buffer = createIntBuffer(data);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vertexVboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		indexCount = data.length;
	}
	public void addAttrib(int index, int size, float[] data){
		int vboID = glGenBuffers();
		vboIDs.add(vboID);
		FloatBuffer buffer = createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	private FloatBuffer createFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	private IntBuffer createIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	public static Model createSquareModel(){
		Model square = new Model();
		square.setFaces(new int[]{
				0, 1, 2,
				0, 2, 3
		});
		square.addAttrib(0, 2, new float[]{
				-0.5f, -0.5f,
				0.5f, -0.5f,
				0.5f, 0.5f,
				-0.5f, 0.5f
		});
		square.addAttrib(1, 2, new float[]{
				0f, 0f,
				1f, 0f,
				1f, 1f,
				0f, 1f
		});
		Model.unbind();
		return square;
	}
	public void dispose(){
		for(int vboID : vboIDs){
			glDeleteBuffers(vboID);
		}
		glDeleteBuffers(vertexVboID);
		glDeleteVertexArrays(vaoID);
	}
}