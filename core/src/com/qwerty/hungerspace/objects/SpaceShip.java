package com.qwerty.hungerspace.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public class SpaceShip extends SphereObject{
    Animation<TextureRegion> spaceAnim;
    float animTime;
    
    private float accelerationFactor;
    private boolean isAccelerating;

    public SpaceShip(List<TextureRegion> animations, float scale, float accelerationFactor) {
        this.scale = scale;
        this.accelerationFactor = accelerationFactor;

        spaceAnim = new Animation(0.05f, animations.toArray());
        spaceAnim.setPlayMode(PlayMode.LOOP);
        
        animTime = 0.0f;
        objectImage = (TextureRegion) spaceAnim.getKeyFrame(animTime, true);

        updateCollider();
    }
    
    public void update(float delta){
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
    
    public void applyAcceleration() {
        isAccelerating = true;
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
