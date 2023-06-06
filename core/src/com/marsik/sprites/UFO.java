package com.marsik.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class UFO extends InteractiveTileObject{
    public UFO(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
