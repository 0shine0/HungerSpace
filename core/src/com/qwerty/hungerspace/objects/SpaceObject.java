package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class SpaceObject {
    public TextureRegion objectImage;
    
    public Vector2 position = new Vector2();
    public Vector2 speed = new Vector2();
    public float scale;
    public float direction;
    
    public int health;

    public void render(SpriteBatch batch) {
        if(objectImage != null){
            batch.draw(objectImage, position.x - objectImage.getRegionWidth()/2, position.y -
                    objectImage.getRegionHeight()/2, objectImage.getRegionWidth()/2,
                    objectImage.getRegionHeight()/2, objectImage.getRegionWidth(),
                    objectImage.getRegionHeight(), scale, scale, direction * 180 / 3.141f);
        }
    }
    
    public abstract void update(float delta);
}
