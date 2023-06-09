package com.marsik.sprites.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import tools.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.screens.PlayScreen;

public class Sample extends InteractiveTileObject {
    public Sample(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.OBJECT_BIT);
    }

    @Override
    public void touchToMarsik() {
        setCategoryFilter(MarsikGame.GOT_BIT);

        Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/sample.wav"));
        touchSound.play();

        getCell().setTile(null);
        Hud.addScore(1);
    }

}
