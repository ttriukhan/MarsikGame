package com.marsik.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    protected float stateTime;
    protected float x;
    protected float y;
    protected float deltaX;
    protected boolean movingRight;

    public Enemy(PlayScreen screen, float x, float y, float deltaX, float speed) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.deltaX = deltaX;
        movingRight = true;
        defineEnemy();
        velocity = new Vector2(speed,0);
        stateTime = 0;
    }

    public void update(float dt) {
        stateTime += dt;
        if(movingRight) {
            if(b2body.getPosition().x>(x+deltaX*16/MarsikGame.PPM)) {
                changeMove();
            }
        } else {
            if(b2body.getPosition().x<x) {
                changeMove();
            }
        }
    }

    protected abstract void defineEnemy();
    protected abstract void changeMove();

    public void reverseVelocity(boolean x, boolean y) {
            if(x) velocity.x = -velocity.x;
            if(y) velocity.y = - velocity.y;
    }

    public boolean isMovingRight() {
        return movingRight;
    }
}
