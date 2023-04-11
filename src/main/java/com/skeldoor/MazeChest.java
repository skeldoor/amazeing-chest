package com.skeldoor;

import net.runelite.api.GameObject;
import net.runelite.api.coords.WorldPoint;

import java.time.Duration;
import java.time.Instant;

public class MazeChest {
    private static final Duration CHEST_OPEN_TIME = Duration.ofMinutes(1);

    private WorldPoint location;
    private Instant spawnTime;
    private GameObject gameObject;

    MazeChest(GameObject obj, WorldPoint loc, Instant time){
        this.gameObject = obj;
        this.location = loc;
        this.spawnTime = time;
    }

    GameObject getGameObject(){
        return gameObject;
    }

    Instant getSpawnTime(){
        return spawnTime;
    }

    Instant getDespawnTime(){
        return spawnTime.plus(CHEST_OPEN_TIME);
    }

    WorldPoint getLocation(){
        return location;
    }
}
