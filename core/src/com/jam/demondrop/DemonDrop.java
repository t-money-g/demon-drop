package com.jam.demondrop;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Created by terrenceyoung on 1/31/16.
 */
public class DemonDrop extends Game{
    public SpriteBatch batch;
    public BitmapFont font;
    private Sound theme;

    public void create() {
        batch = new SpriteBatch();

        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
        theme = Gdx.audio.newSound(Gdx.files.internal("Theme1.ogg"));
        theme.loop();
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        theme.dispose();
        this.getScreen().dispose();
    }
}
