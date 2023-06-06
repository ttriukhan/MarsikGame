package com.marsik.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.MarsikGame;
import com.marsik.screens.PlayScreen;

import java.awt.*;

public class Marsik extends Sprite {

    public World world;
    public Body b2body;

    public Marsik(World world, PlayScreen screen) {
        super(new TextureRegion(screen.getTexture()));
        this.world = world;
        defineMarsik();
        setBounds(0, 0, 16 / MarsikGame.PPM, 32 / MarsikGame.PPM);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
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

        fdef.shape = shape;

        b2body.createFixture(fdef);

        // Dispose the shape after creating the fixture
        shape.dispose();
    }
}
