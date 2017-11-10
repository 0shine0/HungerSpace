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
import com.qwerty.hungerspace.screens.EndGameScreen;
import com.qwerty.hungerspace.screens.GameScreen;
import com.qwerty.hungerspace.screens.GameScreensManager;
import com.qwerty.hungerspace.screens.MenuScreen;
import com.qwerty.hungerspace.screens.PauseScreen;

public class HungerSpaceMain extends ApplicationAdapter {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private SpriteBatch batch;

    private GameScreensManager screensManager;

    private AssetHolder assetHolder;
    private AssetManager assetManager;

    private OrthographicCamera staticGameCamera;

    public MenuScreen menuScreen;
    public GameScreen gameScreen;
    public PauseScreen pauseScreen;
    public EndGameScreen endScreen;

    @Override
    public void create() {
        Gdx.gl.glClearColor(0, 0, 0, 1);

        batch = new SpriteBatch();

        screensManager = new GameScreensManager(batch);

        staticGameCamera = new OrthographicCamera(WIDTH, HEIGHT);
        staticGameCamera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        staticGameCamera.update();

        assetManager = new AssetManager();

        // load all spriteSheets
        assetManager.load("texture/textureAtlas.atlas", TextureAtlas.class);
        // wait until all resources have finished loading
        assetManager.finishLoading();

        Texture.setAssetManager(assetManager);

        assetHolder = new AssetHolder();
        assetHolder.textureAtlas = assetManager.get("texture/textureAtlas.atlas", TextureAtlas.class);

        menuScreen = new MenuScreen(this);
        screensManager.pushScreen(menuScreen);
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
}
