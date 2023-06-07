package com.marsik.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.marsik.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Dron extends Enemy {

    private float stateTime;

    public Dron(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setBounds(getX(), getY(), 16 / MarsikGame.PPM, 16 / MarsikGame.PPM);
        stateTime = 0;
    }

    public void update(float dt) {
        stateTime += dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    @Override
    protected void defineEnemy() {
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

        fdef.filter.categoryBits = MarsikGame.ENEMY_BIT;
        fdef.filter.maskBits = MarsikGame.GROUND_BIT | MarsikGame.SAMPLE_BIT | MarsikGame.BONUS_BIT
                | MarsikGame.ENEMY_BIT | MarsikGame.OBJECT_BIT;

        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData("marsik");
    }
}
