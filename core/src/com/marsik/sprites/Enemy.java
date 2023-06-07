package com.marsik.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.marsik.MarsikGame;
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
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() /2,b2body.getPosition().y - getHeight()/2);
        if(movingRight) {
            if(b2body.getPosition().x>(x+deltaX*16/MarsikGame.PPM)) {
                reverseVelocity(true, false);
                movingRight = false;
            }
        } else {
            if(b2body.getPosition().x<x) {
                reverseVelocity(true, false);
                movingRight = true;
            }
        }
    }

    protected abstract void defineEnemy();

    public void reverseVelocity(boolean x, boolean y) {
            if(x) velocity.x = -velocity.x;
            if(y) velocity.y = - velocity.y;
    }
}
