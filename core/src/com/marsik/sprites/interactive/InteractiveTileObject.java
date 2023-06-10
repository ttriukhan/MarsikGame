package com.marsik.sprites.interactive;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public abstract class InteractiveTileObject {

    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, Rectangle bounds) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(((bounds.getX() + bounds.getWidth()/2)/ MarsikGame.PPM), ((bounds.getY() + bounds.getHeight()/2)/MarsikGame.PPM));

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() /2/MarsikGame.PPM), (bounds.getHeight()/2/MarsikGame.PPM));
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void touchToMarsik();

    public void setCategoryFilter(short filterBit) {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(4);
        int x = (int) (body.getPosition().x * MarsikGame.PPM / 16);
        int y = (int) (body.getPosition().y * MarsikGame.PPM / 16);
        return layer.getCell(x, y);
    }
}
