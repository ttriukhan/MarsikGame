package com.marsik.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class MenuScreen implements Screen {

    private final MarsikGame game;
    private SpriteBatch batch;
    private Texture backgroundImage;

    private Stage stage;

    ImageButton buttonPlay;
    ImageButton buttonInstr;


    public MenuScreen(final MarsikGame game) {
        this.game = game;
        batch = new SpriteBatch();
        backgroundImage = new Texture("menu.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Texture playTexture = new Texture(Gdx.files.internal("buttonM1.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        buttonPlay = new ImageButton(drawable);
        buttonPlay.setWidth(60);
        buttonPlay.setHeight(40);

        Texture instrTexture = new Texture(Gdx.files.internal("buttonM2.png"));
        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(instrTexture));
        buttonInstr = new ImageButton(drawable1);
        buttonPlay.setWidth(60);
        buttonPlay.setHeight(40);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("play touch");
                game.setScreen(new LevelScreen(game, 0));
                dispose();
            }
        });

        buttonInstr.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("instr touch");
                game.setScreen(new InstructionScreen(game));
                dispose();
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.center();
        table.add(buttonPlay).padBottom(20f).row();
        table.add(buttonInstr);


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

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            System.out.println("play touch");
            game.setScreen(new LevelScreen(game, 0));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            System.out.println("instr touch");
            game.setScreen(new InstructionScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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