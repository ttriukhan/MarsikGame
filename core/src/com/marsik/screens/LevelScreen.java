package com.marsik.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import tools.MarsikGame;

public class LevelScreen implements Screen {

    private final MarsikGame game;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private int level;

    private Stage stage;
    private ImageButton buttonPlay;
    private ImageButton buttonMenu;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;

    public LevelScreen(final MarsikGame game, final int level) {
        this.game = game;
        this.level = level;
        batch = new SpriteBatch();
        if(level==0) backgroundImage = new Texture("l10.png");
        if(level==1) backgroundImage = new Texture("l20.png");
        if(level==2) backgroundImage = new Texture("l30.png");

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Texture playTexture = new Texture(Gdx.files.internal("buttonL1.png"));
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        buttonPlay = new ImageButton(drawable);

        Texture instrTexture = new Texture(Gdx.files.internal("buttonL2.png"));
        Drawable drawable1 = new TextureRegionDrawable(new TextureRegion(instrTexture));
        buttonMenu = new ImageButton(drawable1);

        Texture leftTexture = new Texture(Gdx.files.internal("buttonL3.png"));
        Drawable drawable2 = new TextureRegionDrawable(new TextureRegion(leftTexture));
        buttonLeft = new ImageButton(drawable2);

        Texture rightTexture = new Texture(Gdx.files.internal("buttonL4.png"));
        Drawable drawable3 = new TextureRegionDrawable(new TextureRegion(rightTexture));
        buttonRight = new ImageButton(drawable3);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("play touch");
                game.setScreen(new PlayScreen(game));

                dispose();
            }
        });

        buttonMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("menu touch");
                game.setScreen(new MenuScreen(game));

                dispose();
            }
        });

        buttonLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("left touch");
                if(level==1) {
                    game.setScreen(new LevelScreen(game, 0));
                    dispose();
                }
                if(level==2) {
                    game.setScreen(new LevelScreen(game, 1));
                    dispose();
                }
            }
        });

        buttonRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("right touch");
                if(level==0) {
                    game.setScreen(new LevelScreen(game, 1));
                    dispose();
                }
                if(level==1) {
                    game.setScreen(new LevelScreen(game, 2));
                    dispose();
                }
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.center();

        table.add(buttonLeft).pad(0,-200f,0,200f);
        table.add(buttonPlay).pad(0,100f,0,100f);
        table.add(buttonRight).pad(0,200f,0,-200f);

        Table table2 = new Table();
        table2.setFillParent(true);
        stage.addActor(table2);

        table2.bottom();
        table2.add(buttonMenu).padBottom(30f);

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


        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

            System.out.println("left touch");
            if(level==1) {
                game.setScreen(new LevelScreen(game, 0));
                dispose();
            }
            if(level==2) {
                game.setScreen(new LevelScreen(game, 1));
                dispose();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {

            System.out.println("right touch");
            if(level==0) {
                game.setScreen(new LevelScreen(game, 1));
                dispose();
            }
            if(level==1) {
                game.setScreen(new LevelScreen(game, 2));
                dispose();
            }


        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

            System.out.println("menu touch");
            game.setScreen(new MenuScreen(game));

            dispose();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

            System.out.println("play touch");
            game.setScreen(new PlayScreen(game));

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