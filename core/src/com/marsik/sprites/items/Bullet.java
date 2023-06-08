package com.marsik.sprites.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.scenes.Hud;
import com.marsik.screens.PlayScreen;
import tools.MarsikGame;

public class Bullet extends Sprite {

    private PlayScreen screen;
    private World world;
    private Vector2 velocity;
    private Body body;


    public Bullet(PlayScreen screen, float x, float y, boolean right) {
        super();
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        defineItem();
        if(right) velocity = new Vector2(1,0);
        else velocity = new Vector2(-1,0);
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("bullet.png"))));
        setBounds(0, 0, 6 / MarsikGame.PPM, 3 / MarsikGame.PPM);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() /2,body.getPosition().y - getHeight()/2);
        body.setLinearVelocity(velocity);
    }

    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale=0;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        float[] vertices = {
                -1 / MarsikGame.PPM, -1 / MarsikGame.PPM, // bottom-left
                1 / MarsikGame.PPM, -1 / MarsikGame.PPM, // bottom-right
                1 / MarsikGame.PPM, 1 / MarsikGame.PPM, // top-right
                -1 / MarsikGame.PPM, 1 / MarsikGame.PPM // top-left
        };
        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.BULLET_BIT;
        fdef.filter.maskBits = MarsikGame.MARSIK_BIT;
        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);
    }

    public void touchToMarsik() {
        Hud.healthChange(-10);
        //world.destroyBody(body);
        screen.getBullets().remove(this);
    }

}
