package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.HungerSpaceMain;
import com.qwerty.hungerspace.screens.GameScreen;

import java.util.List;

import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_HEIGHT;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_WIDTH;

public class SpaceShip extends SphereObject{
    Animation<TextureRegion> spaceAnim;
    float animTime;
    
    private float resetTime = 0.3f;
    private float cooldownTime;
    
    private float laserSpeed = 500.0f;
    private String laserKey;
    
    private float accelerationFactor;
    private boolean isAccelerating;

    public SpaceShip(List<TextureRegion> animations, float scale, float accelerationFactor, Vector2 pos, String laserKey, float mass) {
        this.scale = scale;
        this.accelerationFactor = accelerationFactor;
        
        this.mass = mass;
        
        this.laserKey = laserKey;
        
        position = pos;
        
        health = 100;
        
        cooldownTime = resetTime;

        spaceAnim = new Animation(0.05f, animations.toArray());
        spaceAnim.setPlayMode(PlayMode.LOOP);
        
        animTime = 0.0f;
        objectImage = spaceAnim.getKeyFrame(animTime, true);

        updateCollider(20);
        
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
            LaserShot laser = new LaserShot(GameScreen.textureRegions.get(laserKey), 0.3f, new Vector2(position), laserSpeed, direction);
            GameScreen.rigidBodies.add(laser);
            exceptions.add(laser);
            laser.exceptions.add(this); 
            
            cooldownTime = resetTime;

            HungerSpaceMain.sounds.get("blaster").play();
        }
    }
    
    public void fireClientLaserShot(Vector2 pos, float dir){
        LaserShot laser = new LaserShot(GameScreen.textureRegions.get(laserKey), 0.3f, pos, laserSpeed, dir);
        GameScreen.rigidBodies.add(laser);
        exceptions.add(laser);
        laser.exceptions.add(this); 
    }
    
    public void applyAcceleration() {
        isAccelerating = true;
    }
    
    public void destroy() {
        objectImage = null;
        GameScreen.rigidBodies.remove(this);
    }

    @Override
    public void collisionResult(SphereObject body) {
        speed.scl(-0.5f);
        body.speed.scl(-0.5f);
//        speed.x = ((mass - body.mass)/(mass + body.mass)*speed.x) + (2*body.mass/(mass + body.mass)*body.speed.x);
//        if(body instanceof SpaceShip){
//            body.speed.x = ((body.mass - mass)/(mass + body.mass)*body.speed.x) + (2*mass/(mass + body.mass)*speed.x);
//        }
//        
//        speed.y = ((mass - body.mass)/(mass + body.mass)*speed.y) + (2*body.mass/(mass + body.mass)*body.speed.y);
//        if(body instanceof SpaceShip){
//            body.speed.y = ((body.mass - mass)/(mass + body.mass)*body.speed.y) + (2*mass/(mass + body.mass)*speed.y);
//        }

        HungerSpaceMain.sounds.get("rockCollision").play();
    }
}
