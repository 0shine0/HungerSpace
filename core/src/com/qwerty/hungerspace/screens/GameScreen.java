package com.qwerty.hungerspace.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.qwerty.hungerspace.HungerSpaceMain;
import com.qwerty.hungerspace.objects.Asteroid;
import com.qwerty.hungerspace.objects.SpaceObject;
import com.qwerty.hungerspace.objects.SpaceShip;

import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_HEIGHT;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_WIDTH;

/**
 * This screen contains the actual gameplay mechanics and represents what the user the user will play.
 *
 */
public class GameScreen extends AbstractScreen {
    private Map<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>();
    public static List<SpaceObject> rigidBodies = new ArrayList<SpaceObject>();
    
    SpaceShip playerShip;

    private final int mapWidth = 12;
    private final int mapHeight = 24;
    private final int gridDimension = 32;
    boolean[][] map = new boolean[mapWidth][mapHeight];

    private Vector2 cameraPosition = new Vector2();

    public GameScreen(HungerSpaceMain game) {
        super(game);
        
        textureRegions.put("background", assetHolder.textureAtlas.findRegion("Background/background"));
        textureRegions.put("spaceShip11", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/1"));
        textureRegions.put("spaceShip12", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/2"));
        textureRegions.put("spaceShip13", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/3"));
        textureRegions.put("spaceShip14", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/4"));
        textureRegions.put("spaceShip15", assetHolder.textureAtlas.findRegion("Blue/Small_ship_blue/5"));
        textureRegions.put("brownAestroid", assetHolder.textureAtlas.findRegion("Aestroids/aestroid_brown"));
        
        List<TextureRegion> spaceShip = new ArrayList<TextureRegion>();
        spaceShip.add(new TextureRegion(textureRegions.get("spaceShip11")));
        spaceShip.add(new TextureRegion(textureRegions.get("spaceShip12")));
        spaceShip.add(new TextureRegion(textureRegions.get("spaceShip13")));
        spaceShip.add(new TextureRegion(textureRegions.get("spaceShip14")));
        spaceShip.add(new TextureRegion(textureRegions.get("spaceShip15")));

        playerShip = new SpaceShip(spaceShip, 0.2f, 500);
        rigidBodies.add(playerShip);
        rigidBodies.add(new Asteroid(new TextureRegion(textureRegions.get("brownAestroid")), 0.3f, new Vector2(150.0f, 150.0f)));

        Random random = HungerSpaceMain.getRandom();
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                map[i][j] = (random.nextInt(2) == 0);
            }
        }

        /*
        boolean[] cellStatus = new boolean[9];
        for (int iteration = 0; iteration < 5; iteration++) {
            for (int i = 0; i < mapWidth; i++) {
                for (int j = 0; j < mapHeight; j++) {
                    int iter = 0;
                    for (int ii = -1; ii < 2; ii++) {
                        for (int jj = -1; jj < 2; jj++) {
                            cellStatus[iter++] = getMapAt(i + ii, )
                        }
                    }

                    boolean
                    boolean up = getMapAt(i - 1, j - 1);
                }
            }
        }
        */
    }

    @Override
    public void update(float delta) {
        int mouseX = Gdx.input.getX();
        int mouseY = SCREEN_HEIGHT - Gdx.input.getY();

        float worldX = mouseX + cameraPosition.x - SCREEN_WIDTH / 2;
        float worldY = mouseY + cameraPosition.y - SCREEN_HEIGHT / 2;

        playerShip.direction = (float)(-Math.PI / 2 + Math.atan2(worldY - playerShip.position.y, worldX - playerShip.position.x));

        if(Gdx.input.isKeyPressed(Keys.W)) {
            playerShip.applyAcceleration();
        }

        playerShip.update(delta);
        cameraPosition.set(playerShip.position.x, playerShip.position.y);

        camera.position.set(cameraPosition, 0);
        camera.update();
    }

    @Override
    public void render(SpriteBatch batch) {
        // TODO Auto-generated method stub
        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        batch.draw(textureRegions.get("background"), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        playerShip.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

    private boolean getMapAt(int i, int j) {
        if (i >= 0 && j >= 0 && i < mapWidth && j < mapHeight) {
            return map[i][j];
        }

        return false;
    }
}
