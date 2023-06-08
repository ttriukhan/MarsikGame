package com.marsik.sprites.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.screens.PlayScreen;

public abstract class Bullet extends Sprite {

    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected Body body;
    protected boolean toDestroy;
    protected boolean destroyed;

    public Bullet(PlayScreen screen, float x, float y, boolean right, float speed) {
        super();
        this.screen = screen;
        this.world = screen.getWorld();
        toDestroy = false;
        destroyed = false;
        setPosition(x,y);
        defineItem();
        if(right) velocity = new Vector2(speed,0);
        else velocity = new Vector2(-speed,0);
    }

    public void update(float dt) {
        if(toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
        if(!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            body.setLinearVelocity(velocity);
        }
    }

    public abstract void defineItem();

    public void destroy(){
        toDestroy = true;
    }

    public void draw(Batch batch) {
        if(!destroyed)
            super.draw(batch);
    }

}
