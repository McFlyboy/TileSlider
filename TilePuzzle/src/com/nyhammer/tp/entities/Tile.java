package com.nyhammer.tp.entities;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Tile{
	public ModelEntity entity;
	private int x;
	private int y;
	public int getX(){
		return x;
	}
	public void moveX(int xMovement){
		x += xMovement;
		entity.position.x = x - 2.5f;
	}
	public int getY(){
		return y;
	}
	public void moveY(int yMovement){
		y += yMovement;
		entity.position.y = 2.5f - y;
	}
	public Tile(int x, int y, ModelEntity entity){
		this.x = x;
		this.y = y;
		this.entity = entity;
		entity.position.x = x - 2.5f;
		entity.position.y = 2.5f - y;
	}
}