package tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.scenes.Hud;
import com.marsik.sprites.InteractiveTileObject;
import com.marsik.MarsikGame;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData()=="marsik" || fixB.getUserData() == "marsik") {
            Fixture marsik = fixA.getUserData() == "marsik" ? fixA : fixB;
            Fixture object = marsik == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).touchToMarsik();
                contact.setEnabled(false);
            }
        }

        switch (cDef) {
            case MarsikGame.DRON_BIT | MarsikGame.MARSIK_BIT:
                Gdx.app.log("marsik","died");
                Hud.healthChange(-10);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
