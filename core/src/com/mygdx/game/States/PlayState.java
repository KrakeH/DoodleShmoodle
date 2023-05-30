package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Main;
import com.mygdx.game.Sprites.Hat;
import com.mygdx.game.Sprites.Platforms.CloudPlatform;
import com.mygdx.game.Sprites.Platforms.DestroyPlatform;
import com.mygdx.game.Sprites.Doodle;
import com.mygdx.game.Sprites.Platforms.Platform;

import java.lang.management.MemoryUsage;

public class PlayState extends State {

    public static final int Platform_Spacing = 120;
    public static final int Platform_Count = 16;
    private boolean RightMove=false;
    private boolean LeftMove=true;
    private Sound sound;
    private Music music;
    BitmapFont font;
    private float gyroY;
    public float record = 0;
    private Doodle doodle;
    private Texture background;
    private Array<Platform> platforms;
    private Array<CloudPlatform> CloudPlatforms;
    private Array<DestroyPlatform> DestPlatforms;
    private Array<Hat> Hats;
    Texture doodletexture = new Texture("DoodleJumpLeft.png");
    boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

    public PlayState(GameStateManager gsm) {
        super(gsm);

        //audio
        sound=Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
        music=Gdx.audio.newMusic(Gdx.files.internal("helicopter.mp3"));

        //font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TNR.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 60;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);
        generator.dispose();

        doodle = new Doodle(Main.WIDTH / 2 - doodletexture.getWidth() / 2, 200);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        background = new Texture("background.png");

        //Platforms
        platforms = new Array<Platform>();
        DestPlatforms = new Array<DestroyPlatform>();
        CloudPlatforms = new Array<CloudPlatform>();

        Hats=new Array<Hat>();

        for (int i = 0; i < Platform_Count; i++) {
            if (i == 0) {
                platforms.add(new Platform(doodle.getPosition().x, 0));
            } else {
                platforms.add(new Platform(i * Platform_Spacing));
            }
            DestPlatforms.add(new DestroyPlatform(i * Platform_Spacing));
            if(platforms.get(i).IsPlat && !DestPlatforms.get(i).IsPlatDest){

                CloudPlatforms.add(new CloudPlatform(i * Platform_Spacing,(int) platforms.get(i).getPosPlat().x));
            }
            if (DestPlatforms.get(i).IsPlatDest){
                CloudPlatforms.add(new CloudPlatform(i * Platform_Spacing,(int) DestPlatforms.get(i).getPosPlatDest().x));
            }
            Hats.add(new Hat(i * Platform_Spacing,platforms.get(i).getPosPlat().x));
        }
        for (int i = 0; i < Platform_Count; i++) {
            if (i!=0 && DestPlatforms.get(i).IsPlatDest) {
                platforms.get(i).IsPlat=false;
            }
            else if(i==0){
                DestPlatforms.get(i).IsPlatDest=false;
                CloudPlatforms.get(i).IsPlatCloud=false;
                Hats.get(i).IsHat=false;
            }
        }
        for (int i = 0; i < Platform_Count; i++) {
            if(platforms.get(i).IsPlat && !DestPlatforms.get(i).IsPlatDest){
                CloudPlatforms.get(i).generate((int) platforms.get(i).getPosPlat().x);
            }
            if (DestPlatforms.get(i).IsPlatDest){
                CloudPlatforms.get(i).generate((int) DestPlatforms.get(i).getPosPlatDest().x);
            }
        }
    }

    @Override
    public void handleInpute() {
    }


    @Override
    public void update(float dt) {
        if (available) {
            gyroY = Gdx.input.getAccelerometerX();
            doodle.move(gyroY * 4);
        }

        //
        if(doodle.HaveCap){
            music.play();
        }
        else{
            music.stop();
        }
        //rotation
        if(gyroY>=0.3){
            RightMove=true;
            LeftMove=false;
        }
        else if(gyroY<=-0.3){
            RightMove=false;
            LeftMove=true;
        }
        //Hat
        if(doodle.getTimer()>0 && doodle.HaveCap){
            doodle.fly(dt);
            for (int i = 0; i < Platform_Count; i++) {
                DestPlatforms.get(i).speedFall();
                platforms.get(i).speedFall();
                CloudPlatforms.get(i).speedFall();
                Hats.get(i).speedFall();
                record+=6*dt;
            }
        }
        else{
            doodle.HaveCap=false;
            doodle.resetTimer();
        }
        doodle.update(dt,gyroY);

        for (int i = 0; i < Platform_Count; i++) {
            if (DestPlatforms.get(i).IsPlatDest) {
                platforms.get(i).IsPlat=false;
            }
        }
        if(!doodle.HaveCap) {
            if (doodle.getPosition().y >= 600) {
                doodle.fall();
                record+=6*dt;
                for (int i = 0; i < platforms.size; i++) {
                    DestPlatforms.get(i).fall();
                    platforms.get(i).fall();
                    CloudPlatforms.get(i).fall();
                    Hats.get(i).fall();
                }
            }
        }
        if(!doodle.HaveCap) {
            for (int i = 0; i < platforms.size; i++) {
                if (((((doodle.getPosition().y >= platforms.get(i).getPosPlat().y) && (doodle.getPosition().y <= platforms.get(i).getPosPlat().y + platforms.get(i).getPlatform().getHeight())) &&
                        ((doodle.getPosition().x + doodle.getDoodle().getWidth() >= platforms.get(i).getPosPlat().x) && (doodle.getPosition().x <= platforms.get(i).getPosPlat().x + platforms.get(i).getPlatform().getWidth()))) && doodle.getVelocity().y <= 0) && platforms.get(i).IsPlat ) {
                    doodle.jump();
                    sound.play();
                }
                if (((((doodle.getPosition().y >= DestPlatforms.get(i).getPosPlatDest().y) && (doodle.getPosition().y <= DestPlatforms.get(i).getPosPlatDest().y + DestPlatforms.get(i).getDestPlatform().getHeight())) &&
                        ((doodle.getPosition().x + doodle.getDoodle().getWidth() >= DestPlatforms.get(i).getPosPlatDest().x) && (doodle.getPosition().x <= DestPlatforms.get(i).getPosPlatDest().x + DestPlatforms.get(i).getDestPlatform().getWidth()))) && doodle.getVelocity().y <= 0) && DestPlatforms.get(i).IsPlatDest) {
                    DestPlatforms.get(i).IsPlatDest=false;
                    doodle.jump();
                    sound.play();
                }
                if (((((doodle.getPosition().y >= CloudPlatforms.get(i).getPosPlatCloud().y) && (doodle.getPosition().y <= CloudPlatforms.get(i).getPosPlatCloud().y + CloudPlatforms.get(i).getCloudPlatform().getHeight())) &&
                        ((doodle.getPosition().x + doodle.getDoodle().getWidth() >= CloudPlatforms.get(i).getPosPlatCloud().x) && (doodle.getPosition().x <= CloudPlatforms.get(i).getPosPlatCloud().x + CloudPlatforms.get(i).getCloudPlatform().getWidth()))) && doodle.getVelocity().y <= 0) && CloudPlatforms.get(i).IsPlatCloud) {
                    CloudPlatforms.get(i).IsPlatCloud = false;
                }
                if ((Hats.get(i).getPosHat().x+Hats.get(i).getHat().getWidth()>=doodle.getPosition().x && Hats.get(i).getPosHat().x<=doodle.getPosition().x+doodle.getDoodle().getWidth()) &&
                        (Hats.get(i).getPosHat().y+Hats.get(i).getHat().getHeight()>=doodle.getPosition().y && Hats.get(i).getPosHat().y<=doodle.getPosition().y+doodle.getDoodle().getHeight()) && Hats.get(i).IsHat && doodle.getVelocity().y<0 && platforms.get(i).IsPlat) {
                    doodle.HaveCap=true;
                    Hats.get(i).IsHat=false;
                }
            }
        }


        if (doodle.getPosition().y <= -doodle.getDoodle().getHeight()) {
            gsm.set(new DieState(gsm, (int)record,doodle.getPosition().x));
            record = 0;
        }

        //generates
        for (int i = 0; i < platforms.size; i++) {
            platforms.get(i).generate();
            DestPlatforms.get(i).generate();
            if(platforms.get(i).IsPlat && !DestPlatforms.get(i).IsPlatDest){
                CloudPlatforms.get(i).generate((int) platforms.get(i).getPosPlat().x);
            }
            if (DestPlatforms.get(i).IsPlatDest){
                CloudPlatforms.get(i).generate((int) DestPlatforms.get(i).getPosPlatDest().x);
            }
            Hats.get(i).generate(platforms.get(i).getPosPlat().x, platforms.get(i).getPosPlat().y);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        for (int i = 0; i < platforms.size; i++) {
            if(platforms.get(i).IsPlat) {
                sb.draw(platforms.get(i).getPlatform(), platforms.get(i).getPosPlat().x, platforms.get(i).getPosPlat().y);
            }
            if (DestPlatforms.get(i).IsPlatDest) {
                sb.draw(DestPlatforms.get(i).getDestPlatform(), DestPlatforms.get(i).getPosPlatDest().x, DestPlatforms.get(i).getPosPlatDest().y);
            }
            if (CloudPlatforms.get(i).IsPlatCloud) {
                sb.draw(CloudPlatforms.get(i).getCloudPlatform(), CloudPlatforms.get(i).getPosPlatCloud().x, CloudPlatforms.get(i).getPosPlatCloud().y);
            }
            if (Hats.get(i).IsHat && platforms.get(i).IsPlat) {
                sb.draw(Hats.get(i).getHat(), Hats.get(i).getPosHat().x, Hats.get(i).getPosHat().y);
            }
        }
        if(RightMove && doodle.HaveCap){
            sb.draw(doodle.getDoodleRightFly(),doodle.getPosition().x,doodle.getPosition().y);
        }
        else if(LeftMove && doodle.HaveCap){
            sb.draw(doodle.getDoodleLeftFly(),doodle.getPosition().x,doodle.getPosition().y);
        }
        else if(RightMove){
            sb.draw(doodle.getDoodleRight(),doodle.getPosition().x,doodle.getPosition().y);
        }
        else if(LeftMove){
            sb.draw(doodle.getDoodleLeft(),doodle.getPosition().x,doodle.getPosition().y);
        }
        font.draw(sb, String.valueOf((int)record), 40, 1880);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        doodle.getDoodle().dispose();
        for (Platform platform : platforms) {
            platform.getPlatform().dispose();
        }
        for (DestroyPlatform destroyPlatform : DestPlatforms) {
            destroyPlatform.getDestPlatform().dispose();
        }
        for (CloudPlatform cloudPlatform : CloudPlatforms) {
            cloudPlatform.getCloudPlatform().dispose();
        }
    }
}