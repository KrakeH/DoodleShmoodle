package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class Platform {
    private Texture platform;
    private Texture platdestroy;
    private Vector2 posPlat,posPlatDest;
    private Random rand;
    private int platwidth=150;

    public Texture getPlatform() {
        return platform;
    }

    public Vector2 getPosPlat() {
        return posPlat;
    }

    private Platform(){
        rand = new Random();
    }

    public Platform(float y) {
        this();
        platform = new Texture("Platform.png");
        platdestroy= new Texture("Platform.png");
        rand = new Random();
        posPlat = new Vector2(rand.nextInt(Main.WIDTH - platwidth), y);
    }
    public Platform(float x,float y) {
        this();
        platform = new Texture("Platform.png");
        posPlat = new Vector2(x, y);
    }

    public void fall(float dt) {
        posPlat.y -= 8;
        if(posPlat.y < 0) {
            if(rand.nextInt(5)==0){
                posPlat.y = 1920 - 8;
                posPlat = new Vector2(rand.nextInt(Main.WIDTH - platwidth), 1920 - 8);
                if(rand.nextInt(2)==0) {
                    posPlatDest = new Vector2(rand.nextInt((int) (Main.WIDTH - posPlat.x-platform.getWidth()-30))+posPlat.x+platform.getWidth()+30, 1920 - 8);
                }
                else{
                    posPlatDest = new Vector2(rand.nextInt((int) (posPlat.x-platwidth-30)), 1920 - 8);
                }
            }
            else {
                posPlat.y = 1920 - 8;
                posPlat = new Vector2(rand.nextInt(Main.WIDTH - platwidth), 1920 - 8);
            }
        }
    }
}