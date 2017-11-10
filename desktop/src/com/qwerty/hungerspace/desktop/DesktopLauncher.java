package com.qwerty.hungerspace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.qwerty.hungerspace.HungerSpaceMain;

public class DesktopLauncher {
    private static boolean rebuildLoadingAtlas = false;
    private static boolean rebuildAtlas = false;
    private static boolean drawDebugOutline = false;
    
	public static void main (String[] arg) {
	    if(rebuildLoadingAtlas){
            Settings settings = new Settings();
            settings.maxWidth = 4096;
            settings.maxHeight = 4096;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "texture-raw/loading", "texture", "loadingAtlas");
        }

	    if(rebuildAtlas){
            Settings settings = new Settings();
            settings.maxWidth = 4096;
            settings.maxHeight = 4096;
            settings.debug = drawDebugOutline;
            TexturePacker.process(settings, "texture-raw/main", "texture", "mainAtlas");
        }

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 711;
        config.height = 400;
		new LwjglApplication(new HungerSpaceMain(), config);
	}
}
