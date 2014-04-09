package com.cryptstalker.states;

import java.awt.event.KeyEvent;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;

public class Menu implements GameState {
	public int ticks = 0;
	public boolean flash = false;
	public int selection, enter = 1, instructions = 2;
	private int frame = 0;
	private boolean finishAnimation;

	public Menu() {
		selection = enter;
	}

	public void tick(InputManager input) {
		ticks++;
		if(input.isClicked(KeyEvent.VK_ENTER)){
			if(selection == enter) Engine.setState(1);
			if(selection == instructions) Engine.setState(2);
		}
		switchOption(input);
		animation();
		if(ticks % 30 == 0) flash = true;
		if(ticks % 60 == 0) flash = false;
	}

	public void render(Screen screen) {
		renderTitle(screen);
		flashOptions(screen);
		screen.renderSprite(50 - frame, 63, 16, 16, 7, 8);
		screen.renderSprite(57, 70 + frame, 16, 16, 7, 8);
		screen.renderSprite(64 + frame, 63, 16, 16, 7, 8);
		screen.renderSprite(57, 56 - frame, 16, 16, 7, 8);
	}

	public void flashOptions(Screen screen) {
		if(selection == enter && flash){
			screen.renderSprite(36, 97, 8, 8, 128 / 8, 32 / 8);
			screen.drawString("Play", 50, 98, 0xffffff);
		}
		else if(selection == instructions && flash){
			screen.renderSprite(4, 109, 8, 8, 128 / 8, 32 / 8);
			screen.drawString("Instructions", 18, 110, 0xffffff);
		}
		if(selection == instructions) screen.drawString("Play", 50, 98, 0xffffff);
		else if(selection == enter) screen.drawString("Instructions", 18, 110, 0xffffff);
		screen.drawString("Press Enter", 22, 136, 0xfffffff);
	}

	public void switchOption(InputManager input) {
		if(input.isClicked(KeyEvent.VK_UP)){
			if(selection == enter) selection = instructions;
			else if(selection == instructions) selection = enter;
		}
		else if(input.isClicked(KeyEvent.VK_DOWN)){
			if(selection == enter) selection = instructions;
			else if(selection == instructions) selection = enter;
		}
	}

	public void animation() {
		int speed = 4;
		if(ticks % speed == 0 && !finishAnimation){
			frame++;
			if(frame == 10) finishAnimation = true;
		}
		else if(ticks % speed == 0 && finishAnimation){
			frame--;
			if(frame == 0) finishAnimation = false;
		}
	}

	public void renderTitle(Screen screen) {
		screen.drawString("Crypt", 44, 144 / 8, 0xffffff);
		screen.drawString("Stalker", 37, 144 / 5 + 1, 0xffffff);
	}
}
