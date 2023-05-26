package com.mygdx.game.Sprites.Platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class DestroyPlatform {
    private Texture DestPlatform;
    private Random rand;
    public Vector2 posPlatDest;
    public boolean IsPlatDest = false;

    public Texture getDestPlatform() {
        return DestPlatform;
    }

    public Vector2 getPosPlatDest() {
        return posPlatDest;
    }

    private DestroyPlatform() {
        rand = new Random();
    }

    public DestroyPlatform(float y) {
        this();
        DestPlatform = new Texture("PlatDestroy.png");
        rand = new Random();
        posPlatDest=new Vector2(rand.nextInt(Main.WIDTH-Main.platwidth),y);
        IsPlatDest = rand.nextInt(3) == 0;
    }

    public void fall() {
        posPlatDest.y -= 8;
    }
    public void speedFall() {
        posPlatDest.y -= 16;
    }

    public void generate() {
        if (posPlatDest.y < 0) {
            IsPlatDest = rand.nextInt(3) == 0;
            posPlatDest=new Vector2(rand.nextInt(Main.WIDTH-Main.platwidth),Main.HEIGHT);
        }

    }
}
