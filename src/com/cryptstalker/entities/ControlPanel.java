package com.cryptstalker.entities;

import com.cryptstalker.core.Engine;
import com.cryptstalker.core.InputManager;
import com.cryptstalker.core.Screen;

public class ControlPanel extends Entity {
	public int time = 0, ticks = 0;
	public Player player;
	public int numGems = 10;
	public boolean flash = false;
	public int score;

	public ControlPanel(int x, int y, int w, int h, Player player) {
		super(x, y, w, h);
		loadSprite(0, 2, SpriteSheet.sheet);
		this.player = player;
	}

	public void tick(InputManager input) {
		ticks++;
		incrementTime();
		gameOver();
		win();
	}

	public void render(Screen screen) {
		if((x > -width && x < Engine.WIDTH) && (y > -height && y < Engine.HEIGHT)){
			screen.renderSprite(x, y, width, height, this);
		}
		if(!flash) screen.drawString("Time:" + time, 1, 1, 0xffffff);
		if(player.gameOver){
			screen.drawString("GAME OVER", 30, 40, 0xffffff);
			screen.drawString("Score:" + score, 30, 60, 0xffffff);
		}
		if(player.win){
			screen.drawString("Congrats!", 30, 40, 0xffffff);
			screen.drawString("Score:" + score, 30, 60, 0xffffff);
		}
		if(player.win || player.gameOver) screen.drawString("Press ESC", 30, 110, 0xffffff);
		renderItems(screen);
		renderNumGems(screen);
	}

	public void renderItems(Screen screen) {
		if(player.stonePick) screen.renderSprite(4, 132, 8, 8, 0, 19);
		if(player.torch) screen.renderSprite(15, 132, 8, 8, 1, 19);
	}

	public void renderNumGems(Screen screen) {
		screen.drawString(player.numGems + "/" + numGems, 86, 132, 0xffffff);
		screen.renderSprite(86 - 16, 128, 16, 16, 7, 8);
	}

	public void gameOver() {
		if(player.gameOver){
			if(ticks % 30 == 0) flash = true;
			if(ticks % 60 == 0) flash = false;
			calculateScore();
		}
	}

	public void win() {
		if(player.numGems == this.numGems){
			player.win = true;
			if(ticks % 30 == 0) flash = true;
			if(ticks % 60 == 0) flash = false;
			calculateScore();
		}
	}

	public void calculateScore() {
		score = (player.numGems * 100) - (time * 2);
	}

	public void incrementTime() {
		if(!player.gameOver && !player.win){
			if(ticks % 60 == 0) time++;
		}
	}
}
