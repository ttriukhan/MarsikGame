package tools;

import com.badlogic.gdx.physics.box2d.*;
import com.marsik.sprites.enemies.Dron;
import com.marsik.sprites.interactive.InteractiveTileObject;
import com.marsik.sprites.items.Bullet;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {

            case MarsikGame.OBJECT_BIT | MarsikGame.MARSIK_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.OBJECT_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).touchToMarsik();
                else
                    ((InteractiveTileObject) fixB.getUserData()).touchToMarsik();
                contact.setEnabled(false);
                break;

            case MarsikGame.BULLET_BIT | MarsikGame.MARSIK_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.BULLET_BIT)
                    ((Bullet) fixA.getUserData()).hitMarsik();
                else
                    ((Bullet) fixB.getUserData()).hitMarsik();
                contact.setEnabled(false);
                break;

            case MarsikGame.DRON_BIT | MarsikGame.MARSIK_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.DRON_BIT)
                    ((Dron) fixA.getUserData()).hitMarsik();
                else
                    ((Dron) fixB.getUserData()).hitMarsik();
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
