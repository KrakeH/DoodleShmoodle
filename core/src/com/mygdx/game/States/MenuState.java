package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Main;

public class MenuState extends State {

    private Texture background;
    private Texture playButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, Main.WIDTH,Main.HEIGHT);
        background = new Texture("background.png");
        playButton = new Texture("playbtn.png");
    }

    @Override
    public void handleInpute() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInpute();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background,0 ,0,Main.WIDTH,Main.HEIGHT);
        sb.draw(playButton, Main.WIDTH /2-playButton.getWidth()/2,Main.HEIGHT/2-playButton.getHeight()/2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}