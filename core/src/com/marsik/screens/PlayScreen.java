package com.marsik.screens;

import com.badlogic.gdx.graphics.Texture;
import com.marsik.sprites.Dron;
import com.marsik.sprites.Enemy;
import com.marsik.sprites.Soldier;
import tools.B2WorldCreator;
import tools.WorldContactListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.marsik.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.sprites.Marsik;

public class PlayScreen implements Screen {

    private MarsikGame game;

    private Texture mTexture;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Marsik player;

    public PlayScreen(MarsikGame game) {
        mTexture = new Texture(Gdx.files.internal("alien.png"));

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MarsikGame.V_WIDTH / MarsikGame.PPM, MarsikGame.V_HEIGHT / MarsikGame.PPM, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MarsikGame.PPM);
        gameCam.position.set(gamePort.getScreenWidth()/2, gamePort.getScreenHeight()/2, 0);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        player = new Marsik(this);

        world.setContactListener(new WorldContactListener());
    }

    public Texture getTexture() {
        return mTexture;
    }

    @Override
    public void show() {

    }

    private void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed((Input.Keys.UP)))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);

        for(Dron dron : creator.getDrons())
            dron.update(dt);
        for(Soldier sold : creator.getSoldiers())
            sold.update(dt);

        hud.update(dt);

        gameCam.position.x = player.b2body.getPosition().x;

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        for(Dron dron : creator.getDrons())
            dron.draw(game.batch);
        for(Soldier sold : creator.getSoldiers())
            sold.draw(game.batch);

        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}