package com.nyhammer.tp.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.tp.entities.ModelEntity;
import com.nyhammer.tp.graphics.shading.shaders.ECShader;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Render{
	private static ECShader shader;
	public static void setShader(ECShader shader){
		Render.shader = shader;
	}
	public static void clear(){
		glClear(GL_COLOR_BUFFER_BIT);
	}
	public static void setClearColor(float red, float green, float blue){
		glClearColor(red, green, blue, 1f);
	}
	public static void render(ModelEntity entity){
		entity.model.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, entity.texture.getTextureID());
		shader.loadTranformation(entity.position, entity.scale);
		glDrawElements(GL_TRIANGLES, entity.model.getIndexCount(), GL_UNSIGNED_INT, 0L);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		Model.unbind();
	}
}