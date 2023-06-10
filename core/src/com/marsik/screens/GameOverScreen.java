package com.marsik.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.marsik.tools.MarsikGame;
import com.marsik.screens.MenuScreen;

import java.security.Key;

public class GameOverScreen implements Screen {

    private final MarsikGame game;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private int level;

    private Stage stage;
    private ImageButton buttonRestart;
    private ImageButton buttonMenu;

    public GameOverScreen(final MarsikGame game, final int level) {
        this.game = game;
        this.level = level;
        batch = new SpriteBatch();
        backgroundImage = new Texture("gameover.jpg");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Texture instrTexture = new Texture(Gdx.files.internal("buttonGO1.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(instrTexture));
        buttonMenu = new ImageButton(drawable);

        Texture playTexture = new Texture(Gdx.files.internal("buttonGO2.png"));
        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(playTexture));
        buttonRestart = new ImageButton(drawable1);

        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });

        buttonRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game, level));
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.bottom();
        table.add(buttonMenu).pad(0,-150f,40f, 250f);
        table.add(buttonRestart).pad(0,250f,40f, -150f);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            game.setScreen(new PlayScreen(game, level));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        backgroundImage.dispose();
    }
}