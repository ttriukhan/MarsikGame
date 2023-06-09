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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import tools.MarsikGame;
import com.marsik.screens.MenuScreen;

import java.security.Key;

public class WinScreen implements Screen {

    private final MarsikGame game;
    private SpriteBatch batch;
    private Texture backgroundImage;

    private Stage stage;
    private ImageButton buttonMenu;
    private ImageButton buttonNext;

    public WinScreen(final MarsikGame game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundImage = new Texture("ufo.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Texture playTexture = new Texture(Gdx.files.internal("sample1.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        buttonMenu = new ImageButton(drawable);

        Texture instrTexture = new Texture(Gdx.files.internal("sample2.png"));
        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(instrTexture));
        buttonNext = new ImageButton(drawable1);

        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("to menu touch");
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });

        buttonNext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("next touch");
                game.setScreen(new PlayScreen(game));
                dispose();
            }
        });

        stage.addActor(buttonMenu);
        stage.addActor(buttonNext);

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

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            System.out.println("next touch");
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            System.out.println("to menu touch");
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
        batch.dispose();
        backgroundImage.dispose();
    }
}