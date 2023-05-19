package com.mygdx.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    public OrthographicCamera camera;
    public Vector3 mouse;
    public GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm=gsm;
        camera=new OrthographicCamera();
        mouse= new Vector3();
    }
    public abstract void handleInpute();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

}