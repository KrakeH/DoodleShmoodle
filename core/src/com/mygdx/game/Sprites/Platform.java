package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class Platform {
    private Texture platform;
    private Texture platDestroy;
    private Vector2 posPlat;
    private Random rand;
    private int platwidth = 120;
    private DestroyPlatform destroyPlatform;
    public boolean IsPlatDest = false;

    public Texture getPlatform() {
        return platform;
    }

    public Vector2 getPosPlat() {
        return posPlat;
    }

    private Platform() {
        rand = new Random();
    }

    public Platform(float y) {
        this();
        platform = new Texture("Platform.png");
        rand = new Random();
//        if (rand.nextInt(3) == 0) {
//            IsPlatDest = true;
//        } else {
//            IsPlatDest = false;
//        }
        posPlat = new Vector2(rand.nextInt(Main.WIDTH - platwidth), y);
    }

    public Platform(float x, float y) {
        this();
        platform = new Texture("Platform.png");
        posPlat = new Vector2(x, y);
    }

    public void fall() {
        posPlat.y -= 8;

    }
    public void generate(){
        if (posPlat.y < 0) {
            if (rand.nextInt(3) == 0) {
                IsPlatDest = true;
            } else {
                IsPlatDest = false;
            }
            posPlat = new Vector2(rand.nextInt(Main.WIDTH - platwidth), 1920 - 8);
        }
    }
}