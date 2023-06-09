package com.marsik.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.marsik.tools.MarsikGame;
import java.util.ArrayList;

public class GameOverScreen implements Screen {

    private final MarsikGame game;
    private final Texture backgroundImage;
    private final int level;
    private final ArrayList<Integer> samples;

    private final Stage stage;

    public GameOverScreen(final MarsikGame game, final int level, final ArrayList<Integer> samples) {
        this.game = game;
        this.level = level;
        this.samples = samples;
        backgroundImage = new Texture("gameover.jpg");

        stage = new Stage(game.gamePort);

        Texture instrTexture = new Texture(Gdx.files.internal("buttonGO1.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(instrTexture));
        ImageButton buttonMenu = new ImageButton(drawable);

        Texture playTexture = new Texture(Gdx.files.internal("buttonGO2.png"));
        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(playTexture));
        ImageButton buttonRestart = new ImageButton(drawable1);

        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, samples));
                dispose();
            }
        });

        buttonRestart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game, level, samples));
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

        game.batch.setProjectionMatrix(game.gameCam.combined);

        game.batch.begin();
        game.batch.draw(backgroundImage, 0, 0);
        game.batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            game.setScreen(new PlayScreen(game, level, samples));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new MenuScreen(game, samples));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.gamePort.update(width, height);
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
        backgroundImage.dispose();
    }
}