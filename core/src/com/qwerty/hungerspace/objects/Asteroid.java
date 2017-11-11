package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public class Asteroid extends SphereObject{
    public Asteroid(TextureRegion image, float scale, Vector2 position) {
        this.scale = scale;
        this.position = position;
        objectImage = image;
        
        health = 50;

        updateCollider();
        
        exceptions.add(this);
    }

    @Override
    public void update(float delta){
        if(health <= 0){
            destroy();
        }
        
        position.x += speed.x * delta;
        position.y += speed.y * delta;
        
        handleCollisions();
    }
    
    public void destroy(){
        objectImage = null;
        GameScreen.rigidBodies.remove(this);
    }

    @Override
    public void collisionResult(SphereObject body) {
        speed = speed.scl(-1.0f);
        
    }

}
