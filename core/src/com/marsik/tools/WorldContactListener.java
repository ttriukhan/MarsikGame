package com.marsik.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.marsik.sprites.enemies.Dron;
import com.marsik.sprites.enemies.Soldier;
import com.marsik.sprites.interactive.InteractiveTileObject;
import com.marsik.sprites.items.FreezeBullet;
import com.marsik.sprites.items.SoldierBullet;

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

            case MarsikGame.UFO_BIT | MarsikGame.MARSIK_BIT:
            case MarsikGame.PLATFORM_BIT | MarsikGame.MARSIK_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.MARSIK_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).touchToMarsik();
                else
                    ((InteractiveTileObject) fixA.getUserData()).touchToMarsik();
                break;

            case MarsikGame.SOLDIER_BULLET_BIT | MarsikGame.MARSIK_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.SOLDIER_BULLET_BIT)
                    ((SoldierBullet) fixA.getUserData()).hitMarsik();
                else
                    ((SoldierBullet) fixB.getUserData()).hitMarsik();
                contact.setEnabled(false);
                break;

            case MarsikGame.SOLDIER_BULLET_BIT | MarsikGame.UFO_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.SOLDIER_BULLET_BIT)
                    ((SoldierBullet) fixA.getUserData()).destroy();
                else
                    ((SoldierBullet) fixB.getUserData()).destroy();
                contact.setEnabled(false);
                break;

            case MarsikGame.DRON_BIT | MarsikGame.MARSIK_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.DRON_BIT)
                    ((Dron) fixA.getUserData()).hitMarsik();
                else
                    ((Dron) fixB.getUserData()).hitMarsik();
                contact.setEnabled(false);
                break;

            case MarsikGame.FREEZE_BULLET_BIT | MarsikGame.SOLDIER_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.FREEZE_BULLET_BIT)
                    ((Soldier) fixB.getUserData()).freeze();
                else
                    ((Soldier) fixA.getUserData()).freeze();
                contact.setEnabled(false);

            case MarsikGame.FREEZE_BULLET_BIT | MarsikGame.PLATFORM_BIT:
            case MarsikGame.FREEZE_BULLET_BIT | MarsikGame.UFO_BIT:
            case MarsikGame.FREEZE_BULLET_BIT | MarsikGame.DRON_BIT:
                if(fixA.getFilterData().categoryBits == MarsikGame.FREEZE_BULLET_BIT)
                    ((FreezeBullet) fixA.getUserData()).destroy();
                else
                    ((FreezeBullet) fixB.getUserData()).destroy();
                contact.setEnabled(false);
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
