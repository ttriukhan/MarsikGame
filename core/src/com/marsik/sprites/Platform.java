package com.marsik.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.marsik.screens.PlayScreen;

public class Platform extends InteractiveTileObject{
    public Platform(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void touchToMarsik() {
        Gdx.app.log("platform","get away");
    }

}
