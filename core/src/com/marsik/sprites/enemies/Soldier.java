package com.marsik.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.marsik.sprites.items.Bullet;
import com.marsik.sprites.items.SoldierBullet;
import com.marsik.tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Soldier extends Enemy {

    private float timer;
    private TextureRegion right;
    private TextureRegion left;
    private TextureRegion rightFrozen;
    private TextureRegion leftFrozen;
    public boolean shooting;
    private boolean frozen;

    public Soldier(PlayScreen screen, float x, float y, float delta, float speed) {
        super(screen, x, y, delta, speed);
        right = new TextureRegion(new Texture(Gdx.files.internal("soldierRight.png")));
        left = new TextureRegion(new Texture(Gdx.files.internal("soldierLeft.png")));
        rightFrozen = new TextureRegion(new Texture(Gdx.files.internal("soldierRightFrozen.png")));
        leftFrozen = new TextureRegion(new Texture(Gdx.files.internal("soldierLeftFrozen.png")));
        shooting = false;
        frozen = false;
        setRegion(right);
        setBounds(0, 0, 16 / MarsikGame.PPM, 24 / MarsikGame.PPM);
    }

    public void update(float dt) {
        super.update(dt);
        timer+=dt;
        if(frozen) {
            b2body.setActive(false);
            if(timer>=5) {

                Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/unfreeze.wav"));
                touchSound.play();

                frozen = false;
                b2body.setActive(true);
                if(movingRight) setRegion(right);
                else setRegion(left);
            }
        }
        if(!shooting) b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() /2,b2body.getPosition().y - getHeight()/2);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        float[] vertices = {
                -8 / MarsikGame.PPM, -12 / MarsikGame.PPM, // bottom-left
                8 / MarsikGame.PPM, -12 / MarsikGame.PPM, // bottom-right
                8 / MarsikGame.PPM, 12 / MarsikGame.PPM, // top-right
                -8 / MarsikGame.PPM, 12 / MarsikGame.PPM // top-left
        };

        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.SOLDIER_BIT;
        fdef.filter.maskBits = MarsikGame.GROUND_BIT | MarsikGame.PLATFORM_BIT | MarsikGame.FREEZE_BULLET_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    protected void changeMove() {
        reverseVelocity(true, false);
        movingRight = !movingRight;
        if(movingRight) setRegion(right);
        else setRegion(left);
    }

    public void shoot() {
        if(!frozen) {
            if(!shooting) timer=0.5f;
            shooting = true;
            if (timer >= 1) {

                Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/sShot.wav"));
                touchSound.play();

                screen.getBullets().add(new SoldierBullet(screen, b2body.getPosition().x, b2body.getPosition().y, movingRight));
                timer = 0;
            }
        }
    }

    public void freeze() {

        Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/freeze.wav"));
        touchSound.play(0.5f);

        frozen = true;
        timer=0;
        if(movingRight) setRegion(rightFrozen);
        else setRegion(leftFrozen);
    }

}
