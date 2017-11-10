package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameScreensManager {
    private Stack<AbstractScreen> screens;
    private SpriteBatch batch;
    
    public GameScreensManager(SpriteBatch batch){
        screens = new Stack<AbstractScreen>();
        this.batch = batch;
    }
    
    public void pushScreen(AbstractScreen screen){
        screens.push(screen);
        batch.setProjectionMatrix(screens.peek().getCamera().combined);
    }
    
    public void popScreen(){
        screens.pop();
        batch.setProjectionMatrix(screens.peek().getCamera().combined);
    }
    
    public void setScreen(AbstractScreen screen){
        screens.pop();
        screens.push(screen);
        batch.setProjectionMatrix(screens.peek().getCamera().combined);
    }
    
    public void update(float delta){
        screens.peek().update(delta);
    }
    
    public void render(){
        screens.peek().render(batch);
    }
    
    public void dispose(){
        while(!screens.empty()){
            screens.pop().dispose();
        }
    }
}
