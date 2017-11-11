package com.qwerty.hungerspace.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public class SpaceShip extends SphereObject{
    Animation<TextureRegion> spaceAnim;
    float animTime;
    
    private float resetTime = 0.3f;
    private float cooldownTime;
    
    private float laserSpeed = 500.0f;
    
    private float accelerationFactor;
    private boolean isAccelerating;

    public SpaceShip(List<TextureRegion> animations, float scale, float accelerationFactor) {
        this.scale = scale;
        this.accelerationFactor = accelerationFactor;
        
        health = 100;
        
        cooldownTime = resetTime;

        spaceAnim = new Animation(0.05f, animations.toArray());
        spaceAnim.setPlayMode(PlayMode.LOOP);
        
        animTime = 0.0f;
        objectImage = (TextureRegion) spaceAnim.getKeyFrame(animTime, true);

        updateCollider();
        
        exceptions.add(this);
    }
    
    @Override
    public void update(float delta){
        if(health <= 0){
            destroy();
        }
        
        cooldownTime -= delta;
        
        speed.scl(0.995f);

        if (isAccelerating) {
            speed.x -= (float)Math.sin(direction) * delta * accelerationFactor;
            speed.y += (float)Math.cos(direction) * delta * accelerationFactor;
        }

        position.x += speed.x * delta;
        position.y += speed.y * delta;

        animTime += delta;
        objectImage = (TextureRegion) spaceAnim.getKeyFrame(animTime);

        isAccelerating = false;
        
        handleCollisions();
    }
    
    public void fireLaserShot(float delta){
        if(cooldownTime <= 0){
            LaserShot laser = new LaserShot(GameScreen.textureRegions.get("laserShot"), 0.3f, new Vector2(position), laserSpeed, direction);
            GameScreen.rigidBodies.add(laser);
            exceptions.add(laser);
            laser.exceptions.add(this); 
            
            cooldownTime = resetTime;
        }
    }
    
    public void applyAcceleration() {
        isAccelerating = true;
    }
    
    public void destroy(){
        objectImage = null;
        GameScreen.rigidBodies.remove(this);
    }

    @Override
    public void collisionResult(SphereObject body) {
        speed = speed.scl(-1.1f);
        
    }
}
