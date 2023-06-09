package com.marsik.sprites.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.marsik.screens.PlayScreen;
import tools.MarsikGame;

public class FreezeBullet extends Bullet {

    public FreezeBullet(PlayScreen screen, float x, float y, boolean right) {
        super(screen, x, y, right, 4,0.5);
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("freezeBullet.png"))));
        setBounds(0, 0, 32 / MarsikGame.PPM, 32 / MarsikGame.PPM);
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
                -4 / MarsikGame.PPM, -4 / MarsikGame.PPM, // bottom-left
                4 / MarsikGame.PPM, -4 / MarsikGame.PPM, // bottom-right
                4 / MarsikGame.PPM, 4 / MarsikGame.PPM, // top-right
                -4 / MarsikGame.PPM, 4 / MarsikGame.PPM // top-left
        };
        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.FREEZE_BULLET_BIT;
        fdef.filter.maskBits = MarsikGame.SOLDIER_BIT | MarsikGame.DRON_BIT | MarsikGame.PLATFORM_BIT | MarsikGame.UFO_BIT;
        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);
    }

}
