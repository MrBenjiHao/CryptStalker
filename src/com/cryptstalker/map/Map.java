package com.cryptstalker.map;

import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.entities.Entity;
import com.cryptstalker.entities.Player;
import com.cryptstalker.entities.SpriteSheet;
import com.cryptstalker.states.Game;

public class Map extends Entity {
	public Tile[] tileArray = new Tile[width * height];
	public Game game;

	public Map(int x, int y, int w, int h, Game game) {
		super(x, y, w, h);
		loadMap(0, 7, SpriteSheet.sheet);
		this.game = game;
	}

	public void tick(InputManager input) {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tileArray[x + y * width].tick(input);
			}
		}
	}

	public void render(Screen screen) {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tileArray[x + y * width].render(screen);
			}
		}
	}

	public void loadMap(int xLoc, int yLoc, SpriteSheet sheet) {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				int pixel = sheet.pixels[(x + (xLoc * width)) + (y + (yLoc * height)) * 256];
				if(pixel == 0xFFA0A0A0) tileArray[x + y * width] = new Floor(x * 16, y * 16, 16, 16, this);
				else if(pixel == 0xFFFFFFFF) tileArray[x + y * width] = new Wall(x * 16, y * 16, 16, 16, this);
				else if(pixel == 0xFFFF0000) tileArray[x + y * width] = new Boulder(x * 16, y * 16, 16, 16, this);
				else if(pixel == 0xFF00FF00) tileArray[x + y * width] = new Chest(x * 16, y * 16, 16, 16, this);
				else if(pixel == 0xFF0000FF) tileArray[x + y * width] = new Gem(x * 16, y * 16, 16, 16, this);
			}
		}
	}

	private final int UP = 3, DOWN = 5, LEFT = 6, RIGHT = 4, shift = 32 * 4;

	public void shiftMap(int dir, Player player) {
		// shift map when player enters new room
		if(dir == UP){
			for (int x = 0; x < width; x++){
				for (int y = 0; y < height; y++){
					tileArray[x + y * width].y -= shift;
				}
			}
			player.yy += shift;
			player.y -= shift;
		}
		else if(dir == DOWN){
			for (int x = 0; x < width; x++){
				for (int y = 0; y < height; y++){
					tileArray[x + y * width].y += shift;
				}
			}
			player.yy -= shift;
			player.y += shift;
		}
		else if(dir == LEFT){
			for (int x = 0; x < width; x++){
				for (int y = 0; y < height; y++){
					tileArray[x + y * width].x -= shift;
				}
			}
			player.xx += shift;
			player.x -= shift;
		}
		else if(dir == RIGHT){
			for (int x = 0; x < width; x++){
				for (int y = 0; y < height; y++){
					tileArray[x + y * width].x += shift;
				}
			}
			player.xx -= shift;
			player.x += shift;
		}
	}
}
