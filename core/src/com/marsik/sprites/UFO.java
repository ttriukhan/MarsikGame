package com.marsik.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.marsik.MarsikGame;
import com.marsik.screens.PlayScreen;

public class UFO extends InteractiveTileObject{
    public UFO(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.UFO_BIT);
    }

    @Override
    public void touchToMarsik() {
        Gdx.app.log("ufo","the end");
    }

}
