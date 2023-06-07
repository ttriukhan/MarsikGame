package com.marsik.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.marsik.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Soldier extends Enemy {

    private Texture soldierTexture;
    private float stateTime;

    public Soldier(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        soldierTexture = new Texture(Gdx.files.internal("drone.gif"));
        TextureRegion soldierRegion = new TextureRegion(soldierTexture);
        setBounds(0, 0, 16 / MarsikGame.PPM, 16 / MarsikGame.PPM);
        setRegion(soldierRegion);
        stateTime = 0;
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() /2,b2body.getPosition().y - getHeight()/2);

    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(10*16/ MarsikGame.PPM, 5*16/MarsikGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Define the vertices of the polygon shape
        float[] vertices = {
                -8 / MarsikGame.PPM, -8 / MarsikGame.PPM, // bottom-left
                8 / MarsikGame.PPM, -8 / MarsikGame.PPM, // bottom-right
                8 / MarsikGame.PPM, 8 / MarsikGame.PPM, // top-right
                -8 / MarsikGame.PPM, 8 / MarsikGame.PPM // top-left
        };

        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.ENEMY_BIT;
        fdef.filter.maskBits = MarsikGame.GROUND_BIT | MarsikGame.ENEMY_BIT | MarsikGame.OBJECT_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData("soldier");
    }
}
