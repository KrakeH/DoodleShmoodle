package com.mygdx.game.Sprites.Platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class Platform {
    private Texture platform;
    private Vector2 posPlat;
    private Random rand;
    public boolean IsPlat = true;

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
        posPlat = new Vector2(rand.nextInt(Main.WIDTH - Main.platwidth), y);
    }

    public Platform(float x, float y) {
        this();
        platform = new Texture("Platform.png");
        posPlat = new Vector2(x, y);
    }

    public void fall() {
        posPlat.y -= 8;
    }

    public void speedFall() {
        posPlat.y -= 16;
    }

    public void generate() {
        if (posPlat.y < 0) {
            IsPlat = true;
            posPlat = new Vector2(rand.nextInt(Main.WIDTH - Main.platwidth), Main.HEIGHT);
        }
    }
}