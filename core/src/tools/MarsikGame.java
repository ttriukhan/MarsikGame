package tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.marsik.screens.PlayScreen;

public class MarsikGame extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short MARSIK_BIT = 2;
	public static final short SAMPLE_BIT = 4;
	public static final short BONUS_BIT = 8;
	public static final short GOT_BIT = 16;
	public static final short PLATFORM_BIT = 32;
	public static final short SOLDIER_BIT = 64;
	public static final short DRON_BIT = 128;
	public static final short UFO_BIT = 256;
	public static final short BULLET_BIT = 512;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
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
