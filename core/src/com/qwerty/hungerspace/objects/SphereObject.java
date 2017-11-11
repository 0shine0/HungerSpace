package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.math.Vector2;

public class SphereObject extends SpaceObject{
    public float radius;
    
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

}
