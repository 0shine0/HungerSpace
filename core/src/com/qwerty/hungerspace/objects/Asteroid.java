package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public class Asteroid extends SphereObject{
    public Vector2 speed = new Vector2();
    float direction;

    public Asteroid(TextureRegion image, float scale, Vector2 pos) {
        this.scale = scale;
        
        position = pos;
        
        direction = 0.0f;
        
        objectImage = image;
    }
    
    public void update(float delta){
        position.x += speed.x * delta;
        position.y += speed.y * delta;
        
        updateCollider();
        
        handleCollisions();
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(objectImage, position.x - objectImage.getRegionWidth()/2, position.y - 
                objectImage.getRegionHeight()/2, objectImage.getRegionWidth()/2, 
                objectImage.getRegionHeight()/2, objectImage.getRegionWidth(), 
                objectImage.getRegionHeight(), scale, scale, direction * 180 / 3.141f);
    }
    
    private void handleCollisions(){
        for(SpaceObject body : GameScreen.rigidBodies){
            if(this == body){
                continue;
            }
            
            if(this.collidesWith((SphereObject)body)){
                speed = speed.scl(-1.0f);
            }
        }
    }

}
