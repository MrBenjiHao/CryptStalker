package com.cryptstalker.core;

import com.cryptstalker.states.Game;
import com.cryptstalker.states.GameState;
import com.cryptstalker.states.Instruction;
import com.cryptstalker.states.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private static JFrame window = new JFrame("Crypt Stalker");
	public static final int WIDTH = 128;
	public static final int HEIGHT = 144;
	public static final int SCALE = 3;
	private BufferStrategy bufferStrategy;
	private static Menu menu = new Menu();
	private static Instruction instruct = new Instruction();
	private static GameState currentState;
	private InputManager input = new InputManager();
	private Screen screen = new Screen(WIDTH, HEIGHT);

	public static BufferedImage image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public static void main(String[] args) {
		Engine engine = new Engine();
		
		ArrayList<Integer> i = new ArrayList<Integer>();

		window.add(engine);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setVisible(true);
		window.pack();
		window.setLocationRelativeTo(null);

		engine.start();
	}

	public Engine() {
		System.setProperty("sun.java2d.opengl", "True");
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setState(0);
		initInput();
	}

	public void start() {
		this.createBufferStrategy(2);
		bufferStrategy = this.getBufferStrategy();
		requestFocus();
		new Thread(this).run();
	}

	public void initInput() {
		this.addKeyListener(input);
	}

	public static void setState(int state) {
		switch (state) {
			case 0:
				if(currentState != instruct){
					currentState = menu;
					Sound.scaryTitle.stop();
					Sound.scaryTitle2.loop();
				}
				else currentState = menu;
				break;
			case 1:
				currentState = new Game();
				Sound.scaryTitle.loop();
				Sound.scaryTitle2.stop();
				break;
			case 2:
				currentState = instruct;
				break;
		}
	}

	public void run() {
		requestFocus();
		double delta = (double) 1 / 60;
		long timer = System.nanoTime();
		double prevTime = System.nanoTime() / 1000000000.0;
		double maxTimeDiff = 0.5;

		while (true){
			double currTime = System.nanoTime() / 1000000000.0;
			if(currTime - prevTime > maxTimeDiff) prevTime = currTime;
			if(currTime >= prevTime){
				tick();
				prevTime += delta;
				if(currTime < prevTime) render();
			}
			else{
				int sleep = (int) (1000 * (prevTime - currTime));
				try{
					Thread.sleep(sleep);
				}
				catch (InterruptedException e){
				}
			}
		}
	}

	public void tick() {
        input.tick();
        currentState.tick(input);
    }

	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		screen.clear();
		currentState.render(screen);
		screen.overwriteData();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bufferStrategy.show();
	}
}
