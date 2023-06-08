package com.marsik.sprites.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.marsik.scenes.Hud;
import com.marsik.sprites.Marsik;
import tools.MarsikGame;
import com.marsik.screens.PlayScreen;

public class Dron extends Enemy {

    public Dron(PlayScreen screen, float x, float y, float delta, float speed) {
        super(screen, x, y, delta,speed);
        Texture dronTexture = new Texture(Gdx.files.internal("drone.gif"));
        TextureRegion soldierRegion = new TextureRegion(dronTexture);
        setBounds(0, 0, 24 / MarsikGame.PPM, 24 / MarsikGame.PPM);
        setRegion(soldierRegion);
    }

    public void update(float dt) {
        super.update(dt);
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() /2,b2body.getPosition().y - getHeight()/2);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale=0;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Define the vertices of the polygon shape
        float[] vertices = {
                -8 / MarsikGame.PPM, -16 / MarsikGame.PPM, // bottom-left
                8 / MarsikGame.PPM, -16 / MarsikGame.PPM, // bottom-right
                8 / MarsikGame.PPM, 8 / MarsikGame.PPM, // top-right
                -8 / MarsikGame.PPM, 8 / MarsikGame.PPM // top-left
        };

        shape.set(vertices);

        fdef.filter.categoryBits = MarsikGame.DRON_BIT;
        fdef.filter.maskBits = MarsikGame.GROUND_BIT | MarsikGame.PLATFORM_BIT | MarsikGame.MARSIK_BIT | MarsikGame.FREEZE_BULLET_BIT;
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    protected void changeMove() {
        reverseVelocity(true, false);
        movingRight = !movingRight;
    }

    public void hitMarsik() {
        if(screen.currentBonus != Marsik.BonusStatus.RESISTANCE) {
            screen.getPlayer().changeHealth(-25);
            Body body = screen.getPlayer().b2body;
            if (screen.getPlayer().currentState== Marsik.State.RUNNING || screen.getPlayer().currentState== Marsik.State.JUMPING) {
                if(screen.getPlayer().movingRight) body.setLinearVelocity(new Vector2(-2, 2));
                else body.setLinearVelocity(new Vector2(2, 2));
            }
            if (screen.getPlayer().currentState== Marsik.State.STANDING) {
                if(movingRight) body.setLinearVelocity(new Vector2(2, 2));
                else body.setLinearVelocity(new Vector2(-2, 2));
            }
            if (screen.getPlayer().currentState== Marsik.State.FALLING) {
                body.setLinearVelocity(new Vector2(0, 3));
            }
        }
    }

}