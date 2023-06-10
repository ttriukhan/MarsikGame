package com.marsik.sprites.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.math.Vector2;
import com.marsik.screens.PlayScreen;
import com.marsik.sprites.Marsik;
import com.marsik.tools.MarsikGame;

public class SoldierBullet extends Bullet {

    public SoldierBullet(PlayScreen screen, float x, float y, boolean right) {
        super(screen, x, y, right, 1,5);
        setRegion(new TextureRegion(new Texture(Gdx.files.internal("soldierBullet.png"))));
        setBounds(0, 0, 6 / MarsikGame.PPM, 3 / MarsikGame.PPM);
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

        fdef.filter.categoryBits = MarsikGame.SOLDIER_BULLET_BIT;
        fdef.filter.maskBits = MarsikGame.MARSIK_BIT | MarsikGame.UFO_BIT;
        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);
    }

    public void hitMarsik() {
        if(screen.currentBonus != Marsik.BonusStatus.RESISTANCE) {

            Sound touchSound = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/hit.wav"));
            touchSound.play(0.5f);

            Sound auch = Gdx.audio.newSound(Gdx.files.internal("audio/sounds/auch.mp3"));
            auch.play(0.5f);

            screen.getPlayer().changeHealth(-10);
            screen.getPlayer().b2body.applyLinearImpulse(new Vector2(velocity.x/2, 0.5f), screen.getPlayer().b2body.getWorldCenter(), true);
        }
        destroy();
    }

}
