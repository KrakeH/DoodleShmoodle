package com.mygdx.game.Sprites.Platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class CloudPlatform {
    private Texture CloudPlat;
    private Vector2 posPlatCloud;
    private Random rand;
    public boolean IsPlatCloud=false;

    public Texture getCloudPlatform() {
        return CloudPlat;
    }

    public Vector2 getPosPlatCloud() {
        return posPlatCloud;
    }

    private CloudPlatform() {
        rand = new Random();
    }

    public CloudPlatform(float y,float x) {
        this();
        CloudPlat = new Texture("PlatCloud.png");
        rand = new Random();
        posPlatCloud=new Vector2();
        float freeSpaceRight = Main.WIDTH - Main.platwidth - x - 50;
        posPlatCloud.y=y;
        if (freeSpaceRight > Main.platwidth) {
            posPlatCloud.x = x + Main.platwidth + 50 + rand.nextInt((int) freeSpaceRight - Main.platwidth);
        } else if (x - 50 > Main.platwidth) {
            posPlatCloud.x = rand.nextInt((int) (x - 50 - Main.platwidth));
        }
    }

    public void fall() {
        posPlatCloud.y -= 8;

    }
    public void generate(int x){
        if (posPlatCloud.y < 0) {
            posPlatCloud.y=Main.HEIGHT;
            IsPlatCloud = rand.nextInt(3) == 0;
            float freeSpaceRight = Main.WIDTH - Main.platwidth - x - 50;
            if (freeSpaceRight > Main.platwidth) {
                posPlatCloud.x = x + Main.platwidth + 50 + rand.nextInt((int) freeSpaceRight - Main.platwidth);
            } else if (x - 50 > Main.platwidth) {
                posPlatCloud.x = rand.nextInt(x - 50 - Main.platwidth);
            }
        }
    }
}
