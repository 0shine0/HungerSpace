package com.qwerty.hungerspace.objects;

import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_HEIGHT;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_WIDTH;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

public abstract class SphereObject extends SpaceObject{
    public float radius;
    public static final float epsilon = 0.01f;
    public List<SpaceObject> exceptions = new ArrayList<SpaceObject>();
    
    public void updateCollider(int margin) {
        if(objectImage.getRegionWidth() >= objectImage.getRegionHeight()){
            radius = scale * objectImage.getRegionWidth()/2 - margin;
        }
        else{
            radius = scale * objectImage.getRegionHeight()/2 - margin;
        }
    }

    public boolean collidesWith(SphereObject obj){
        return sphereCollision((SphereObject) obj);
    }
    
    private boolean sphereCollision(SphereObject obj){
        Vector2 dir = new Vector2(position.x - obj.position.x, position.y - obj.position.y);
        if(dir.len2() <= Math.pow(radius + obj.radius, 2)){
            if(this instanceof SpaceShip){
                dir.nor();
                dir.scl(radius + obj.radius + epsilon);
                
                position.set(obj.position.x + dir.x, obj.position.y + dir.y);
            }
            else if(obj instanceof SpaceShip){
                dir.nor();
                dir.scl(-1.0f);
                dir.scl(radius + obj.radius + epsilon);
                
                obj.position.set(position.x + dir.x, position.y + dir.y);
            }
            
            return true;
        }

        return false;
    }
    
    public void handleCollisions(){
        if (position.x - radius <= -SCREEN_WIDTH) {
            position.x = -SCREEN_WIDTH + radius + epsilon;
            speed.scl(-0.2f);
            if(this instanceof LaserShot){
                GameScreen.toRemoveRigidBody.add(this);
            }
        }
        else if(position.x + radius >= SCREEN_WIDTH){
            position.x = SCREEN_WIDTH - radius - epsilon;
            speed.scl(-0.2f);
            if(this instanceof LaserShot){
                GameScreen.toRemoveRigidBody.add(this);
            }
        }

        if (position.y - radius <= -SCREEN_HEIGHT) {
            position.y = -SCREEN_HEIGHT + radius + epsilon;
            speed.scl(-0.2f);
            if(this instanceof LaserShot){
                GameScreen.toRemoveRigidBody.add(this);
            }
        }
        else if(position.y + radius >= SCREEN_HEIGHT){
            position.y = SCREEN_HEIGHT - radius - epsilon;
            speed.scl(-0.2f);
            if(this instanceof LaserShot){
                GameScreen.toRemoveRigidBody.add(this);
            }
        }
        
        for(SpaceObject body : GameScreen.rigidBodies){
            if(exceptions.contains(body)){
                continue;
            }
            
            if(this.collidesWith((SphereObject)body)){
                collisionResult((SphereObject)body);
            }
        }
    }
    
    public abstract void collisionResult(SphereObject body);

}
