package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public class Asteroid extends SphereObject{
    public Asteroid(TextureRegion image, float scale, Vector2 position) {
        this.scale = scale;
        this.position = position;
        objectImage = image;

        updateCollider();
    }

    public void update(float delta){
        position.x += speed.x * delta;
        position.y += speed.y * delta;
        
        handleCollisions();
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
