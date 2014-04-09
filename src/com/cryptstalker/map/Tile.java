package com.cryptstalker.map;

import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.entities.Entity;

public abstract class Tile extends Entity {
	public boolean isSolid = false;
	public Map map;
	
	public Tile(int x, int y, int w, int h, Map map) {
		super(x, y, w, h);
		this.map = map;
	}
	
	public abstract void tick(InputManager input);
	public abstract void render(Screen screen);
}
