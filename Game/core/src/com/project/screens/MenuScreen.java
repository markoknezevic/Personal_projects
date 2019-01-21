package com.project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.Settings;
import com.project.states.GameState;
import com.project.systems.MenuSystem;
import com.project.worlds.MenuWorld;

public class MenuScreen implements Screen
{
    private Core game;
    private Stage stage;

    private TextButton playButton;
    private TextButton leaderboardsButton;
    private TextButton quitButton;
    private TextButton settingsButton;

    private CheckBox checkBox;

    private MenuWorld menuWorld;

    public MenuScreen(Core game)
    {
        if(Settings.__MUSIC_ENABLED__)
        {
            Assets.__MENU_MUSIC__.setLooping(true);
            Assets.__MENU_MUSIC__.play();

        }
        initializeGame(game);
        initializeGameState();
        initializeMenuWorldAndStage();
        setWidgets();
        configureWidgets();
        setListeners();
    }

    private void initializeGame(Core game)
    {
        this.game = game;
    }

    private void initializeGameState()
    {
        Assets.__GAME_MUSIC__.stop();
        GameState.__RENDER_MENU_TITLE__ = true;
        GameState.__STARTING_GAME__ = false;
    }

    private void initializeMenuWorldAndStage()
    {
        menuWorld = new MenuWorld(game);
        stage = new Stage(new FitViewport(Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT));
    }

    private void setWidgets()
    {
        playButton = new TextButton("Play", Assets.__LIBGDX_FLATEARTH_SKIN__);
        leaderboardsButton = new TextButton("Leaderboards", Assets.__LIBGDX_FLATEARTH_SKIN__);
        quitButton = new TextButton("Quit", Assets.__LIBGDX_FLATEARTH_SKIN__);
        checkBox = new CheckBox("Christmas theme", Assets.__LIBGDX_FLATEARTH_SKIN__);
        settingsButton = new TextButton("Settings", Assets.__LIBGDX_FLATEARTH_SKIN__);
    }

    private void configureWidgets()
    {
        playButton.setSize(200, 50);
        playButton.setPosition((float) Core.SCREEN_WIDTH / 2 - playButton.getWidth() / 2, (float) Core.SCREEN_HEIGHT / 2 - 100);

        leaderboardsButton.setSize(200, 50);
        leaderboardsButton.setPosition((float) Core.SCREEN_WIDTH / 2 - leaderboardsButton.getWidth() / 2 - 100, (float) Core.SCREEN_HEIGHT / 2 - 170);

        quitButton.setSize(200, 50);
        quitButton.setPosition((float) Core.SCREEN_WIDTH / 2 - quitButton.getWidth() / 2 , (float) Core.SCREEN_HEIGHT / 2 - 240);

        checkBox.setSize(200, 50);
        checkBox.setPosition((float) Core.SCREEN_WIDTH / 2 - checkBox.getWidth() / 2, (float) Core.SCREEN_HEIGHT / 2 - 300);
        checkBox.setChecked(Settings.__CHRISTMAS_THEME__);

        settingsButton.setSize(200, 50);
        settingsButton.setPosition(leaderboardsButton.getX() + settingsButton.getWidth() + 20, leaderboardsButton.getY());

        stage.addActor(playButton);
        stage.addActor(leaderboardsButton);
        stage.addActor(quitButton);
        stage.addActor(checkBox);
        stage.addActor(settingsButton);
    }

    private void setListeners()
    {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.__RENDER_MENU_TITLE__ = false;
                GameState.__STARTING_GAME__ = true;
                playButton.remove();
                leaderboardsButton.remove();
                quitButton.remove();
                checkBox.remove();
                settingsButton.remove();
                Gdx.input.setCursorCatched(true);
            }
        });

        leaderboardsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LeaderboardsScreen(game));
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Settings.__MUSIC_ENABLED__)
                {
                    Assets.__MENU_MUSIC__.stop();
                }
                Gdx.app.exit();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Settings.__CHRISTMAS_THEME__ = checkBox.isChecked();
            }
        });

        Gdx.input.setCursorCatched(false);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        stage.act(delta);
        menuWorld.render(delta);
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
