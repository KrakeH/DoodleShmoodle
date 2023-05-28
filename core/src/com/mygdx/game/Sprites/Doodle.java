package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Main;

public class Doodle {
    public static final int Gravity = -20;
    private Vector3 position;
    private Vector3 velocity;
    private float Timer=3;
    private Texture doodleRight;
    private Texture doodleLeft;
    private Texture doodleRightFly;
    private Texture doodleLeftFly;
    private Texture doodle;
    public boolean HaveCap=false;
    public Doodle(float x, float y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        doodleRight = new Texture("DoodleJumpLeft.png");
        doodleLeft = new Texture("DoodleJumpRight.png");
        doodleRightFly = new Texture("DoodleJumpLeftFly.png");
        doodleLeftFly = new Texture("DoodleJumpRightFly.png");
        doodle = new Texture("DoodleJumpLeft.png");
    }

    public Vector3 getPosition() {
        return position;
    }
    public float getTimer() {
        return Timer;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Texture getDoodle() {
        return doodle;
    }
    public Texture getDoodleRight() {
        return doodleRight;
    }
    public Texture getDoodleLeft() {
        return doodleLeft;
    }
    public Texture getDoodleRightFly() {
        return doodleRightFly;
    }
    public Texture getDoodleLeftFly() {
        return doodleLeftFly;
    }

    public void update(float dt,float gyroY) {
        velocity.add(0, Gravity, 0);
        velocity.scl(dt);
        position.add(0, velocity.y, 0);
        velocity.scl(1 / dt);

        if (position.x > Main.WIDTH) {
            position.x = 0;
        }
        if (position.x < -doodle.getWidth()) {
            position.x = Main.WIDTH;
        }
    }

    public void jump() {
        velocity.y = 800;
    }//

    public void move(float x) {
        position.x -= x;
    }

    public void fall() {
        position.y -= 8;
    }

    public void fly(float dt){
        Timer-=dt;
        velocity.add(0,-Gravity, 0);
        velocity.scl(dt);
        position.add(0, velocity.y, 0);
        position.y+=2;
        velocity.y=0;
    }
    public void resetTimer(){
        Timer=3;
    }
}