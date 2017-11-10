package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.qwerty.hungerspace.HungerSpaceMain;

/**
 * This screen represents main user interaction screen from which user can play, quit and do other 
 * miscellaneous stuff.
 * @author shine
 *
 */
public class MenuScreen extends AbstractScreen{
    private TextureRegion background;
    
    public MenuScreen(HungerSpaceMain game) {
        super(game);
        initialize();
    }
    
    @Override
    public void initialize() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        batch.begin();
        
        batch.draw(background, 0, 0, HungerSpaceMain.WIDTH, HungerSpaceMain.HEIGHT);
        
        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    public void setBackground(TextureRegion background){
        this.background = background;
    }

}
