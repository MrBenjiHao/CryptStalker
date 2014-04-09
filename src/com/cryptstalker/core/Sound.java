package com.cryptstalker.core;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	private Clip sound;
	public static Sound breakStone = new Sound("breakStone.wav");
	public static Sound scaryTitle = new Sound("scaryTitle.wav");
	public static Sound scaryTitle2 = new Sound("scaryTitle2.wav");
	public static Sound chest = new Sound("item.wav");
	public static Sound cannotUse = new Sound("cannotUse.wav");
	public static Sound gemPickUp = new Sound("pickUp.wav");

	public Sound(String fileName) {
		sound = loadSound(fileName);
	}

	private Clip loadSound(String fileName) {
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getClassLoader().getResource("sounds/" + fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			return clip;
		}
		catch (Exception e){
			//System.err.println(e);
			return null;
		}
	}

	public void play() {
		if(sound != null){
			sound.stop();
			sound.setFramePosition(0);
			sound.start();
		}
	}
	
	public void stop(){
		sound.stop();
	}

	public void loop() {
		if(sound != null){
			sound.stop();
			sound.setFramePosition(0);
			sound.loop(999);
		}
	}
}
