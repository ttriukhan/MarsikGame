package com.marsik.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Soldier extends Enemy {

    private float timer;

    public Soldier(PlayScreen screen, float x, float y, float delta, float speed) {
        super(screen, x, y, delta, speed);
        Texture soldierTexture = new Texture(Gdx.files.internal("soldier.png"));
        TextureRegion soldierRegion = new TextureRegion(soldierTexture);
        setBounds(0, 0, 16 / MarsikGame.PPM, 24 / MarsikGame.PPM);
        setRegion(soldierRegion);
    }

    public void update(float dt) {
        super.update(dt);
        timer+=dt;
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Define the vertices of the polygon shape
        float[] vertices = {
                -8 / MarsikGame.PPM, -12 / MarsikGame.PPM, // bottom-left
                8 / MarsikGame.PPM, -12 / MarsikGame.PPM, // bottom-right
                8 / MarsikGame.PPM, 12 / MarsikGame.PPM, // top-right
                -8 / MarsikGame.PPM, 12 / MarsikGame.PPM // top-left
        };

        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.SOLDIER_BIT;
        fdef.filter.maskBits = MarsikGame.GROUND_BIT | MarsikGame.PLATFORM_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData("soldier");
    }

    public void shoot(float x, float y, boolean right) {
        if(timer>=1) {
            Gdx.app.log("soldier", "shooting");
            timer=0;
        }
        b2body.setActive(false);
    }
}
