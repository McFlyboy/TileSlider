package com.nyhammer.tp.graphics.shading.shaders;

import com.nyhammer.tp.graphics.shading.ShaderProgram;
import com.nyhammer.tp.math.Vector2f;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class ECShader extends ShaderProgram{
	private static String vshFilename = "ec.vsh";
	private static String fshFilename = "ec.fsh";
	private int positionLocation;
	private int scaleLocation;
	public ECShader(){
		super(vshFilename, fshFilename);
	}
	@Override
	protected void getUniformLocations(){
		positionLocation = super.getUniformLocation("position");
		scaleLocation = super.getUniformLocation("scale");
	}
	@Override
	protected void bindAttribs(){
		super.bindAttrib(0, "vertex");
		super.bindAttrib(1, "textureCoords");
	}
	public void loadTranformation(Vector2f position, float scale){
		super.loadVector2f(positionLocation, position);
		super.loadFloat(scaleLocation, scale);
	}
}