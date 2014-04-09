package com.cryptstalker.states;

import java.awt.event.KeyEvent;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;

public class Instruction implements GameState{

	public void tick(InputManager input) {
		if(input.isClicked(KeyEvent.VK_SPACE) || input.isClicked(KeyEvent.VK_ENTER)) Engine.setState(0);
	}

	public void render(Screen screen) {
		screen.drawString("use the arrow", 14, 0, 0xffffff);
		screen.drawString("keys to move", 18, 9, 0xffffff);
		
		screen.drawString("space to", 34, 20, 0xffffff);
		screen.drawString("interact", 34, 29, 0xffffff);
		
		screen.drawString("Collect the gems", 0, 42, 0xffffff);
		screen.drawString("before the light", 0, 51, 0xffffff);
		screen.drawString("burns out!", 30, 60, 0xffffff);
		
		screen.drawString("Chests may yeild", 0, 72, 0xffffff);
		screen.drawString("torches!", 34, 82, 0xffffff);
		
		screen.drawString("Press Enter", 22, 136, 0xfffffff);
	}
}
