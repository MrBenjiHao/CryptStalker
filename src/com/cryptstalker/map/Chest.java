package com.cryptstalker.map;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.core.Sound;
import com.cryptstalker.entities.Player;
import com.cryptstalker.entities.SpriteSheet;

public class Chest extends Tile {
	private boolean isOpen = false, isEmpty = false;

	public Chest(int x, int y, int w, int h, Map map) {
		super(x, y, w, h, map);
		loadSprite(3, 8, SpriteSheet.sheet);
		isSolid = true;
	}

	public void tick(InputManager input) {
		if(isOpen) loadSprite(4, 8, SpriteSheet.sheet);
		else loadSprite(3, 8, SpriteSheet.sheet);
	}

	public void render(Screen screen) {
		if((x > -width && x < Engine.WIDTH) && (y > -height && y < Engine.HEIGHT)){
			screen.renderSprite(x, y, width, height, this);
		}
	}

	public void open(Player player) {
		if(!isOpen){
			isOpen = true;
			giveItem(player);
			Sound.chest.play();
		}
	}

	public void giveItem(Player player) {
		if(isOpen && !isEmpty){
			if(!player.stonePick) player.stonePick = true;
			if(!player.torch || player.lightDecay){
				player.torch = true;
				player.lightDecay = false;
			}
			isEmpty = true;
		}
	}
}
