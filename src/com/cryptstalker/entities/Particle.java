package com.cryptstalker.entities;

import java.util.ArrayList;

import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;

public class Particle extends Entity {
	private int frame = 0, ticks = 0;
	private boolean finishAnimation = false;

	public Particle(int x, int y, int w, int h, ArrayList<Entity> particles) {
		super(x, y, w, h);
		loadSprite(0, 7, SpriteSheet.sheet);
		if(particles != null) particles.add(this);
	}

	public void tick(InputManager input) {
		ticks++;
		animation(4);
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, width, height, this);
	}

	public void animation(int numFrames) {
		int speed = 6;
		if(ticks % speed == 0 && !finishAnimation){
			frame++;
			if(frame == numFrames){
				finishAnimation = true;
				remove();
			}
		}
		else if(ticks % speed == 0 && finishAnimation){
			frame = 0;
			finishAnimation = false;
		}
		loadSprite(frame, 7, SpriteSheet.sheet);
	}
}
