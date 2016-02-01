package com.jam.demondrop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by terrenceyoung on 1/31/16.
 */
public class GameOver implements Screen {

    final DemonDrop game;

    OrthographicCamera camera;
    private Texture gameOver;
    private Sound hahaSound;
    private boolean timerIsOn = false;
    private boolean canRestart = false;
    public GameOver(final DemonDrop gam) {
        game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        gameOver = new Texture(Gdx.files.internal("GameOver.png"));
        hahaSound =  Gdx.audio.newSound(Gdx.files.internal("demon.wav"));
        hahaSound.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        //
        game.batch.begin();
        game.batch.draw(gameOver, 0, 0);
        if(!timerIsOn) {
            timerIsOn = true;



            Timer.schedule(new Timer.Task() {

                @Override
                public void run() {
                    canRestart = true;


                }

            }, 3);

        } else if(Gdx.input.isTouched() && canRestart) {

            Timer.instance().clear();

            game.setScreen(new GameScreen(game));
            dispose();
        }

        if(canRestart) {
            game.font.draw(game.batch, "Tap to Restart", 100, 100);
        }
        game.batch.end();


    }

    @Override
    public void dispose() {
        hahaSound.dispose();
        gameOver.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
