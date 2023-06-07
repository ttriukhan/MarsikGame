package com.marsik.sprites.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Platform extends InteractiveTileObject {
    public Platform(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.PLATFORM_BIT);
    }

    @Override
    public void touchToMarsik() {
        Gdx.app.log("platform","get away");
    }

}
