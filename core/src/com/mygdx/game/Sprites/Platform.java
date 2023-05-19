package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class Platform {
    private Texture platform;
    private Vector2 posPlat;
    private Random rand;

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
        rand = new Random();
        posPlat = new Vector2(rand.nextInt(Main.WIDTH - 120), y);
    }
    public Platform(float x,float y) {
        this();
        platform = new Texture("Platform.png");
        posPlat = new Vector2(x, y);
    }

    public void fall(float dt) {
        posPlat.y -= 8;
        if(posPlat.y < 0) {
            posPlat.y = 1920-8;
            posPlat = new Vector2(rand.nextInt(Main.WIDTH - 120), 1920-8);
        }
    }
}