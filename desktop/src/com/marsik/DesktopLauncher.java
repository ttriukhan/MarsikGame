package com.marsik;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.marsik.tools.MarsikGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1200, 625);
		config.setForegroundFPS(60);
		config.setTitle("MARSIK");
		config.setWindowIcon("icon.png");
		new Lwjgl3Application(new MarsikGame(), config);
	}
}
