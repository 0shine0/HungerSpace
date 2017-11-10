package com.qwerty.hungerspace.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpaceShip {
    public Sprite spaceShip;
    public Vector2 speed;
    
    public SpaceShip(List<TextureRegion> animations){
        spaceShip = new Sprite(animations.get(0));
        spaceShip.setPosition(0, 0);
        spaceShip.setScale(0.2f);
        speed = new Vector2(0.0f, 0.0f);
    }
    
    public void update(float delta){
        spaceShip.setPosition(spaceShip.getX() + speed.x * delta, spaceShip.getY() + speed.y * delta);
    }
    
    public void render(SpriteBatch batch){
        spaceShip.draw(batch);
    }

}
