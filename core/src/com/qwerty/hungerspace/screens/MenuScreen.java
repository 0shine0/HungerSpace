package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.qwerty.hungerspace.HungerSpaceMain;

import java.util.HashMap;
import java.util.Map;

/**
 * This screen represents main user interaction screen from which user can play, quit and do other
 * miscellaneous stuff.
 *
 */
public class MenuScreen extends AbstractScreen {
    private Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();

    public MenuScreen(HungerSpaceMain game) {
        super(game);

        textureRegions.put("background", assetHolder.textureAtlas.findRegion("Background/background"));
        textureRegions.put("newGame", assetHolder.textureAtlas.findRegion("Menu Screen/newGame"));
        textureRegions.put("newGameActivated", assetHolder.textureAtlas.findRegion("Menu Screen/newGameActivated"));
        textureRegions.put("exit", assetHolder.textureAtlas.findRegion("Menu Screen/exit"));
        textureRegions.put("exitActivated", assetHolder.textureAtlas.findRegion("Menu Screen/exitActivated"));
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();

        drawBackground(batch);

        batch.draw(textureRegions.get("newGameActivated"), 0, 0);

        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }

    private void drawBackground(SpriteBatch batch) {
        batch.draw(textureRegions.get("background"), 0 ,0);
        batch.draw(textureRegions.get("background"), HungerSpaceMain.BACKGROUND_SIZE ,0);
    }
}
