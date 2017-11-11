package com.qwerty.hungerspace;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.qwerty.hungerspace.assets.AssetHolder;
import com.qwerty.hungerspace.screens.AbstractScreen;
import com.qwerty.hungerspace.screens.GameScreen;
import com.qwerty.hungerspace.screens.GameScreensManager;
import com.qwerty.hungerspace.screens.IntroScreen;
import com.qwerty.hungerspace.screens.MenuScreen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HungerSpaceMain extends ApplicationAdapter {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    public static final float BACKGROUND_SIZE = 1024;

    private SpriteBatch batch;

    private GameScreensManager screensManager;

    private AssetHolder assetHolder;
    private AssetManager assetManager;

    private OrthographicCamera staticGameCamera;

    private static Random random = new Random();

    public Map<String, AbstractScreen> screens = new HashMap<String, AbstractScreen>();

    @Override
    public void create() {
        Gdx.gl.glClearColor(0, 0, 0, 1);

        batch = new SpriteBatch();

        screensManager = new GameScreensManager(batch);

        staticGameCamera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        staticGameCamera.position.set(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 0);
        staticGameCamera.update();

        assetManager = new AssetManager();

        // load all spriteSheets
        assetManager.load("texture/textureAtlas.atlas", TextureAtlas.class);
        // wait until all resources have finished loading
        assetManager.finishLoading();

        Texture.setAssetManager(assetManager);

        assetHolder = new AssetHolder();
        assetHolder.textureAtlas = assetManager.get("texture/textureAtlas.atlas", TextureAtlas.class);

        screens.put("intro", new IntroScreen(this));
        screens.put("menu", new MenuScreen(this));
        screens.put("game", new GameScreen(this));
        screensManager.pushScreen(screens.get("menu"));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        screensManager.update(Gdx.graphics.getDeltaTime());

        screensManager.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.dispose();
        screensManager.dispose();
    }

    /**
     * @return the screensManager
     */
    public GameScreensManager getScreensManager() {
        return screensManager;
    }

    /**
     * @return the assetHolder
     */
    public AssetHolder getAssetHolder() {
        return assetHolder;
    }

    /**
     * @return the assetManager
     */
    public AssetManager getAssetManager() {
        return assetManager;
    }

    /**
     * @return the staticGameCamera
     */
    public OrthographicCamera getStaticGameCamera() {
        return staticGameCamera;
    }

    public static Random getRandom() {
        return random;
    }
}
