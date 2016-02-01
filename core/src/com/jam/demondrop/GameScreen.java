package com.jam.demondrop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
	final DemonDrop game;
	private Texture dropImage;
	private Texture bucketImage;
	private Texture background;
	private Sound ouchSound;
	private Sound hahSound;

	private Music rainMusic;
	private OrthographicCamera camera;

	private Rectangle bucket;
	private Array<Rectangle> demondrops;
	private long lastDropTime;

	private int dropsCollected;
	private int lives = 3;
	public GameScreen(final DemonDrop gam) {
		this.game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);


		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("Imp.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		background = new Texture(Gdx.files.internal("DemonCastle2.png"));

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 /2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		// load the drop sound effect and the rain background "music"
		ouchSound = Gdx.audio.newSound(Gdx.files.internal("ouch2.wav"));
		hahSound = Gdx.audio.newSound(Gdx.files.internal("hah.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);


		demondrops = new Array<Rectangle>();
		spawnDemondrop();

		dropsCollected = 0;
	}

	@Override
	public void render (float deltatime) {
		Gdx.gl.glClearColor(0, 0, 0.01f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		checkWin();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800) bucket.x = 800 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnDemondrop();

		Iterator<Rectangle> iter = demondrops.iterator();
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 117 < 0) {
				iter.remove();
				hahSound.play();
				lives--;
			};
			if(raindrop.overlaps(bucket)) {
				ouchSound.play();
				iter.remove();
				dropsCollected++;
			}
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		game.batch.draw(background, 0, 0);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		game.font.draw(game.batch, "Demons Collected: " + dropsCollected, 0, 480);
		game.font.draw(game.batch, "Lives: " + lives, 725, 480);

		for (Rectangle raindrop: demondrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();
	}

	private void spawnDemondrop() {
		Rectangle demondrop = new Rectangle();
		demondrop.x = MathUtils.random(0, 800 - 64);
		demondrop.y = 480;
		demondrop.width = 64;
		demondrop.height = 117;
		demondrops.add(demondrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		ouchSound.dispose();
		rainMusic.dispose();

	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
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

	@Override
	public void resize(int width, int height) {
	}

	public void checkWin() {
		if(lives ==0) {
			dispose();
			game.setScreen(new  GameOver(game));
		}
	}
}
