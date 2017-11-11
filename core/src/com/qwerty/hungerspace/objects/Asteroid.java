package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.HungerSpaceMain;
import com.qwerty.hungerspace.assets.AssetHolder;
import com.qwerty.hungerspace.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Asteroid extends SphereObject{
    List<TextureRegion> poofFrames = new ArrayList<TextureRegion>();

    public Asteroid(TextureRegion image, float scale, Vector2 position) {
        this.scale = scale;
        this.position = position;
        objectImage = image;
        
        health = 50;

        updateCollider(10);

        exceptions.add(this);

        for (int i = 0; i < 17; i++) {
            TextureRegion tr = AssetHolder.textureAtlas.findRegion("Effects/Galaxy/" + i);
            GameScreen.textureRegions.put("poof" + i, tr);
            poofFrames.add(new TextureRegion(tr));
        }
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

        Poof poof = new Poof(poofFrames);
        poof.position.set(position);
        GameScreen.particles.add(poof);

        HungerSpaceMain.sounds.get("rockDestroy").play();
    }

    @Override
    public void collisionResult(SphereObject body) {
        speed = speed.scl(-1.0f);
    }
}
