package com.cryptstalker.entities;

import java.awt.event.KeyEvent;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.map.Boulder;
import com.cryptstalker.map.Chest;
import com.cryptstalker.map.Gem;
import com.cryptstalker.map.Map;
import com.cryptstalker.map.Tile;

public class Player extends Entity {
	public int ticks = 0;
	public int frame = 0;
	public boolean finishAnimation = false, canMove = false;;
	private final int UP = 3, DOWN = 5, LEFT = 6, RIGHT = 4;
	public boolean stonePick = false, torch = false, lightDecay = true, gameOver = false, win = false;
	public int lightRadius = 80;
	public int numGems = 0;
	public int currDir;
	private Map map;

	public Player(int x, int y, int w, int h, Map map) {
		super(x, y, w, h);
		loadSprite(1, 6, SpriteSheet.sheet);
		currDir = DOWN;
		this.map = map;
	}

	public void tick(InputManager input) {
		ticks++;
		if(!gameOver && !win){
			movement(input);
			animation();
			performAction(input);
		}
	}

	public void render(Screen screen) {
		if((x > -width && x < Engine.WIDTH) && (y > -height && y < Engine.HEIGHT)){
			screen.renderSprite(x, y, width, height, this);
		}
		renderLight(screen);
	}

	public void animation() {
		int speed = 5;
		if(canMove){
			if(ticks % speed == 0 && !finishAnimation){
				frame++;
				if(frame == 2) finishAnimation = true;
			}
			else if(ticks % speed == 0 && finishAnimation){
				frame--;
				if(frame == 0) finishAnimation = false;
			}
			loadSprite(frame, currDir, SpriteSheet.sheet);
		}
		else loadSprite(1, currDir, SpriteSheet.sheet);
	}

	private int pixelsWalked = 0;

	public void movement(InputManager input) {
		if(!canMove){
			if(input.isDown(KeyEvent.VK_UP)){
				currDir = UP;
				if(!isSolid(UP)){
					pixelsWalked = 0;
					canMove = true;
				}
			}
			else if(input.isDown(KeyEvent.VK_DOWN)){
				currDir = DOWN;
				if(!isSolid(DOWN)){
					pixelsWalked = 0;
					canMove = true;
				}
			}
			else if(input.isDown(KeyEvent.VK_LEFT)){
				currDir = LEFT;
				if(!isSolid(LEFT)){
					pixelsWalked = 0;
					canMove = true;
				}
			}
			else if(input.isDown(KeyEvent.VK_RIGHT)){
				currDir = RIGHT;
				if(!isSolid(RIGHT)){
					pixelsWalked = 0;
					canMove = true;
				}
			}
			changeRoom();
		}

		if(pixelsWalked < 16 && canMove){
			if(currDir == UP) y--;
			else if(currDir == DOWN) y++;
			else if(currDir == LEFT) x--;
			else if(currDir == RIGHT) x++;
			pixelsWalked++;
		}
		else canMove = false;
	}

	public void renderLight(Screen screen) {
		if(torch){
			if(!lightDecay){
				lightRadius = 80;
				lightDecay = true;
			}
		}
		if(ticks % 30 == 0 && lightDecay && !win){
			lightRadius -= 1;
			if(lightRadius <= 0){
				torch = false;
				lightDecay = false;
				gameOver = true;
			}
		}
		screen.renderLight(x + (width / 2), y + (height / 2), lightRadius);
	}

	public int xx = 0, yy = 0;

	public boolean isSolid(int dir) {
		if(dir == UP) return map.tileArray[((x + xx) / 16) + (((y + yy) - 16) / 16) * 32].isSolid;
		else if(dir == DOWN) return map.tileArray[((x + xx) / 16) + (((y + yy) + 16) / 16) * 32].isSolid;
		else if(dir == LEFT) return map.tileArray[(((x + xx) - 16) / 16) + ((y + yy) / 16) * 32].isSolid;
		else if(dir == RIGHT) return map.tileArray[(((x + xx) + 16) / 16) + ((y + yy) / 16) * 32].isSolid;
		else return false;
	}

	public void performAction(InputManager input) {
		if(input.isClicked(KeyEvent.VK_SPACE)){
			if(currDir == UP){
				int loc = ((x + xx) / 16) + (((y + yy) - 16) / 16) * 32;
				Tile t = map.tileArray[loc];
				if(t instanceof Boulder) ((Boulder) map.tileArray[loc]).destroy(this, loc);
				else if(t instanceof Chest) ((Chest) map.tileArray[loc]).open(this);
				else if(t instanceof Gem) ((Gem) map.tileArray[loc]).pickUp(this, loc);
			}
			else if(currDir == DOWN){
				int loc = ((x + xx) / 16) + (((y + yy) + 16) / 16) * 32;
				Tile t = map.tileArray[loc];
				if(t instanceof Boulder) ((Boulder) map.tileArray[loc]).destroy(this, loc);
				else if(t instanceof Chest) ((Chest) map.tileArray[loc]).open(this);
				else if(t instanceof Gem) ((Gem) map.tileArray[loc]).pickUp(this, loc);
			}
			else if(currDir == LEFT){
				int loc = (((x + xx) - 16) / 16) + ((y + yy) / 16) * 32;
				Tile t = map.tileArray[loc];
				if(t instanceof Boulder) ((Boulder) map.tileArray[loc]).destroy(this, loc);
				else if(t instanceof Chest) ((Chest) map.tileArray[loc]).open(this);
				else if(t instanceof Gem) ((Gem) map.tileArray[loc]).pickUp(this, loc);
			}
			else if(currDir == RIGHT){
				int loc = (((x + xx) + 16) / 16) + ((y + yy) / 16) * 32;
				Tile t = map.tileArray[loc];
				if(t instanceof Boulder) ((Boulder) map.tileArray[loc]).destroy(this, loc);
				else if(t instanceof Chest) ((Chest) map.tileArray[loc]).open(this);
				else if(t instanceof Gem) ((Gem) map.tileArray[loc]).pickUp(this, loc);
			}
		}
	}

	public void changeRoom() {
		if(x > Engine.WIDTH - 16){
			map.shiftMap(LEFT, this);
		}
		else if(x < 0){
			map.shiftMap(RIGHT, this);
		}
		else if(y > Engine.HEIGHT - 32){
			map.shiftMap(UP, this);
		}
		else if(y < 0){
			map.shiftMap(DOWN, this);
		}
	}
}
