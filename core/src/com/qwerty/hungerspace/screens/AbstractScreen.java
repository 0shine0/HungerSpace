package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.qwerty.hungerspace.HungerSpaceMain;
import com.qwerty.hungerspace.assets.AssetHolder;

/**
 * Abstract class which can be extended to define new screens.
 *
 */
public abstract class AbstractScreen {
    /**
     * The active camera that the screen should render through. Note that multiple camera's are
     * permitted in a single screen but only one camera can be active at a given time.
     */
    protected Camera camera;

    protected AssetHolder assetHolder;
    protected AssetManager assetManager;

    protected GameScreensManager screensManager;

    protected HungerSpaceMain game;

    public AbstractScreen(HungerSpaceMain game) {
        this.game = game;

        this.camera = game.getStaticGameCamera();

        this.assetHolder = game.getAssetHolder();
        this.assetManager = game.getAssetManager();

        this.screensManager = game.getScreensManager();
    }

    /**
     * Runs on every frame of the game if current screen is active. Usually used to handleInput and
     * process the logic of game for current screen.
     *
     * @param delta The time in seconds that has passed since the last frame.
     */
    public abstract void update(float delta);

    /**
     * Runs on every frame of the game if current screen is active. Usually used to draw images and
     * assets to the screen using a canvas.
     *
     * @param batch The canvas on which the assets and images are drawn.
     */
    public abstract void render(SpriteBatch batch);

    /**
     * Runs when we dispose a screen. Note that closing a screen is not disposing it. Usually runs
     * when we close the game.
     */
    public abstract void dispose();

    /**
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }

}
