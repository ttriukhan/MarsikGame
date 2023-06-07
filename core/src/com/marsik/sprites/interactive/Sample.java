package com.marsik.sprites.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import tools.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.screens.PlayScreen;

public class Sample extends InteractiveTileObject {
    public Sample(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.SAMPLE_BIT);
    }

    @Override
    public void touchToMarsik() {
        Gdx.app.log("sample","well done");
        setCategoryFilter(MarsikGame.GOT_BIT);
        for (TiledMapTileLayer.Cell cell : getCell())
            cell.setTile(null);
        Hud.addScore(1000);
    }



}
