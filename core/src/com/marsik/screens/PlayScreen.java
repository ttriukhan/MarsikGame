package com.marsik.screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.marsik.sprites.enemies.Dron;
import com.marsik.sprites.enemies.Soldier;
import com.marsik.sprites.items.Bullet;
import com.marsik.sprites.items.FreezeBullet;
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
import tools.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.sprites.Marsik;

import java.util.ArrayList;

public class PlayScreen implements Screen {

    private MarsikGame game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private float mapWidth;
    private float mapHeight;

    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Marsik player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> freezeBullets;

    public Marsik.BonusStatus currentBonus;
    public Marsik.BonusStatus previousBonus;
    public int bonusTime;
    private float bonusTimer;
    private float healthBonusTimer;

    private float reloadTimer;
    private float reloadTime;

    private Music backgroundMusic;


    public PlayScreen(MarsikGame game) {

        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(MarsikGame.V_WIDTH / MarsikGame.PPM, MarsikGame.V_HEIGHT / MarsikGame.PPM, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MarsikGame.PPM);
        gameCam.position.set(gamePort.getScreenWidth()/2f, gamePort.getScreenHeight()/2f, 0);

        mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class) /MarsikGame.PPM;
        mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class) /MarsikGame.PPM;

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        creator = new B2WorldCreator(this);

        player = new Marsik(this);
        bullets = new ArrayList<>();
        freezeBullets = new ArrayList<>();

        currentBonus = Marsik.BonusStatus.NONE;
        previousBonus = Marsik.BonusStatus.NONE;
        bonusTime = 0;
        bonusTimer = 0;
        healthBonusTimer = 0;

        reloadTime = 2;
        reloadTimer = reloadTime;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/marsik.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.2f);

        world.setContactListener(new WorldContactListener());
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Marsik getPlayer() {
        return player;
    }

    @Override
    public void show() {
        backgroundMusic.play();
    }

    private void handleInput(float dt) {
        if(currentBonus== Marsik.BonusStatus.RELOAD) reloadTimer+=3*dt;
        else reloadTimer+=dt;

        if((Gdx.input.isKeyJustPressed((Input.Keys.UP)) || Gdx.input.isKeyJustPressed((Input.Keys.W))) && player.currentState!= Marsik.State.FALLING && player.currentState!= Marsik.State.JUMPING) {

            Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/jump.wav"));
            touchSound.play();

            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed((Input.Keys.D))) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.08f, 0), player.b2body.getWorldCenter(), true);
        if((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed((Input.Keys.A))) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.08f, 0), player.b2body.getWorldCenter(), true);
        if((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) && reloadTimer>=reloadTime) {

            Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/mShot.wav"));
            touchSound.play();

            freezeBullets.add(new FreezeBullet(this, player.b2body.getPosition().x, player.b2body.getPosition().y, player.runningRight));
            reloadTimer=0;
        }

        float clampedX = MathUtils.clamp(player.b2body.getPosition().x, player.getWidth()/2, mapWidth-player.getWidth()/2);
        float clampedY = MathUtils.clamp(player.b2body.getPosition().y, 0, mapHeight+player.getHeight());

        player.b2body.setTransform(clampedX, clampedY, player.b2body.getAngle());
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);

        bonusUpdate(dt);

        for (Dron dron : creator.getDrons())
            dron.update(dt);

        for (Soldier sold : creator.getSoldiers()) {
            sold.update(dt);
            if (sold.getY() >= player.getY() - player.getHeight() && sold.getY() <= player.getY() + player.getHeight()) {
                if (!sold.isMovingRight()) {
                    if (sold.getX() > player.getX() && sold.getX() - player.getX() < 10 * 16 / MarsikGame.PPM)
                        sold.shoot();
                    else sold.shooting = false;
                } else {
                    if (sold.getX() < player.getX() && player.getX() - sold.getX() < 10 * 16 / MarsikGame.PPM)
                        sold.shoot();
                    else sold.shooting = false;
                }
            } else sold.shooting = false;
        }

        for (Bullet bullet : bullets)
            bullet.update(dt);

        for (Bullet bullet : freezeBullets)
            bullet.update(dt);

        hud.update(dt);

        float leftBoundary = player.b2body.getPosition().x - gamePort.getWorldWidth() / 2;
        float rightBoundary = player.b2body.getPosition().x + gamePort.getWorldWidth() / 2;

        if (leftBoundary < 0) {
            gameCam.position.x = gamePort.getWorldWidth() / 2;
        } else if (rightBoundary > mapWidth) {
            gameCam.position.x = mapWidth - gamePort.getWorldWidth() / 2;
        } else {
            gameCam.position.x = player.b2body.getPosition().x;
        }

        gameCam.update();
        renderer.setView(gameCam);
    }

    private void bonusUpdate(float dt) {
        if(currentBonus!=previousBonus) {
            bonusTimer = 0;
            if(currentBonus == Marsik.BonusStatus.SAME) {
                currentBonus = previousBonus;
            }
            if(currentBonus== Marsik.BonusStatus.HEALTH) {
                bonusTime = 5;
                healthBonusTimer = 0;
                Hud.addBonus("HEALTH BONUS", 5);
            }
            if(currentBonus== Marsik.BonusStatus.RELOAD) {
                bonusTime = 20;
                Hud.addBonus("RELOAD BONUS", 20);
            }
            if(currentBonus== Marsik.BonusStatus.RESISTANCE) {
                bonusTime = 10;
                Hud.addBonus("RESISTANCE BONUS", 10);
            }
        }

        if(currentBonus== Marsik.BonusStatus.HEALTH) {
            healthBonusTimer+=dt;
            if(healthBonusTimer>=1) {
                healthBonusTimer=0;
                player.changeHealth(10);
            }
        }

        previousBonus = currentBonus;
        if(currentBonus != Marsik.BonusStatus.NONE) bonusTimer += dt;
        if(bonusTimer>=bonusTime) currentBonus = Marsik.BonusStatus.NONE;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        for(Dron dron : creator.getDrons())
            dron.draw(game.batch);

        for(Soldier sold : creator.getSoldiers())
            sold.draw(game.batch);

        for(Bullet bullet : bullets)
            bullet.draw(game.batch);

        for(Bullet bullet : freezeBullets)
            bullet.draw(game.batch);

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
        backgroundMusic.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

}