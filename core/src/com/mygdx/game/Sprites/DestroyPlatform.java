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

    public DestroyPlatform(float y,float x) {
        this();
        DestPlatform = new Texture("PlatDestroy.png");
        rand = new Random();

        posPlatDest=new Vector2();

        float freeSpaceRight = Main.WIDTH - platwidth - x - 50;
        posPlatDest.y=y;
        if (freeSpaceRight > platwidth) {
            posPlatDest.x = x + platwidth + 50 + rand.nextInt((int) freeSpaceRight - platwidth);
        } else if (x - 50 > platwidth) {
            posPlatDest.x = rand.nextInt((int) (x - 50 - platwidth));
        }

        IsPlatDest = rand.nextInt(3) == 0;
    }

    public void fall() {
        posPlatDest.y -= 8;
    }

    public void generate(int x) {
        if (posPlatDest.y < 0) {
            IsPlatDest = rand.nextInt(3) == 0;
            posPlatDest.y = 1920 - 8;
            float freeSpaceRight = Main.WIDTH - platwidth - x - 50;
            if (freeSpaceRight > platwidth) {
                posPlatDest.x = x + platwidth + 50 + rand.nextInt((int) freeSpaceRight - platwidth);
            } else if (x - 50 > platwidth) {
                posPlatDest.x = rand.nextInt(x - 50 - platwidth);
            }
        }

    }
}
