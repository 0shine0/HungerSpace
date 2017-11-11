package com.qwerty.hungerspace.objects;

import java.util.ArrayList;
import java.util.List;

import com.qwerty.hungerspace.screens.GameScreen;

public abstract class SphereObject extends SpaceObject{
    public float radius;
    public List<SpaceObject> exceptions = new ArrayList<SpaceObject>();
    
    public void updateCollider(){
        if(objectImage.getRegionWidth() >= objectImage.getRegionHeight()){
            radius = scale * objectImage.getRegionWidth()/2;
        }
        else{
            radius = scale * objectImage.getRegionHeight()/2;
        }
    }

    public boolean collidesWith(SphereObject obj){
        return sphereCollision((SphereObject) obj);
    }
    
    private boolean sphereCollision(SphereObject obj){
        if(Math.pow(position.x - obj.position.x, 2) + Math.pow(position.y - obj.position.y, 2) <= Math.pow(radius + obj.radius, 2)){
            return true;
        }

        return false;
    }
    
    public void handleCollisions(){
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
