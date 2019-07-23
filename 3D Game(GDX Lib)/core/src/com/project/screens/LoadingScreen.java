package com.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.project.game.Assets;
import com.project.game.Core;

public class LoadingScreen implements Screen
{
    private Core game;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont fontForText;

    private Sprite frame = new Sprite(new Texture(Gdx.files.internal("core/assets/textures/frame.jpg")));

    private SpriteBatch spriteBatch;
    private float time = 0;

    public LoadingScreen(Core game)
    {
        initializeGame(game);
        initializeFonts();
        initializeSpriteBatch();
        setupGdxInputs();
    }

    private void initializeGame(Core game)
    {
        this.game = game;
    }

    private void setupGdxInputs()
    {
        Gdx.input.setCursorCatched(true);
    }

    private void initializeFonts()
    {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/fonts/8-BITWONDER.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        fontForText = fontGenerator.generateFont(parameter);
    }

    private void initializeSpriteBatch()
    {
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render(float delta)
    {
        spriteBatch.begin();
        spriteBatch.draw(frame, 0, 0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        fontForText.draw(spriteBatch, "Loading please wait", (float) Core.SCREEN_WIDTH / 2 - 250, (float) Core.SCREEN_HEIGHT / 2 + 20);
        spriteBatch.end();

        time += delta;

        if(time > 1)
        {
            new Assets();
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void dispose()
    {
        spriteBatch.dispose();
        fontGenerator.dispose();
        fontForText.dispose();
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
