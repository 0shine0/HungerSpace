package com.qwerty.hungerspace.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.screens.GameScreen;

import java.util.List;

public class Poof extends SphereObject{
    public Animation<TextureRegion> spaceAnim;
    public float animTime;

    public Poof(List<TextureRegion> animations) {
        health = 9999;

        spaceAnim = new Animation(0.05f, animations.toArray());
        spaceAnim.setPlayMode(PlayMode.LOOP);

        animTime = 0.0f;
        objectImage = spaceAnim.getKeyFrame(animTime, true);

        scale = 1;
    }

    @Override
    public void update(float delta){
        animTime += delta;
        objectImage = spaceAnim.getKeyFrame(animTime);
    }

    public void destroy() {
        objectImage = null;
        GameScreen.particles.remove(this);
    }

    public void collisionResult(SphereObject body) { }
}
