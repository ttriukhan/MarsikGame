package com.marsik.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.marsik.screens.*;

public class MarsikGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short MARSIK_BIT = 2;
	public static final short OBJECT_BIT = 4;
	public static final short GOT_BIT = 8;
	public static final short PLATFORM_BIT = 16;
	public static final short SOLDIER_BIT = 32;
	public static final short DRON_BIT = 64;
	public static final short UFO_BIT = 128;
	public static final short SOLDIER_BULLET_BIT = 256;
	public static final short FREEZE_BULLET_BIT = 512;

	public Music backgroundMusic;

	public SpriteBatch batch;

	public OrthographicCamera gameCam;
	public Viewport gamePort;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/backMusic.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.4f);
		backgroundMusic.play();
		gameCam = new OrthographicCamera();
		gameCam.position.set(0,0,0);
		gameCam.setToOrtho(false);
		gamePort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCam);
		setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
