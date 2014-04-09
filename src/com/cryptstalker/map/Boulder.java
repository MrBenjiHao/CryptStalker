package com.cryptstalker.map;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.core.Sound;
import com.cryptstalker.entities.Particle;
import com.cryptstalker.entities.Player;
import com.cryptstalker.entities.SpriteSheet;
import com.cryptstalker.states.Game;

public class Boulder extends Tile {

	public Boulder(int x, int y, int w, int h, Map map) {
		super(x, y, w, h, map);
		loadSprite(2, 8, SpriteSheet.sheet);
		isSolid = true;
	}

	public void tick(InputManager input) {

	}

	public void render(Screen screen) {
		if((x > -width && x < Engine.WIDTH) && (y > -height && y < Engine.HEIGHT)){
			screen.renderSprite(x, y, width, height, this);
		}
	}
	
	public void destroy(Player player, int loc){
		if(player.stonePick){
			map.tileArray[loc] = new Floor(x, y, 16, 16, map);
			new Particle(x , y, 16, 16, map.game.particles);
			Sound.breakStone.play();
		}
		else Sound.cannotUse.play();
	}
}
