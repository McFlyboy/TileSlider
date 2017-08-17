package com.nyhammer.tp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nyhammer.tp.entities.Grid;
import com.nyhammer.tp.entities.ModelEntity;
import com.nyhammer.tp.entities.Player;
import com.nyhammer.tp.entities.Tile;
import com.nyhammer.tp.graphics.Model;
import com.nyhammer.tp.graphics.Render;
import com.nyhammer.tp.graphics.Texture;
import com.nyhammer.tp.graphics.shading.shaders.ECShader;
import com.nyhammer.tp.input.Keyboard;
import com.nyhammer.tp.ui.GameWindow;

/**
 * 
 * @author McFlyboy
 * 
 * @since 0.1.0a
 *
 */
public class Main{
	private ECShader shader;
	private Model square;
	private Texture[] tileTextures = new Texture[15];
	private List<Integer> startPositions;
	private Random random = new Random();
	private Texture gridTexture;
	private Grid grid;
	private Player player;
	private boolean complete;
	private Timer timer;
	private boolean firstMove;
	private int steps;
	private void start(){
		Framework.init();
		GameWindow.create(300, 300, "Endless cicle");
		Keyboard.create();
		Render.setClearColor(0.75f, 0.75f, 0.75f);
		player = new Player();
		square = Model.createSquareModel();
		for(int i = 0; i < tileTextures.length; i++){
			int textureNumber = i + 1;
			tileTextures[i] = new Texture("Tile" + textureNumber + ".png");
		}
		gridTexture = new Texture("Grid.png");
		grid = new Grid(square, gridTexture);
		generatePossiblePuzzle();
		shader = new ECShader();
		Render.setShader(shader);
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){
			e.printStackTrace();
		}
		shader.start();
		run();
	}
	private void run(){
		while(!GameWindow.shouldClose()){
			update();
			render();
		}
		stop();
	}
	private void update(){
		if(Keyboard.isKeyPressed(Keyboard.KEY_ESCAPE)){
			GameWindow.close();
		}
		if(!complete){
			if(Keyboard.isKeyPressed(Keyboard.KEY_LEFT)){
				if(firstMove){
					timer = new Timer();
					firstMove = false;
				}
				for(Tile tile : grid.tiles){
					if(tile.getX() == player.x + 1 && tile.getY() == player.y){
						player.x += 1f;
						tile.moveX(-1);
						if(checkFinished()){
							complete = true;
						}
						steps++;
						break;
					}
				}
			}
			if(Keyboard.isKeyPressed(Keyboard.KEY_RIGHT)){
				if(firstMove){
					timer = new Timer();
					firstMove = false;
				}
				for(Tile tile : grid.tiles){
					if(tile.getX() == player.x - 1 && tile.getY() == player.y){
						player.x -= 1f;
						tile.moveX(1);
						if(checkFinished()){
							complete = true;
						}
						steps++;
						break;
					}
				}
			}
			if(Keyboard.isKeyPressed(Keyboard.KEY_DOWN)){
				if(firstMove){
					timer = new Timer();
					firstMove = false;
				}
				for(Tile tile : grid.tiles){
					if(tile.getY() == player.y - 1 && tile.getX() == player.x){
						player.y -= 1f;
						tile.moveY(1);
						if(checkFinished()){
							complete = true;
						}
						steps++;
						break;
					}
				}
			}
			if(Keyboard.isKeyPressed(Keyboard.KEY_UP)){
				if(firstMove){
					timer = new Timer();
					firstMove = false;
				}
				for(Tile tile : grid.tiles){
					if(tile.getY() == player.y + 1 && tile.getX() == player.x){
						player.y += 1f;
						tile.moveY(-1);
						if(checkFinished()){
							complete = true;
						}
						steps++;
						break;
					}
				}
			}
		}
		else{
			showMessage();
			generatePossiblePuzzle();
		}
	}
	public void generatePuzzle(){
		startPositions = new ArrayList<Integer>();
		for(int i = 1; i <= 16; i++){
			startPositions.add(i);
		}
		for(int i = 0; i < grid.tiles.length; i++){
			int startPosition = startPositions.remove(random.nextInt(startPositions.size()));
			int x;
			int y = 1;
			while(startPosition > 4){
				y++;
				startPosition -= 4;
			}
			x = startPosition;
			grid.tiles[i] = new Tile(x, y, new ModelEntity(square, tileTextures[i]));
		}
		int startPosition = startPositions.get(0);
		player.y = 1;
		while(startPosition > 4){
			player.y++;
			startPosition -= 4;
		}
		player.x = startPosition;
		complete = false;
		firstMove = true;
	}
	private void generatePossiblePuzzle(){
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 4; x++){
				if(x * y != 9){
					grid.tiles[x + y * 4] = new Tile(x + 1, y + 1, new ModelEntity(square, tileTextures[x + y * 4]));
				}
			}
		}
		player.x = 4;
		player.y = 4;
		int scrambleCount = 80;
		int lastDirection = 100;
		while(scrambleCount > 0){
			int direction;
			do{
				direction = random.nextInt(4);
			}
			while(Math.abs(direction - lastDirection) == 2);
			if(direction == 0){
				for(Tile tile : grid.tiles){
					if(tile.getX() == player.x + 1 && tile.getY() == player.y){
						player.x += 1f;
						tile.moveX(-1);
						scrambleCount--;
						lastDirection = 0;
						break;
					}
				}
			}
			if(direction == 2){
				for(Tile tile : grid.tiles){
					if(tile.getX() == player.x - 1 && tile.getY() == player.y){
						player.x -= 1f;
						tile.moveX(1);
						scrambleCount--;
						lastDirection = 2;
						break;
					}
				}
			}
			if(direction == 1){
				for(Tile tile : grid.tiles){
					if(tile.getY() == player.y - 1 && tile.getX() == player.x){
						player.y -= 1f;
						tile.moveY(1);
						scrambleCount--;
						lastDirection = 1;
						break;
					}
				}
			}
			if(direction == 3){
				for(Tile tile : grid.tiles){
					if(tile.getY() == player.y + 1 && tile.getX() == player.x){
						player.y += 1f;
						tile.moveY(-1);
						scrambleCount--;
						lastDirection = 3;
						break;
					}
				}
			}
		}
		complete = false;
		firstMove = true;
		steps = 0;
	}
	private boolean checkFinished(){
		for(int i = 0; i < grid.tiles.length; i++){
			if(i + 1 != grid.tiles[i].getX() + (grid.tiles[i].getY() - 1) * 4){
				return false;
			}
		}
		return true;
	}
	private void render(){
		Render.render(grid);
		for(Tile tile : grid.tiles){
			Render.render(tile.entity);
		}
		GameWindow.update();
	}
	private void showMessage(){
		int seconds = (int)timer.getTime();
		int minutes = 0;
		int hours = 0;
		while(seconds >= 60){
			minutes++;
			seconds -= 60;
		}
		while(minutes >= 60){
			hours++;
			minutes -= 60;
		}
		StringBuilder message = new StringBuilder();
		message.append("Time: ");
		if(hours > 0){
			message.append(hours + ":");
		}
		if(minutes > 0 || hours > 0){
			message.append(minutes + ":");
		}
		message.append(seconds);
		message.append("\nSteps: " + steps);
		if(hours > 0){
			message.append("\n\nHOW DID YOU MANAGE TO SPEND SO MUCH TIME ON ONE PUZZLE?!\nWHAT ARE YOU DOOIIIING?!?!?!");
		}
		JOptionPane.showMessageDialog(null, message.toString());
	}
	private void stop(){
		ECShader.stop();
		shader.dispose();
		for(Texture tileTexture : tileTextures){
			tileTexture.dispose();
		}
		gridTexture.dispose();
		Keyboard.destroy();
		GameWindow.destroy();
		Framework.terminate();
		System.exit(0);
	}
	public static void main(String[] args){
		new Main().start();
	}
}