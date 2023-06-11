package com.marsik.sprites.interactive;

import com.badlogic.gdx.math.Rectangle;
import com.marsik.screens.PlayScreen;
import com.marsik.tools.MarsikGame;

public class Platform extends InteractiveTileObject {
    public Platform(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.PLATFORM_BIT);
    }

    @Override
    public void touchToMarsik() {
    }

}
