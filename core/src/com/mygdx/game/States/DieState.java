package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Doodle;
import com.mygdx.game.Sprites.Platform;

public class DieState extends State{
    private Texture background;
    private Texture playButton;
    private Doodle doodle;
    private int record;
    private float bestRecord;
    private float nowRecord;
    private int highscore;
    GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member

    BitmapFont font;
    Texture doodletexture=new Texture("DoodleJump.png");
    Preferences prefs = Gdx.app.getPreferences("game preferences");

    public DieState(GameStateManager gsm,int record) {
        super(gsm);
        this.record=record;
        if (record > highscore) {
            prefs.putInteger("highscore", record);
            prefs.flush();
        }
        highscore = prefs.getInteger("highscore");
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

        layout.setText(font,"Best record: "+String.valueOf(highscore));
        bestRecord=layout.width;
        layout.setText(font,"Your record: "+String.valueOf(record));
        nowRecord=layout.width;
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
        if(doodle.getPosition().y<0) {
            sb.draw(playButton, Main.WIDTH / 2 - playButton.getWidth() / 2, Main.HEIGHT / 2 - playButton.getHeight() / 2);
            font.draw(sb,"Best record: "+String.valueOf(highscore),Main.WIDTH/2-bestRecord/2,Main.HEIGHT/2+playButton.getHeight()/2+200);
            font.draw(sb,"Your record: "+String.valueOf(record),Main.WIDTH/2-nowRecord/2,Main.HEIGHT/2+playButton.getHeight()/2+110);

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