package com.jam.demondrop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jam.demondrop.DemonDrop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Demon Summoner";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new DemonDrop(), config);
	}
}
