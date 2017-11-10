package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.qwerty.hungerspace.HungerSpaceMain;

import java.util.Random;

/**
 * This screen is introduced at start of application and is shown when the assets are being loaded 
 * in the background.
 * @author shine
 *
 */
public class LoadingScreen extends AbstractScreen{
    private TextureRegion background;
    
    private Sprite square;
    private Sprite circle;
    private Sprite rect;
    private Sprite tri;
    
    private Sprite loadBarEmpty;
    private Sprite loadBarFull;
    
    float loadLength = 0;
    float loadVelocity = 50;            //pixels per sec
    float loadLimit = 0;
    float loadEnd = 400;
    
    float squareAngularVel = -90;        //degrees per sec
    float sqTotalRot = 0;
    float leftEnd = -15;
//    float rightEnd = -25;
    boolean hangAnimFinished = false;
    
    float scaleVel = 10;
    float totalScale = 0;
    float scaleCounter = 0;
    boolean iconScaleAnimFinished = false;
    
    boolean assetsLoaded = false;
    
    public LoadingScreen(HungerSpaceMain game){
        super(game);
        initialize();
    }
    
    @Override
    public void initialize() {
        Random rand = new Random();
        int number = rand.nextInt(4)+1;

        background = assetHolder.loadingAtlas.findRegion("loadingBackground"+number);

        square = new Sprite(assetHolder.loadingAtlas.findRegion("square"));
        circle = new Sprite(assetHolder.loadingAtlas.findRegion("circle"));
        rect = new Sprite(assetHolder.loadingAtlas.findRegion("rectangle"));
        tri = new Sprite(assetHolder.loadingAtlas.findRegion("triangle"));

        loadBarEmpty = new Sprite(assetHolder.loadingAtlas.findRegion("loadBarEmpty"));
        loadBarFull = new Sprite(assetHolder.loadingAtlas.findRegion("loadBarFull"));

        setScreenLayout();
        
        loadMainAssets();
    }

    @Override
    public void update(float delta) {
//        if(!iconScaleAnimFinished){
//            showIconAnim(delta);
//        }
        if(!hangAnimFinished){
            hangIconAnim(delta);
        }
        else{
            loadingAnim(delta);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        
        batch.draw(background, 0, 0, HungerSpaceMain.WIDTH, HungerSpaceMain.HEIGHT);
        square.draw(batch);
        rect.draw(batch);
        circle.draw(batch);
        tri.draw(batch);
        
        loadBarEmpty.draw(batch);
        loadBarFull.draw(batch);
        
        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        
    }
    
    //-----------------------private methods---------------------------------
    
    private void loadMainAssets(){
        assetManager.load("texture/mainAtlas.atlas", TextureAtlas.class);
    }
    
    private void holdMainAssets(){
        assetHolder.mainAtlas = assetManager.get("texture/mainAtlas.atlas", TextureAtlas.class);
    }
    
    private void initializeScreens(){
        game.menuScreen = new MenuScreen(game);
        game.gameScreen = new GameScreen(game);
        game.pauseScreen = new PauseScreen(game);
        game.endScreen = new EndGameScreen(game);
    }
    
    private void setScreenLayout(){
        square.setSize(140, 140);
        square.setOriginCenter();
        square.setCenter(HungerSpaceMain.WIDTH/2, HungerSpaceMain.HEIGHT/2);
//        square.setScale(0);
        
        rect.setSize(90, 25);
        rect.setOriginCenter();
        rect.setCenter(HungerSpaceMain.WIDTH/2, HungerSpaceMain.HEIGHT/2-30);
//        rect.setScale(0);
        
        circle.setSize(40, 40);
        circle.setOriginCenter();
        circle.setCenter(HungerSpaceMain.WIDTH/2-25, HungerSpaceMain.HEIGHT/2+26.25f);
//        circle.setScale(0);
        
        tri.setSize(40, 40);
        tri.setOriginCenter();
        tri.setCenter(HungerSpaceMain.WIDTH/2+25, HungerSpaceMain.HEIGHT/2+26.25f);
//        tri.setScale(0);
        
        square.setOrigin(0, square.getHeight());
        rect.setOrigin(square.getX()-rect.getX(), square.getY()+square.getHeight()-
                rect.getY());
        tri.setOrigin(square.getX()-tri.getX(), square.getY()+square.getHeight()-
                tri.getY());
        circle.setOrigin(square.getX()-circle.getX(), square.getY()+square.getHeight()-
                circle.getY());
        
        loadBarEmpty.setSize(loadEnd, 8);
        loadBarEmpty.setCenter(HungerSpaceMain.WIDTH/2, HungerSpaceMain.HEIGHT/2 - 150);
        
        loadBarFull.setSize(0, 8);
//        loadBarFull.setPosition(loadBarEmpty.getX(), loadBarEmpty.getY());    //for normal loading
        loadBarFull.setCenter(HungerSpaceMain.WIDTH/2, HungerSpaceMain.HEIGHT/2 - 150);     //for middle loading
        
    }
    
    private void loadingAnim(float delta){
        loadLength += delta*loadVelocity;
        
        assetManager.update();                              //load next asset
        loadLimit = assetManager.getProgress()*loadEnd;
        
        if(loadLength > loadLimit){
            loadLength = loadLimit;
        }
        
        loadBarFull.setSize(loadLength, loadBarEmpty.getHeight());
        loadBarFull.setCenter(HungerSpaceMain.WIDTH/2, HungerSpaceMain.HEIGHT/2 - 150);     //for middle loading
        
        // all assets loaded
        if(loadLength >= loadEnd){
            // hold the loaded assets in asset holder
            holdMainAssets();
            
            // initialize all other screens
            initializeScreens();
            
            game.menuScreen.setBackground(background);
            screensManager.setScreen(game.menuScreen);
        }
    }
    
//    private void showIconAnim(float delta){
//        if(scaleVel != 0){
//            totalScale += scaleVel*delta;
//        }
//        
//        if(totalScale > 2 && scaleVel >0){
//            scaleVel = -scaleVel;
//        }
//        else if(totalScale < 1 && scaleVel < 0){
//            totalScale = 1;
//            scaleVel = 0;
//        }
//        
//        if(scaleCounter == 0){
//            square.setScale(totalScale);
//        }
//        else if(scaleCounter == 1){
//            rect.setScale(totalScale);
//        }
//        else if(scaleCounter == 2){
//            circle.setScale(totalScale);
//        }
//        else if(scaleCounter == 3){
//            tri.setScale(totalScale);
//        }
//        
//        if(scaleVel == 0){
//            if(scaleCounter < 4){
//                scaleCounter++;
//                scaleVel = 10;
//                totalScale = 0;
//            }
//            else{
//                iconScaleAnimFinished = true;
//                
//                square.setOrigin(0, square.getHeight());
//                rect.setOrigin(square.getX()-rect.getX(), square.getY()+square.getHeight()-
//                        rect.getY());
//                tri.setOrigin(square.getX()-tri.getX(), square.getY()+square.getHeight()-
//                        tri.getY());
//                circle.setOrigin(square.getX()-circle.getX(), square.getY()+square.getHeight()-
//                        circle.getY());
//            }
//        }
//    }
    
    private void hangIconAnim(float delta){
        if(sqTotalRot > leftEnd && squareAngularVel < 0){
            sqTotalRot += squareAngularVel*delta;
            
            square.setRotation(sqTotalRot);
            rect.setRotation(sqTotalRot);
            tri.setRotation(sqTotalRot);
            circle.setRotation(sqTotalRot);
        }
        else{
            hangAnimFinished = true;
        }
//        else if(squareAngularVel < 0){
//            squareAngularVel = -squareAngularVel;
//            squareAngularVel -= 10;
//            leftEnd += 5;
//            if(leftEnd > -45){
//                leftEnd = 45;
//            }
//        }
//        
//        if(sqTotalRot < rightEnd && squareAngularVel > 0){
//            sqTotalRot += squareAngularVel*delta;
//            
//            square.setRotation(sqTotalRot);
//            rect.setRotation(sqTotalRot);
//            tri.setRotation(sqTotalRot);
//            circle.setRotation(sqTotalRot);
//        }
//        else if(squareAngularVel > 0){
//            squareAngularVel = -squareAngularVel;
//            squareAngularVel += 10;
//            rightEnd -= 5;
//            if(rightEnd < -45){
//                rightEnd = 45;
//            }
//        }
//        
//        if(squareAngularVel == 0){
//            hangAnimFinished = true;
//        }
    }

}
