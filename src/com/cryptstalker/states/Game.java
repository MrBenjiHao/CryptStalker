package com.cryptstalker.states;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;
import com.cryptstalker.entities.ControlPanel;
import com.cryptstalker.entities.Entity;
import com.cryptstalker.entities.Player;
import com.cryptstalker.map.Map;

public class Game implements GameState {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> particles = new ArrayList<Entity>();
	public Player player;
	public ControlPanel panel;

	public Game() {
		init();
	}
	
	public void init(){
		// Initialize entities and components
		Map map = new Map(0, 0, 32, 32, this);
		player = new Player(16 * 2, 16 * 1, 16, 16, map);
		panel = new ControlPanel(0, 16 * 8, 16 * 8, 16, player);
		entities.add(map);
		entities.add(player);

	}

	public void tick(InputManager input) {
		if(input.isClicked(KeyEvent.VK_ESCAPE)) Engine.setState(0);
		for (Entity e : entities) e.tick(input);
		for (Entity e : particles) e.tick(input);
		panel.tick(input);
		removeEntity();
	}

	public void render(Screen screen) {
		for (Entity e : entities) e.render(screen);
		for (Entity e : particles) e.render(screen);
		panel.render(screen);
	}

	public void removeEntity() {
		for (int i = 0; i < entities.size(); i++){
			if(entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < particles.size(); i++){
			if(particles.get(i).isRemoved()) particles.remove(i);
		}
	}
}
