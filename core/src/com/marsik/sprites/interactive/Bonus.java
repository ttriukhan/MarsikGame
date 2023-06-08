package com.marsik.sprites.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.marsik.sprites.Marsik;
import tools.MarsikGame;
import com.marsik.scenes.Hud;
import com.marsik.screens.PlayScreen;

import java.util.Random;

public class Bonus extends InteractiveTileObject {

    public Bonus(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarsikGame.OBJECT_BIT);
    }

    @Override
    public void touchToMarsik() {
        setCategoryFilter(MarsikGame.GOT_BIT);
        getCell().setTile(null);

        Random ran = new Random();
        int i = ran.nextInt(0,3);
        if(i==0) {
            Hud.addBonus("HEALTH BONUS", 5);
            screen.currentBonus = Marsik.BonusStatus.HEALTH;
            screen.bonusTime = 5;
        }
        if(i==1) {
            Hud.addBonus("RELOAD BONUS", 20);
            screen.currentBonus = Marsik.BonusStatus.RELOAD;
            screen.bonusTime = 20;
        }
        if(i==2) {
            Hud.addBonus("RESISTANCE BONUS", 10);
            screen.currentBonus = Marsik.BonusStatus.RESISTANCE;
            screen.bonusTime = 10;
        }
    }

}
