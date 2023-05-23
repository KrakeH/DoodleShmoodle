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
import com.mygdx.game.Sprites.DestroyPlatform;
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
    private Array<DestroyPlatform> DestPlatforms;
    Texture doodletexture = new Texture("DoodleJump.png");
    boolean available = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

    public PlayState(GameStateManager gsm) {
        super(gsm);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("TNR.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 60;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();

        doodle = new Doodle(Main.WIDTH / 2 - doodletexture.getWidth() / 2, 200);
        camera.setToOrtho(false, Main.WIDTH, Main.HEIGHT);
        background = new Texture("background.png");

        platforms = new Array<Platform>();
        DestPlatforms = new Array<DestroyPlatform>();

        for (int i = 0; i < Platform_Count; i++) {
            if (i == 0) {
                platforms.add(new Platform(doodle.getPosition().x, 0));
            } else {
                platforms.add(new Platform(i * Platform_Spacing));
            }
            DestPlatforms.add(new DestroyPlatform(platforms.get(i).getPosPlat().y, platforms.get(i).getPosPlat().x));
        }
    }

    @Override
    public void handleInpute() {
    }


    @Override
    public void update(float dt) {
        for (int i = 0; i < platforms.size; i++) {
            System.out.println("platform "+i+":"+DestPlatforms.get(i).getPosPlatDest());
        }
        if (doodle.getPosition().y >= 600) {
            doodle.fall();
            record++;
            for (int i = 0; i < platforms.size; i++) {
                DestPlatforms.get(i).fall();
                platforms.get(i).fall();
            }
        }

        if (available) {
            float gyroY = Gdx.input.getAccelerometerX();
            doodle.move(gyroY * 4);
        }

        handleInpute();
        doodle.update(dt);

        for (int i = 0; i < platforms.size; i++) {
            if ((((doodle.getPosition().y >= platforms.get(i).getPosPlat().y) && (doodle.getPosition().y <= platforms.get(i).getPosPlat().y + platforms.get(i).getPlatform().getHeight())) &&
                    ((doodle.getPosition().x + doodle.getDoodle().getWidth() >= platforms.get(i).getPosPlat().x) && (doodle.getPosition().x <= platforms.get(i).getPosPlat().x + platforms.get(i).getPlatform().getWidth()))) && doodle.getVelocity().y <= 0) {
                doodle.jump();
            }
            if (((((doodle.getPosition().y >= DestPlatforms.get(i).getPosPlatDest().y) && (doodle.getPosition().y <= DestPlatforms.get(i).getPosPlatDest().y + DestPlatforms.get(i).getDestPlatform().getHeight())) &&
                    ((doodle.getPosition().x + doodle.getDoodle().getWidth() >= DestPlatforms.get(i).getPosPlatDest().x) && (doodle.getPosition().x <= DestPlatforms.get(i).getPosPlatDest().x + DestPlatforms.get(i).getDestPlatform().getWidth()))) && doodle.getVelocity().y <= 0)&& DestPlatforms.get(i).IsPlatDest) {
                doodle.jump();
                DestPlatforms.get(i).IsPlatDest=false;
            }
        }


        if (doodle.getPosition().y <= -doodle.getDoodle().getHeight()) {
            gsm.set(new DieState(gsm, record,doodle.getPosition().x));
            record = 0;
        }
        for (int i = 0; i < platforms.size; i++) {
            platforms.get(i).generate();
            DestPlatforms.get(i).generate((int) platforms.get(i).getPosPlat().x);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, Main.WIDTH, Main.HEIGHT);
        sb.draw(doodle.getDoodle(), doodle.getPosition().x, doodle.getPosition().y);
        for (int i = 0; i < platforms.size; i++) {
            sb.draw(platforms.get(i).getPlatform(), platforms.get(i).getPosPlat().x, platforms.get(i).getPosPlat().y);
            if (DestPlatforms.get(i).IsPlatDest) {
                sb.draw(DestPlatforms.get(i).getDestPlatform(), DestPlatforms.get(i).getPosPlatDest().x, DestPlatforms.get(i).getPosPlatDest().y);
            }
        }
        font.draw(sb, String.valueOf(record), 40, 1880);
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
    }
}