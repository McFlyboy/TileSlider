package com.nyhammer.tp.entities;

import com.nyhammer.tp.graphics.Model;
import com.nyhammer.tp.graphics.Texture;
import com.nyhammer.tp.math.Vector2f;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class ModelEntity extends Entity{
	public Model model;
	public Texture texture;
	public ModelEntity(Model model, Texture texture, Vector2f position, Vector2f angle, float scale){
		super(position, angle, scale);
		this.model = model;
		this.texture = texture;
	}
	public ModelEntity(Model model, Texture texture, Vector2f position, float scale){
		super(position, scale);
		this.model = model;
		this.texture = texture;
	}
	public ModelEntity(Model model, Texture texture, Vector2f position){
		super(position);
		this.model = model;
		this.texture = texture;
	}
	public ModelEntity(Model model, Texture texture){
		super();
		this.model = model;
		this.texture = texture;
	}
}