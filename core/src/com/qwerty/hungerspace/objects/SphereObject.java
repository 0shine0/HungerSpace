package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.math.Vector2;

public class SphereObject extends SpaceObject{
    public float radius;
    
    public void updateCollider(){
        if(objectImage.getRegionWidth() >= objectImage.getRegionHeight()){
            radius = objectImage.getRegionWidth()/2;
        }
        else{
            radius = objectImage.getRegionHeight()/2;
        }
    }
    
    public boolean collidesWith(SphereObject obj){
        return sphereCollision((SphereObject) obj);
    }
    
    private boolean sphereCollision(SphereObject obj){
        if(position.sub(obj.position).len2() <= (radius + obj.radius) * (radius + obj.radius)){
            return true;
        }
        
        return false;
    }

}
