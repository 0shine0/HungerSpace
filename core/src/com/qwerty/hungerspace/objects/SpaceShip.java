package com.qwerty.hungerspace.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpaceShip {
    public Sprite spaceShip;
    private float speed = 0.0f;
    
    public SpaceShip(List<TextureRegion> animations){
        spaceShip = new Sprite(animations.get(0));
        spaceShip.setPosition(0, 0);
        spaceShip.setScale(0.2f);
    }
    
    public void update(){
        
    }
    
    public void render(SpriteBatch batch){
        spaceShip.draw(batch);
    }

}
