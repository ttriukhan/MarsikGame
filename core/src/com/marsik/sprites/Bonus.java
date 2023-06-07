package com.marsik.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.marsik.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.screens.PlayScreen;

import java.util.Random;

public class Bonus extends InteractiveTileObject{
    public Bonus(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.BONUS_BIT);
    }

    @Override
    public void touchToMarsik() {
        Gdx.app.log("bonus","you're stronger");
        setCategoryFilter(MarsikGame.GOT_BIT);
        for (TiledMapTileLayer.Cell cell : getCell())
            cell.setTile(null);

        Random ran = new Random();
        int i = ran.nextInt(0,3);
        if(i==0) {
            Hud.addBonus("HEALTH BONUS", 5);
            healthBonus();
        }
        if(i==1) {
            Hud.addBonus("RELOAD BONUS", 5);
            reloadBonus();
        }
        if(i==2) {
            Hud.addBonus("RESISTANCE BONUS", 5);
            resistance();
        }
    }

    private void resistance() {
    }

    private void reloadBonus() {
    }

    private void healthBonus() {
        Hud.healthChange(5);
    }

}
