package com.cryptstalker.map;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.core.Sound;
import com.cryptstalker.entities.Particle;
import com.cryptstalker.entities.Player;
import com.cryptstalker.entities.SpriteSheet;
import com.cryptstalker.states.Game;

public class Gem extends Tile {
	private int ticks;
	private boolean finishAnimation = false;
	private int frame = 5;
	public boolean animate = true;

	public Gem(int x, int y, int w, int h, Map map) {
		super(x, y, w, h, map);
		loadSprite(5, 8, SpriteSheet.sheet);
		isSolid = true;
	}

	public void tick(InputManager input) {
		ticks++;
		animation(animate);
	}

	public void render(Screen screen) {
		if((x > -width && x < Engine.WIDTH) && (y > -height && y < Engine.HEIGHT)){
			screen.renderSprite(x, y, width, height, this);
		}
	}

	public void animation(boolean animate) {
		int speed = 30;
		if(animate){
			if(ticks % speed == 0 && !finishAnimation){
				frame++;
				if(frame == 6) finishAnimation = true;
			}
			else if(ticks % speed == 0 && finishAnimation){
				frame--;
				if(frame == 5) finishAnimation = false;
			}
			loadSprite(frame, 8, SpriteSheet.sheet);
		}
	}

	public void pickUp(Player player, int loc) {
		map.tileArray[loc] = new Floor(x, y, 16, 16, map);
		new Particle(x, y, 16, 16, map.game.particles);
		player.numGems++;
		Sound.gemPickUp.play();
	}
}
