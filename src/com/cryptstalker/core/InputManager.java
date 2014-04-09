package com.cryptstalker.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener {
	private HashMap<Integer, Input> inputs = new HashMap<Integer, Input>();

	public void tick() {
		for (Map.Entry<Integer, Input> entry : inputs.entrySet()){
			entry.getValue().tick();
		}
	}

	public boolean isDown(int keyCode) {
		Input input = inputs.get(keyCode);
		if(input != null){
			return input.isDown;
		}
		else return false;
	}

	public boolean isClicked(int keyCode) {
		Input input = inputs.get(keyCode);
		if(input != null){
			return input.isClicked;
		}
		else return false;
	}

	public void keyPressed(KeyEvent ke) {
		int keyCode = ke.getKeyCode();
		if(!inputs.containsKey(keyCode)){
			Input input = new Input();
			input.toggle(true);
			inputs.put(keyCode, input);
		}
		else inputs.get(keyCode).toggle(true);
	}

	public void keyReleased(KeyEvent ke) {
		inputs.get(ke.getKeyCode()).toggle(false);
	}

	public void keyTyped(KeyEvent ke) {
	}

	public class Input {
		private int presses, absorbs;
		public boolean isDown, isClicked;

		public void toggle(boolean pressed) {
			if(pressed != isDown) isDown = pressed;
			if(pressed) presses++;
		}

		public void tick() {
			if(absorbs < presses){
				absorbs++;
				isClicked = true;
			}
			else isClicked = false;
		}
	}
}
