package com.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.Settings;

public class SettingsScreen implements Screen
{
    private Core game;
    private Stage stage;

    private CheckBox enableMusic;
    private CheckBox enableSound;
    private TextButton backButton;

    private SpriteBatch spriteBatch;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter, title;
    private BitmapFont fontForText;
    private BitmapFont fontForTitle;

    public SettingsScreen(Core game)
    {
        initializeGame(game);
        initializeSpriteBatch();
        initializeFonts();
        initializeWidgets();
        Gdx.input.setInputProcessor(stage);
    }

    private void initializeGame(Core game)
    {
        this.game = game;
    }

    private void initializeSpriteBatch()
    {
        spriteBatch = new SpriteBatch();
    }

    private void initializeFonts()
    {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/fonts/8-BITWONDER.TTF"));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;

        title = new FreeTypeFontGenerator.FreeTypeFontParameter();
        title.size = 50;

        fontForText = fontGenerator.generateFont(parameter);
        fontForTitle = fontGenerator.generateFont(title);
    }

    private void initializeWidgets()
    {
        stage = new Stage(new FitViewport(Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT));

        enableMusic = new CheckBox("Enable music", Assets.__LIBGDX_FLATEARTH_SKIN__);
        enableSound = new CheckBox("Enable sound", Assets.__LIBGDX_FLATEARTH_SKIN__);
        backButton = new TextButton("Back", Assets.__LIBGDX_FLATEARTH_SKIN__);

        backButton.setSize(128, 64);
        backButton.setPosition(Core.SCREEN_WIDTH - backButton.getWidth() - 5, 5);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        enableMusic.setSize(200, 50);
        enableMusic.setPosition(10, Core.SCREEN_HEIGHT - 70);
        enableMusic.setChecked(Settings.__MUSIC_ENABLED__);
        enableMusic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.__MUSIC_ENABLED__ = enableMusic.isChecked();
                if(!Settings.__MUSIC_ENABLED__)
                {
                    Assets.__MENU_MUSIC__.stop();
                }
                else
                {
                    Assets.__MENU_MUSIC__.play();
                }
            }
        });

        enableSound.setSize(200, 50);
        enableSound.setPosition(14, Core.SCREEN_HEIGHT - 110);
        enableSound.setChecked(Settings.__SOUND_ENABLED__);
        enableSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.__SOUND_ENABLED__ = enableSound.isChecked();
            }
        });

        stage.addActor(enableMusic);
        stage.addActor(enableSound);
        stage.addActor(backButton);
    }

    @Override
    public void render(float delta)
    {


        spriteBatch.begin();
        spriteBatch.draw(Assets.__FRAME__, 0, 0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);

        fontForTitle.draw(spriteBatch, "Controls", (float) Core.SCREEN_WIDTH / 2 - 200 , Core.SCREEN_HEIGHT - 50);
        fontForText.draw(spriteBatch, "W - Move forward",(float) Core.SCREEN_WIDTH / 2 - 145, Core.SCREEN_HEIGHT - 140);
        fontForText.draw(spriteBatch, "A - Move left",(float) Core.SCREEN_WIDTH / 2 - 105, Core.SCREEN_HEIGHT - 170);
        fontForText.draw(spriteBatch, "D - Move right", (float)Core.SCREEN_WIDTH / 2 - 110, Core.SCREEN_HEIGHT - 200);
        fontForText.draw(spriteBatch, "S - Move backward", (float)Core.SCREEN_WIDTH / 2 - 150, Core.SCREEN_HEIGHT - 230);
        fontForText.draw(spriteBatch, "R - Reload", (float)Core.SCREEN_WIDTH / 2 - 80, Core.SCREEN_HEIGHT - 260);
        fontForText.draw(spriteBatch, "Space - Jump", (float)Core.SCREEN_WIDTH / 2 - 105, Core.SCREEN_HEIGHT - 290);
        fontForText.draw(spriteBatch, "F - Display FPS",(float) Core.SCREEN_WIDTH / 2 - 125, Core.SCREEN_HEIGHT - 320);
        fontForText.draw(spriteBatch, "Esc - Pause game", (float)Core.SCREEN_WIDTH / 2 - 145, Core.SCREEN_HEIGHT - 350);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        fontForTitle.dispose();
        fontForText.dispose();
        fontGenerator.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

}
