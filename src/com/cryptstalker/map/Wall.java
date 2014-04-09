package com.cryptstalker.map;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.entities.SpriteSheet;

public class Wall extends Tile {

	public Wall(int x, int y, int w, int h, Map map) {
		super(x, y, w, h, map);
		loadSprite(0, 8, SpriteSheet.sheet);
		isSolid = true;
	}

	public void tick(InputManager input) {
	}

	public void render(Screen screen) {
		if((x > -width && x < Engine.WIDTH) && (y > -height && y < Engine.HEIGHT)){
			screen.renderSprite(x, y, width, height, this);
		}
	}
}
