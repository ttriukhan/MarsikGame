package com.marsik.sprites.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.marsik.sprites.Marsik;
import com.marsik.tools.MarsikGame;
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

        Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/bonus.wav"));
        touchSound.play();

        getCell().setTile(null);

        Random ran = new Random();
        int i = ran.nextInt(0,3);
        Marsik.BonusStatus bonus = null;
        if(i==0) bonus = Marsik.BonusStatus.HEALTH;
        if(i==1) bonus = Marsik.BonusStatus.RELOAD;
        if(i==2) bonus = Marsik.BonusStatus.RESISTANCE;
        if(bonus==screen.currentBonus) bonus = Marsik.BonusStatus.SAME;
        screen.currentBonus = bonus;
    }

}
