package com.nyhammer.tp.entities;

import com.nyhammer.tp.graphics.Model;
import com.nyhammer.tp.graphics.Texture;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Grid extends ModelEntity{
	public Tile[] tiles;
	public Grid(Model model, Texture texture){
		super(model, texture);
		this.scale = 4f;
		tiles = new Tile[15];
	}
}