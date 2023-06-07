package com.marsik.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.marsik.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.screens.PlayScreen;

import java.util.Random;

public class Bonus extends InteractiveTileObject{
    public Bonus(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.SAMPLE_BIT);
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
            Hud.addBonus(1, 5);
            bonus1();
        }
        if(i==1) {
            Hud.addBonus(2, 5);
            bonus2();
        }
        if(i==2) {
            Hud.addBonus(3, 5);
            bonus3();
        }
    }

    private void bonus3() {
    }

    private void bonus2() {
    }

    private void bonus1() {
    }

}
