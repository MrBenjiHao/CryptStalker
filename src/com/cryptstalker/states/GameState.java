package com.cryptstalker.states;

import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;

public interface GameState {
	void tick(InputManager input);

	void render(Screen screen);
}
