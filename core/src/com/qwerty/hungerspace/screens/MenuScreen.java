package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.qwerty.hungerspace.HungerSpaceMain;

import java.util.HashMap;
import java.util.Map;

import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_HEIGHT;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_WIDTH;

/**
 * This screen represents main user interaction screen from which user can play, quit and do other
 * miscellaneous stuff.
 *
 */
public class MenuScreen extends AbstractScreen {
    private Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
    private int activatedButton = -1;

    private static final int margin = 20;

    private int buttonHeight;
    private int buttonStartX;
    private int buttonEndX;
    private int buttonStartY;

    public MenuScreen(HungerSpaceMain game) {
        super(game);

        textureRegions.put("background", assetHolder.textureAtlas.findRegion("Background/background"));
        textureRegions.put("logo", assetHolder.textureAtlas.findRegion("Menu Screen/logo"));
        textureRegions.put("newGame", assetHolder.textureAtlas.findRegion("Menu Screen/newGame"));
        textureRegions.put("newGameActivated", assetHolder.textureAtlas.findRegion("Menu Screen/newGameActivated"));
        textureRegions.put("exit", assetHolder.textureAtlas.findRegion("Menu Screen/exit"));
        textureRegions.put("exitActivated", assetHolder.textureAtlas.findRegion("Menu Screen/exitActivated"));

        buttonHeight = textureRegions.get("newGame").getRegionHeight();
        buttonStartX = (SCREEN_WIDTH - textureRegions.get("newGame").getRegionWidth()) / 2;
        buttonEndX = buttonStartX + textureRegions.get("newGame").getRegionWidth();
        buttonStartY = 225;

        HungerSpaceMain.sounds.put("uiHover", Gdx.audio.newSound(Gdx.files.internal("sounds/uiHover.ogg")));

        Music music = Gdx.audio.newMusic(Gdx.files.internal("music/dreadnaught.ogg"));
        music.play();
    }

    @Override
    public void update(float delta) {
        int lastActivedButton = activatedButton;

        activatedButton = -1;

        int mouseX = Gdx.input.getX();
        int mouseY = SCREEN_HEIGHT - Gdx.input.getY();

        int y = buttonStartY;
        for (int i = 0; i < 2; i++) {
            if (mouseX >= buttonStartX && mouseX < buttonEndX && mouseY >= y + 20 && mouseY < y + buttonHeight - 20) {
                activatedButton = i;
                break;
            }

            y -= buttonHeight + margin;
        }

        if (lastActivedButton != activatedButton) {
            HungerSpaceMain.sounds.get("uiHover").play();
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            switch (activatedButton) {
                case 0:
                    screensManager.pushScreen(game.screens.get("game"));
                    break;
                case 1:
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();

        drawBackground(batch);

        batch.draw(textureRegions.get("logo"), (SCREEN_WIDTH - textureRegions.get("logo").getRegionWidth()) / 2, SCREEN_HEIGHT - textureRegions.get("logo").getRegionHeight() - margin * 5);

        batch.draw(textureRegions.get("newGame" + (activatedButton == 0? "Activated": "")), buttonStartX, buttonStartY);
        batch.draw(textureRegions.get("exit" + (activatedButton == 1? "Activated": "")), buttonStartX, buttonStartY - buttonHeight - margin);

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
