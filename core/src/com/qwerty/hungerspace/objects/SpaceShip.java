package com.qwerty.hungerspace.objects;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SpaceShip {
    public TextureRegion spaceShip;
    Animation<TextureRegion> spaceAnim;
    float animTime;
    public Vector2 speed = new Vector2();
    public Vector2 position = new Vector2();
    public float direction;

    private float scale;
    private float accelerationFactor;
    private boolean isAccelerating;

    public SpaceShip(List<TextureRegion> animations, float scale, float accelerationFactor) {
        this.scale = scale;
        this.accelerationFactor = accelerationFactor;

        spaceAnim = new Animation(0.05f, animations.toArray());
        spaceAnim.setPlayMode(PlayMode.LOOP);
        
        animTime = 0.0f;
        spaceShip = (TextureRegion) spaceAnim.getKeyFrame(animTime, true);
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
        spaceShip = (TextureRegion) spaceAnim.getKeyFrame(animTime);

        isAccelerating = false;
    }
    
    public void render(SpriteBatch batch) {
        batch.draw(spaceShip, position.x - spaceShip.getRegionWidth()/2, position.y - spaceShip.getRegionHeight()/2,
                spaceShip.getRegionWidth()/2, spaceShip.getRegionHeight()/2, spaceShip.getRegionWidth(),
                spaceShip.getRegionHeight(), scale, scale, direction * 180 / 3.141f);
    }

    public void applyAcceleration() {
        isAccelerating = true;
    }
}
