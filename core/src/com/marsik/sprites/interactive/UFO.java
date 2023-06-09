package com.marsik.sprites.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.marsik.sprites.Marsik;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class UFO extends InteractiveTileObject {
    public UFO(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.UFO_BIT);
    }

    @Override
    public void touchToMarsik() {
        screen.endGame(true);
    }

}
