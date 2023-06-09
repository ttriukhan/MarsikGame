package com.marsik.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.screens.PlayScreen;
import com.marsik.sprites.enemies.Dron;
import com.marsik.sprites.enemies.Soldier;
import com.marsik.sprites.interactive.Bonus;
import com.marsik.sprites.interactive.Platform;
import com.marsik.sprites.interactive.Sample;
import com.marsik.sprites.interactive.UFO;

import java.util.ArrayList;
import java.util.Random;

public class B2WorldCreator {

    private final ArrayList<Soldier> soldiers;
    private final ArrayList<Dron> drones;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(((rect.getX() + rect.getWidth()/2)/ MarsikGame.PPM), (rect.getY() + rect.getHeight()/2)/MarsikGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /2 /MarsikGame.PPM, rect.getHeight()/2 /MarsikGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            new Platform(screen, rect);
        }

        for(RectangleMapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            new UFO(screen, rect);
        }

        for(RectangleMapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            new Sample(screen, rect);
        }

        for(RectangleMapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            new Bonus(screen, rect);
        }

        Random ran = new Random();
        soldiers = new ArrayList<>();
        drones = new ArrayList<>();

        for(RectangleMapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            soldiers.add(new Soldier(screen, rect.getX() / MarsikGame.PPM, rect.getY() / MarsikGame.PPM, ran.nextFloat(3,6), ran.nextFloat(0.2f,1)));
        }

        for(RectangleMapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = object.getRectangle();

            drones.add(new Dron(screen, rect.getX() / MarsikGame.PPM, rect.getY() / MarsikGame.PPM, ran.nextFloat(3,6), ran.nextFloat(0.2f,1)));
        }

    }

    public ArrayList<Dron> getDrones() {
        return drones;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }
}
