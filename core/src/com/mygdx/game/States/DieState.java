package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Doodle;
import com.mygdx.game.Sprites.Platform;

public class DieState extends State{
    private Texture background;
    private Texture playButton;
    private Doodle doodle;
    BitmapFont font;
    Texture doodletexture=new Texture("DoodleJump.png");

    public DieState(GameStateManager gsm) {
        super(gsm);
        doodle = new Doodle(Main.WIDTH/2-doodletexture.getWidth()/2, 1920);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        background = new Texture("background.png");
        playButton = new Texture("playbtn.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TNR.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=60;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();
    }

    @Override
    public void handleInpute() {
        if(Gdx.input.justTouched() && doodle.getPosition().y<=0){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInpute();
        doodle.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.draw(background,0 ,0,Main.WIDTH,Main.HEIGHT);
        sb.draw(doodle.getDoodle(), doodle.getPosition().x, doodle.getPosition().y);
        font.draw(sb,"Your record: "+String.valueOf(0),Main.WIDTH/2,Main.HEIGHT/2);
        if(doodle.getPosition().y<0) {
            sb.draw(playButton, Main.WIDTH / 2 - playButton.getWidth() / 2, Main.HEIGHT / 2 - playButton.getHeight() / 2);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        doodle.getDoodle().dispose();
        playButton.dispose();
    }
}