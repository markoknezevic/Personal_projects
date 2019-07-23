package com.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.project.game.Core;
import com.project.managers.Settings;
import com.project.states.GameState;
import com.project.ui.GameUI;
import com.project.worlds.GameWorld;

public class GameScreen implements Screen
{
    private Core game;
    private GameWorld gameWorld;
    private GameUI gameUI;

    public GameScreen(Core game)
    {
        initializeBullet();
        initializeGame(game);
        initializeGameUIAndGameWorld();
        setupGdxInputs();
    }

    private void initializeBullet()
    {
        Bullet.init();
    }

    private void initializeGame(Core game)
    {
        this.game = game;
        Settings.__PAUSED__ = false;
    }

    private void initializeGameUIAndGameWorld()
    {
        gameUI = new GameUI(game);
        gameWorld = new GameWorld(gameUI);
    }

    private void setupGdxInputs()
    {
        Gdx.input.setInputProcessor(gameUI.stage);
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render(float delta)
    {
        gameUI.update(delta);
        gameWorld.render(delta);
        gameUI.render();

        if(GameState.__START_DEAD_COUNTER__ && GameState.__DEAD_PLAYER_COUNTER_SCREEN__ > 1f)
        {
            gameUI.gameOverWidget.gameOverButtons();
        }
    }

    @Override
    public void dispose()
    {
        gameWorld.dispose();
        gameUI.dispose();
    }

    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void show() { }

}
