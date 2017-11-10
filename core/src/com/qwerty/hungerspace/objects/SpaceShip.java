package com.qwerty.hungerspace.objects;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpaceShip {
    public TextureRegion spaceShip;
    Animation<TextureRegion> spaceAnim;
    float animTime;
    
    public Vector2 direction;
    public float speed;
    public Vector2 position;
    
    float scale;
    
    public SpaceShip(List<TextureRegion> animations){
        
        spaceAnim = new Animation(0.05f, animations.toArray());
        spaceAnim.setPlayMode(PlayMode.LOOP);
        
        animTime = 0.0f;
        spaceShip = (TextureRegion) spaceAnim.getKeyFrame(animTime, true);
        
        scale = 0.2f;
        
        direction = new Vector2(0.0f, 0.0f);
        speed = 50.0f;
        position = new Vector2(0.0f, 0.0f);
    }
    
    public void update(float delta){
        position.add(speed * direction.x * delta, speed * direction.y * delta);
        
        animTime += delta;
        spaceShip = (TextureRegion) spaceAnim.getKeyFrame(animTime);
    }
    
    public void render(SpriteBatch batch){
        batch.draw(spaceShip, position.x, position.y, 0, 0, spaceShip.getRegionWidth(), 
                spaceShip.getRegionHeight(), scale, scale, 0.0f, true);
    }

}
