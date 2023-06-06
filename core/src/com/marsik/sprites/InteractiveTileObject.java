package com.marsik.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.MarsikGame;

import java.awt.*;

public class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(((bounds.getX() + bounds.getWidth()/2)/ MarsikGame.PPM), ((bounds.getY() + bounds.getHeight()/2)/MarsikGame.PPM));

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() /2/MarsikGame.PPM), (bounds.getHeight()/2/MarsikGame.PPM));
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
