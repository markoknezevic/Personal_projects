package com.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.Settings;

public class LeaderboardsScreen implements Screen
{
    private Core game;
    private Stage stage;

    private TextButton backButton;
    private Label [] label;

    private SpriteBatch spriteBatch;

    public LeaderboardsScreen(Core game)
    {
        spriteBatch = new SpriteBatch();
        initializeGame(game);
        initializeStage();
        setWidgets();
        configureWidgets();
        setListeners();
    }

    private void initializeGame(Core game)
    {
        this.game = game;
        Assets.__GAME_MUSIC__.stop();
    }

    private void initializeStage()
    {
        stage = new Stage(new FitViewport(Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT));
    }

    private void setWidgets()
    {
        backButton = new TextButton("Back", Assets.__LIBGDX_FLATEARTH_SKIN__);
        label = new Label[5];

        for(int i = 0; i < 5; i++)
        {
            label[i] = new Label(i + 1 + ") " + Settings.__USERNAMES__.get(i) + " - " + Settings.__HIGHSCORES__.get(i), Assets.__LIBGDX_FLATEARTH_SKIN__);
        }
    }

    private void configureWidgets()
    {
        backButton.setSize(128, 64);
        backButton.setPosition(Core.SCREEN_WIDTH - backButton.getWidth() - 5, 5);

        stage.addActor(backButton);

        int y = 0;

        for(int i = 0; i < label.length; i++)
        {
            label[i].setFontScale(3);
            label[i].setPosition(15, Core.SCREEN_HEIGHT - label[i].getHeight() - 25 - y);
            y += 96;
            stage.addActor(label[i]);
        }
    }

    private void setListeners()
    {
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(Assets.__FRAME__, 0, 0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose()
    {
        stage.dispose();
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
