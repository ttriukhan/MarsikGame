package com.marsik.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.marsik.tools.MarsikGame;
import com.marsik.screens.MenuScreen;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

public class MainScreen implements Screen {

    private final MarsikGame game;
    private Texture backgroundImage;


    public MainScreen(final MarsikGame game) {
        this.game = game;
        backgroundImage = new Texture("main.png");
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

        if (Gdx.input.justTouched()) {
            game.setScreen(new MenuScreen(game, new ArrayList<Integer>(Arrays.asList(0, 0, 0))));
            dispose();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game, new ArrayList<Integer>(Arrays.asList(0, 0, 0))));
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
        backgroundImage.dispose();
    }
}