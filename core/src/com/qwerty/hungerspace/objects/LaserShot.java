package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public class LaserShot extends SphereObject{
    public int damage = 24;
    
    public LaserShot(TextureRegion image, float scale, Vector2 position, float speed, float rotation){
        this.scale = scale;
        this.position = position;
        objectImage = image;
        
        this.direction = rotation;
        
        this.speed =  new Vector2((float)Math.cos(direction + 3.14/2) * speed, (float)Math.sin(direction + 3.14/2) * speed);
        
        health = 1;

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
    public void collisionResult(SphereObject body){
        body.health -= damage;
        health = 0;
    }

}
