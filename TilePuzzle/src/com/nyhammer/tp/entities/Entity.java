package com.nyhammer.tp.entities;

import com.nyhammer.tp.math.Vector2f;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Entity{
	public Vector2f position;
	public Vector2f angle;
	public float scale;
	public Entity(Vector2f position, Vector2f angle, float scale){
		this.position = position;
		this.angle = angle;
		this.scale = scale;
	}
	public Entity(Vector2f position, float scale){
		this(position, new Vector2f(), scale);
	}
	public Entity(Vector2f position){
		this(position, new Vector2f(), 1f);
	}
	public Entity(){
		this(new Vector2f(), new Vector2f(), 1f);
	}
}