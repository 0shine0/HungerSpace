package com.qwerty.hungerspace.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.qwerty.hungerspace.HungerSpaceMain;
import com.qwerty.hungerspace.objects.Asteroid;
import com.qwerty.hungerspace.objects.Poof;
import com.qwerty.hungerspace.objects.SpaceObject;
import com.qwerty.hungerspace.objects.SpaceShip;

import static com.qwerty.hungerspace.HungerSpaceMain.BACKGROUND_SIZE;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_HEIGHT;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_WIDTH;

/**
 * This screen contains the actual gameplay mechanics and represents what the user the user will play.
 *
 */
public class GameScreen extends AbstractScreen {
    public static Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
    public static List<SpaceObject> rigidBodies = new ArrayList<SpaceObject>();
    public static List<Poof> particles = new ArrayList<Poof>();
    public static List<Poof> toRemoveParticles = new ArrayList<Poof>();

    SpaceShip playerShip;
    SpaceShip enemyShip;
    
    private boolean sendData = false;
    
    private String id;
    private static String serverUrl;
    private Socket socket;

    private final int mapWidth = 12;
    private final int mapHeight = 24;
    private final int gridDimension = 32;
    boolean[][] map = new boolean[mapWidth][mapHeight];

    private Vector2 cameraPosition = new Vector2();
    
    private boolean gameOver = false;
    private boolean won = false;

    public GameScreen(HungerSpaceMain game) {
        super(game);
        
        serverUrl = "localhost";
        
        textureRegions.put("background", assetHolder.textureAtlas.findRegion("Background/background"));
        textureRegions.put("spaceShip11", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/1"));
        textureRegions.put("spaceShip12", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/2"));
        textureRegions.put("spaceShip13", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/3"));
        textureRegions.put("spaceShip14", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/4"));
        textureRegions.put("spaceShip15", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/5"));
        textureRegions.put("laserShot", assetHolder.textureAtlas.findRegion("Blue/bullet"));
        
        textureRegions.put("spaceShip21", assetHolder.textureAtlas.findRegion("Red/small_ship_animation/1"));
        textureRegions.put("spaceShip22", assetHolder.textureAtlas.findRegion("Red/small_ship_animation/2"));
        textureRegions.put("spaceShip23", assetHolder.textureAtlas.findRegion("Red/small_ship_animation/3"));
        textureRegions.put("spaceShip24", assetHolder.textureAtlas.findRegion("Red/small_ship_animation/4"));
        textureRegions.put("spaceShip25", assetHolder.textureAtlas.findRegion("Red/small_ship_animation/5"));
        textureRegions.put("rLaserShot", assetHolder.textureAtlas.findRegion("Red/bullet_red"));
        
        textureRegions.put("brownAestroid", assetHolder.textureAtlas.findRegion("Aestroids/aestroid_brown"));
        textureRegions.put("darkAestroid", assetHolder.textureAtlas.findRegion("Aestroids/aestroid_dark"));
        textureRegions.put("greyAestroid", assetHolder.textureAtlas.findRegion("Aestroids/aestroid_gay_2"));
        textureRegions.put("grey2Aestroid", assetHolder.textureAtlas.findRegion("Aestroids/aestroid_gray"));
        textureRegions.put("boundary", assetHolder.textureAtlas.findRegion("Background/boundary"));
        int m = (HungerSpaceMain.SCREEN_WIDTH * 2)/200;
        int n = (HungerSpaceMain.SCREEN_HEIGHT * 2)/200;
        
        String a1 = "brownAestroid";
        String a2 = "darkAestroid";
        String a3 = "grey2Aestroid";    
        String a;
        Random rand = HungerSpaceMain.getRandom();
        for(int i=0; i< m; i++){
            for(int j=0; j< n; j++){                
                if(rand.nextBoolean()){
                    int choice = rand.nextInt(4);
                    if(choice == 0){
                        a = a1;
                    }else if(choice == 1){
                        a = a2;
                    }else if(choice == 2){
                        a = a3;
                    }else{
                        a = a3;
                    }
                    Gdx.app.log("a", a);
                    rigidBodies.add(new Asteroid(new TextureRegion(textureRegions.get(a)), 0.3f, new Vector2(-HungerSpaceMain.SCREEN_WIDTH + i*200.0f, -HungerSpaceMain.SCREEN_HEIGHT + j*200.0f)));
                }
            }
        }

        Random random = HungerSpaceMain.getRandom();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                map[i][j] = (random.nextInt(2) == 0);
            }
        }
        
        connectSocket();
        configSocketEvents();
        HungerSpaceMain.sounds.put("blaster", Gdx.audio.newSound(Gdx.files.internal("sounds/blasterShoot.ogg")));
        HungerSpaceMain.sounds.put("rockCollision", Gdx.audio.newSound(Gdx.files.internal("sounds/rockCollision.ogg")));
        HungerSpaceMain.sounds.put("rockDestroy", Gdx.audio.newSound(Gdx.files.internal("sounds/rockDestroy.ogg")));
        HungerSpaceMain.sounds.put("rockBulletHit", Gdx.audio.newSound(Gdx.files.internal("sounds/rockBulletHit.ogg")));

    }

    @Override
    public void update(float delta) {
        int mouseX = Gdx.input.getX();
        int mouseY = SCREEN_HEIGHT - Gdx.input.getY();

        float worldX = mouseX + cameraPosition.x - SCREEN_WIDTH / 2;
        float worldY = mouseY + cameraPosition.y - SCREEN_HEIGHT / 2;

        if(playerShip != null){
            playerShip.direction = (float)(-Math.PI / 2 + Math.atan2(worldY - playerShip.position.y, worldX - playerShip.position.x));
        }

        if(Gdx.input.isKeyPressed(Keys.W)) {
            if(playerShip != null){
                playerShip.applyAcceleration();
            }
        }
        if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
            if(playerShip != null){
                playerShip.fireLaserShot(delta);
            }
            if(sendData){
                sendLaserEventToServer(playerShip.position.x, playerShip.position.y, playerShip.direction);
            }
        }
        
        List<SpaceObject> bodies = new ArrayList<SpaceObject>(rigidBodies);
        
        for (SpaceObject rigidBody : bodies) {
            rigidBody.update(delta);
        }

        for (Poof particle : particles) {
            particle.update(delta);

            if (particle.animTime >= particle.spaceAnim.getAnimationDuration()) {
                toRemoveParticles.add(particle);
            }
        }

        particles.removeAll(toRemoveParticles);
        toRemoveParticles.clear();

        cameraPosition.set(playerShip.position.x, playerShip.position.y);
        
        if(playerShip != null){
            cameraPosition.set(playerShip.position.x, playerShip.position.y);
        }

        camera.position.set(cameraPosition, 0);
        camera.update();
        
        if(sendData){
            sendPosToServer(playerShip.position.x, playerShip.position.y, playerShip.direction);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        drawBackground(batch);

        for (SpaceObject rigidBody : rigidBodies) {
            rigidBody.render(batch);
        }

        for (SpaceObject particle : particles) {
            particle.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    private void drawBackground(SpriteBatch batch) {
        int x1 = (int)Math.floor((camera.position.x - SCREEN_WIDTH/2) / BACKGROUND_SIZE);
        int x2 = (int)Math.floor((camera.position.x + SCREEN_WIDTH/2) / BACKGROUND_SIZE);

        int y1 = (int)Math.floor((camera.position.y - SCREEN_HEIGHT/2) / BACKGROUND_SIZE);
        int y2 = (int)Math.floor((camera.position.y + SCREEN_HEIGHT/2) / BACKGROUND_SIZE);

        for (float i = x1; i <= x2; i++) {
            for (float j = y1; j <= y2; j++) {
                batch.draw(textureRegions.get("background"), i * BACKGROUND_SIZE - i, j * BACKGROUND_SIZE - j);
            }
        }

        batch.draw(textureRegions.get("boundary"), -textureRegions.get("boundary").getRegionWidth()/2, -textureRegions.get("boundary").getRegionHeight()/2);
    }
    
    private void sendLaserEventToServer(float posX, float posY, float dir){
        JSONObject data = new JSONObject();
        try{
            data.put("xPos", posX);
            data.put("yPos", posY);
            data.put("direction", dir);
            socket.emit("laserFired", data);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    private void sendPosToServer(float posX, float posY, float dir){
        JSONObject data = new JSONObject();
        try{
            data.put("xPos", posX);
            data.put("yPos", posY);
            data.put("direction", dir);
            socket.emit("updatePos", data);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
    }
    
    public void connectSocket(){
        try{
            socket = IO.socket("http://"+serverUrl+":8081");
            socket.connect();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                Gdx.app.log("Server", "connected");
            }
        }).on("socketID", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
                    Gdx.app.log("My ID", id);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).on("startGame1", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                sendData = true;
                List<TextureRegion> spaceShip = new ArrayList<TextureRegion>();
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip11")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip12")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip13")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip14")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip15")));
                playerShip = new SpaceShip(spaceShip, 0.2f, 500, new Vector2(-HungerSpaceMain.SCREEN_WIDTH/2, 0.0f));
                rigidBodies.add(playerShip);
                
                spaceShip = new ArrayList<TextureRegion>();
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip21")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip22")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip23")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip24")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip25")));
                enemyShip = new SpaceShip(spaceShip, 0.2f, 500, new Vector2(HungerSpaceMain.SCREEN_WIDTH/2, 0.0f));
                rigidBodies.add(enemyShip);
            };
        }).on("startGame2", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                sendData = true;
                List<TextureRegion> spaceShip = new ArrayList<TextureRegion>();
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip11")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip12")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip13")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip14")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip15")));
                playerShip = new SpaceShip(spaceShip, 0.2f, 500, new Vector2(HungerSpaceMain.SCREEN_WIDTH/2, 0.0f));
                rigidBodies.add(playerShip);
                
                spaceShip = new ArrayList<TextureRegion>();
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip21")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip22")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip23")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip24")));
                spaceShip.add(new TextureRegion(textureRegions.get("spaceShip25")));
                enemyShip = new SpaceShip(spaceShip, 0.2f, 500, new Vector2(-HungerSpaceMain.SCREEN_WIDTH/2, 0.0f));
                rigidBodies.add(enemyShip);
            };
        }).on("updatePos", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                JSONObject data = (JSONObject) args[0];
                try {
                    enemyShip.position.x = (float)data.getDouble("xPos");
                    enemyShip.position.y = (float)data.getDouble("yPos");
                    enemyShip.direction = (float)data.getDouble("direction");
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).on("laserFired", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                JSONObject data = (JSONObject) args[0];
                try {
                    Vector2 laserPos = new Vector2();
                    laserPos.x = (float)data.getDouble("xPos");
                    laserPos.y = (float)data.getDouble("yPos");
                    float direction = (float)data.getDouble("direction");
                    
                    enemyShip.fireClientLaserShot(laserPos, direction);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).on("win", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                gameOver = true;
                won = true;
            }
        }).on("lose", new Emitter.Listener() {
            
            @Override
            public void call(Object... args) {
                // TODO Auto-generated method stub
                gameOver = true;
                won = false;
            }
        });
    }
}
