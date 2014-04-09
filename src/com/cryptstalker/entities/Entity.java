package com.cryptstalker.entities;

import java.awt.Rectangle;

import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;

public abstract class Entity {
	public int x, y, width, height;
	private boolean removed = false;
	public int[] pixels;

	public Entity(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		pixels = new int[width * height];
	}

	public abstract void tick(InputManager input);

	public abstract void render(Screen screen);

	public void loadSprite(int xLoc, int yLoc, SpriteSheet sheet) {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				pixels[x + y * width] = sheet.pixels[(x + (xLoc * width)) + (y + (yLoc * height)) * 256];
			}
		}
	}

	public boolean hasCollided(Entity e1, Entity e2) {
		Rectangle r1 = new Rectangle(e1.x, e1.y, e1.width, e1.height);
		Rectangle r2 = new Rectangle(e2.x, e2.y, e2.width, e2.height);

		return r1.intersects(r2);
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
}
