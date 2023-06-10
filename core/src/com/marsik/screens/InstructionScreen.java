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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import java.util.ArrayList;

public class InstructionScreen implements Screen {

    private final MarsikGame game;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private ArrayList<Integer> samples;

    private Stage stage;
    private ImageButton buttonBack;

    public InstructionScreen(final MarsikGame game, final ArrayList<Integer> samples) {
        this.game = game;
        this.samples = samples;
        batch = new SpriteBatch();
        backgroundImage = new Texture("instruction.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Texture playTexture = new Texture(Gdx.files.internal("buttonIn.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        buttonBack = new ImageButton(drawable);

        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game, samples));
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.bottom();
        table.add(buttonBack).pad(0,-270f, 30f,200f);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            System.out.println("back touch");
            game.setScreen(new MenuScreen(game, samples));
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