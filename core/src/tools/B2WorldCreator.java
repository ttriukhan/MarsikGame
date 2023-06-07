package tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.marsik.MarsikGame;
import com.marsik.screens.PlayScreen;
import com.marsik.sprites.*;

import java.util.ArrayList;
import java.util.Random;

public class B2WorldCreator {

    private ArrayList<Soldier> soldiers;
    private ArrayList<Dron> drons;

    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(((rect.getX() + rect.getWidth()/2)/ MarsikGame.PPM), (rect.getY() + rect.getHeight()/2)/MarsikGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() /2 /MarsikGame.PPM, rect.getHeight()/2 /MarsikGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Platform(screen, rect);
        }

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new UFO(screen, rect);
        }

        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Sample(screen, rect);
        }

        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Bonus(screen, rect);
        }

        Random ran = new Random();
        soldiers = new ArrayList<>();
        drons = new ArrayList<>();

        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            soldiers.add(new Soldier(screen, rect.getX() / MarsikGame.PPM, rect.getY() / MarsikGame.PPM, ran.nextFloat(3,6), ran.nextFloat(0.2f,1)));
        }

        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            drons.add(new Dron(screen, rect.getX() / MarsikGame.PPM, rect.getY() / MarsikGame.PPM, ran.nextFloat(3,6), ran.nextFloat(0.2f,1)));
        }

    }

    public ArrayList<Dron> getDrons() {
        return drons;
    }

    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }
}
