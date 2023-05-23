package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Main;

public class Doodle {
    public static final int Gravity = -20;
    private Vector3 position;
    private Vector3 velocity;

    private Texture doodle;
    private boolean haveHat=false;
    public Doodle(float x, float y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        doodle = new Texture("DoodleJump.png");
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public Texture getDoodle() {
        return doodle;
    }

    public void update(float dt) {
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
}