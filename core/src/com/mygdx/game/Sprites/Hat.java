package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.util.Random;

public class Hat {
    private Texture Hat;
    private Vector2 posHat;
    private Random rand;
    public boolean IsHat=false;

    public Texture getHat() {
        return Hat;
    }

    public Vector2 getPosHat() {
        return posHat;
    }

    private Hat() {
        rand = new Random();
    }

    public Hat(float y,float x) {
        this();
        Hat = new Texture("Cap.png");
        rand = new Random();
        posHat = new Vector2(x,y+30);
    }

    public void fall() {
        posHat.y -= 8;
    }
    public void speedFall() {
        posHat.y -= 16;
    }
    public void generate(float x,float y){
        if (posHat.y < 0) {
            IsHat=rand.nextInt(100) == 0;
            posHat = new Vector2(x+Main.platwidth/2-Hat.getWidth()/2,y+30);
        }
    }
}
