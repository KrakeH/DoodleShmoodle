package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class DestroyPlatform {
    private Texture DestPlatform;
    private Random rand;
    private int platwidth = 120;
    public Vector2 posPlatDest;

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
        posPlatDest = new Vector2(rand.nextInt(Main.WIDTH - platwidth), y);
    }

    public void fall() {
        posPlatDest.y -= 8;
    }

    public void generate(int x) {
        if(posPlatDest.y<0) {
            posPlatDest.x=rand.nextInt(Main.WIDTH-2*platwidth)+x;
            if(posPlatDest.x>Main.WIDTH){
                posPlatDest.x-=Main.WIDTH;
            } else if (posPlatDest.x>Main.WIDTH-platwidth) {
                posPlatDest.x=0;
            }
        }

    }
}
