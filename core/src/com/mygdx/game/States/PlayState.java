package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Doodle;
import com.mygdx.game.Sprites.Platform;

public class PlayState extends State {

    public static final int Platform_Spacing = 120;
    public static final int Platform_Count = 16;
    BitmapFont font;
    public int record = 0;
    private Doodle doodle;
    private Texture background;
    private Array<Platform> platforms;
    Texture doodletexture=new Texture("DoodleJump.png");
    boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

    public PlayState(GameStateManager gsm) {
        super(gsm);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TNR.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size=60;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        doodle = new Doodle(Main.WIDTH/2-doodletexture.getWidth()/2, 200);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        background = new Texture("background.png");

        platforms = new Array<Platform>();

        for (int i = 0; i < Platform_Count; i++) {
            if(i==0){
                platforms.add(new Platform(doodle.getPosition().x,0));
            }
            else {
                platforms.add(new Platform(i * Platform_Spacing));
            }
        }

    }

    @Override
    public void handleInpute() {
    }

    @Override
    public void update(float dt) {
        if(doodle.getPosition().y >= 600){
            doodle.fall(dt);
            record++;
            for(Platform platform : platforms){
                platform.fall(dt);
            }
        }



//        for(Platform platform : platforms){
//            platform.fall(dt, doodle.getPosition().y, doodle.getVelocity().y);
//        }

        if (available) {
            float gyroY = Gdx.input.getAccelerometerX();
            System.out.println(gyroY +" puk");
            doodle.move(gyroY*4);
        }

        handleInpute();
        doodle.update(dt);

        for(Platform platform: platforms){
            if((((doodle.getPosition().y>=platform.getPosPlat().y) && (doodle.getPosition().y<=platform.getPosPlat().y+ platform.getPlatform().getHeight()))&&
                    ((doodle.getPosition().x+doodle.getDoodle().getWidth()>=platform.getPosPlat().x) && (doodle.getPosition().x<=platform.getPosPlat().x+platform.getPlatform().getWidth())))&& doodle.getVelocity().y<=0){
                doodle.jump();
            }
        }


        if(doodle.getPosition().y<=-doodle.getDoodle().getHeight())
        {
            gsm.set(new DieState(gsm,record));
            record=0;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        sb.draw(doodle.getDoodle(), doodle.getPosition().x, doodle.getPosition().y);
        for (Platform platform : platforms) {
            sb.draw(platform.getPlatform(), platform.getPosPlat().x, platform.getPosPlat().y);
        }
        font.draw(sb,String.valueOf(record),40,1880);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        doodle.getDoodle().dispose();
        for(Platform platform: platforms){
            platform.getPlatform().dispose();
        }
    }
}