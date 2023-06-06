package com.marsik.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.MarsikGame;

public class Marsik extends Sprite {

    public World world;
    public Body b2body;

    public Marsik(World world) {
        this.world = world;
        defineMarsik();
    }

    public void defineMarsik() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ MarsikGame.PPM, 32/MarsikGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MarsikGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
