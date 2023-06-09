package com.marsik.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.scenes.Hud;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Marsik extends Sprite {
    public enum State {JUMPING, FALLING, STANDING, RUNNING};
    public State currentState;
    public State previousState;

    public enum BonusStatus {NONE, HEALTH, RESISTANCE, RELOAD, SAME};

    public PlayScreen screen;
    public World world;
    public Body b2body;
    public boolean movingRight;
    private float stateTimer;
    private Integer health;

    public Marsik(PlayScreen screen) {
        super(new TextureRegion(new Texture(Gdx.files.internal("alien.png"))));
        this.screen = screen;
        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer= 0;
        movingRight = true;
        health = 100;

        defineMarsik();
        setBounds(0, 0, 16 / MarsikGame.PPM, 32 / MarsikGame.PPM);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        previousState = currentState;
        currentState = getState();
        movingRight = isMovingRight();
        checkStateTime(dt);
    }

    private void checkStateTime(float dt) {
        if(currentState==previousState) stateTimer+=dt;
        else stateTimer =0;
    }

    private boolean isMovingRight() {
        if(b2body.getLinearVelocity().x ==0 && movingRight)
            return true;
        return b2body.getLinearVelocity().x > 0;
    }

    public void changeHealth(int value) {
        Hud.healthChange(value);
        health+=value;
        if(health>100)
            health=100;
        if(health<=0)
            screen.endGame(false);
    }

    private State getState() {
        if(b2body.getLinearVelocity().y >0 )
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y <0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x!=0)
            return State.RUNNING;
        else return State.STANDING;
    }

    public void defineMarsik() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ MarsikGame.PPM, 32/MarsikGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Define the vertices of the polygon shape
        float[] vertices = {
                -8 / MarsikGame.PPM, -16 / MarsikGame.PPM, // bottom-left
                8 / MarsikGame.PPM, -16 / MarsikGame.PPM, // bottom-right
                8 / MarsikGame.PPM, 16 / MarsikGame.PPM, // top-right
                -8 / MarsikGame.PPM, 16 / MarsikGame.PPM // top-left
        };

        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.MARSIK_BIT;
        fdef.filter.maskBits = MarsikGame.GROUND_BIT | MarsikGame.PLATFORM_BIT | MarsikGame.OBJECT_BIT
                | MarsikGame.DRON_BIT | MarsikGame.UFO_BIT | MarsikGame.SOLDIER_BULLET_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

}
