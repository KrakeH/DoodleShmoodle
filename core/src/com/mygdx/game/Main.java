package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;

public class Main extends ApplicationAdapter {

    public static final int platwidth =120;
    public static int WIDTH = 1080;
    public static int HEIGHT = 1920;
    private GameStateManager gsm;
    SpriteBatch batch;
    Texture img;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager();
        gsm.push(new MenuState(gsm));
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}