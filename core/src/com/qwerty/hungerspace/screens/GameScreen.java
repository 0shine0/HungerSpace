package com.qwerty.hungerspace.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.qwerty.hungerspace.HungerSpaceMain;
import com.qwerty.hungerspace.objects.SpaceShip;

/**
 * This screen contains the actual gameplay mechanics and represents what the user the user will play.
 *
 */
public class GameScreen extends AbstractScreen {
    private Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
    
    SpaceShip playerShip;

    public GameScreen(HungerSpaceMain game) {
        super(game);
        
        textureRegions.put("background", assetHolder.textureAtlas.findRegion("Background/background"));
        textureRegions.put("spaceShip11", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/1"));
        textureRegions.put("spaceShip12", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/2"));
        textureRegions.put("spaceShip13", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/3"));
        textureRegions.put("spaceShip14", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/4"));
        textureRegions.put("spaceShip15", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/5"));
        
        List<TextureRegion> spaceShip = new ArrayList<TextureRegion>();
        spaceShip.add(textureRegions.get("spaceShip11"));
        
        playerShip = new SpaceShip(spaceShip);
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        batch.begin();

        batch.draw(textureRegions.get("background"), 0, 0, HungerSpaceMain.WIDTH, HungerSpaceMain.HEIGHT);

        playerShip.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
