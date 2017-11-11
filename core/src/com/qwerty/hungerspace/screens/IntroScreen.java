package com.qwerty.hungerspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.qwerty.hungerspace.HungerSpaceMain;

import java.util.ArrayList;
import java.util.List;

import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_HEIGHT;
import static com.qwerty.hungerspace.HungerSpaceMain.SCREEN_WIDTH;
import static java.awt.Color.PINK;
import static java.awt.SystemColor.text;

/**
 * This screen is used to represent the ending of a play session after the user has either lost, won
 * or quit the session.
 *
 */
public class IntroScreen extends AbstractScreen {
    private float timeElapsed = 0;

    private BitmapFont font;
    private float lineHeight;
    private static final float margin = 20;

    private Music music;

    public IntroScreen(HungerSpaceMain game) {
        super(game);
        font = new BitmapFont(Gdx.files.internal("fonts/baron-neue.fnt"), Gdx.files.internal("fonts/baron-neue.png"), false);
        lineHeight = font.getLineHeight();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/starWars.mp3"));

        music.play();
    }

    @Override
    public void update(float delta) {
        timeElapsed += delta;

        if (timeElapsed >= 42) {
            screensManager.pushScreen(game.screens.get("menu"));

            music.stop();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (timeElapsed < 7) {
            float y = (SCREEN_HEIGHT - 2*lineHeight - margin) / 2 + 3*margin;

            List<String> lines = new ArrayList<String>();
            lines.add("A long time ago, in a galaxy");
            lines.add("far, far away...");

            font.setColor(1, 1, 1, timeElapsed < 1? timeElapsed: timeElapsed > 6? (7 - timeElapsed) / 2: 1);

            GlyphLayout layout = new GlyphLayout(font, "");

            batch.begin();
            for (String line : lines) {
                layout.setText(font, line);
                final float fontX = (SCREEN_WIDTH - layout.width) / 2;
                font.draw(batch, layout, fontX, y);

                y -= lineHeight + margin;
            }
            batch.end();
        } else if (timeElapsed > 8 && timeElapsed < 15) {
            font.setColor(1, 1, 1, 1);

            float y = (SCREEN_HEIGHT - 2*lineHeight - margin) / 2 + 3*margin;

            List<String> lines = new ArrayList<String>();
            lines.add("HUNGER");
            lines.add("SPACE");

            font.getData().setScale(timeElapsed < 10? 4 / (timeElapsed - 8): timeElapsed > 13? (15 - timeElapsed): 2);
            GlyphLayout layout = new GlyphLayout(font, "");

            batch.begin();
            for (String line : lines) {
                layout.setText(font, line);
                final float fontX = (SCREEN_WIDTH - layout.width) / 2;
                font.draw(batch, layout, fontX, y);

                y -= lineHeight + margin;
            }
            batch.end();
        } else {
            font.getData().setScale(1);

            float y = -margin + (timeElapsed - 15) * 40;

            List<String> lines = new ArrayList<String>();
            lines.add("HONGER*");
            lines.add("SPACE");
            lines.add("*So hungry that I can't even spell right.");
            lines.add("");
            lines.add("It is a period of civil war. Rebel spaceships have been captured,");
            lines.add("and are forced to fight till to the death. Only two ships remain,");
            lines.add("with no poisonous berries in sight to save their lives.");

            GlyphLayout layout = new GlyphLayout(font, "");

            batch.begin();
            for (String line : lines) {
                layout.setText(font, line);
                final float fontX = (SCREEN_WIDTH - layout.width) / 2;
                font.draw(batch, layout, fontX, y);

                y -= lineHeight + margin;
            }
            batch.end();

            if (timeElapsed > 37) {
                music.setVolume((42 - timeElapsed) / 5);
            }
        }
    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
