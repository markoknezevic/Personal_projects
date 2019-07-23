package com.project.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.Settings;
import com.project.screens.GameScreen;
import com.project.screens.MenuScreen;

public class PauseWidget extends Actor
{
    private Core game;
    private Window window;

    private TextButton closeDialog, restartButton, quitButton, menuButton;
    private Stage stage;
    private Table table;
    private Slider slider;
    private Label label, text;

    public PauseWidget(Core game, Stage stage)
    {
        this.game = game;
        this.stage = stage;
        setWidgets();
        configureWidgets();
        setListeners();
    }

    private void setWidgets()
    {
        table = new Table();
        slider = new Slider(0.01f, 0.5f, 0.01f, false, Assets.__LIBGDX_DEFAULT_SKIN__);
        window = new Window("Pause", Assets.__LIBGDX_DEFAULT_SKIN__);
        slider.setValue(Settings.__MOUSE_SENSITIVITY__);
        closeDialog = new TextButton("x", Assets.__LIBGDX_DEFAULT_SKIN__);
        closeDialog.setSize(10, 10);
        restartButton = new TextButton("Restart", Assets.__LIBGDX_DEFAULT_SKIN__);
        quitButton = new TextButton("Quit", Assets.__LIBGDX_DEFAULT_SKIN__);
        menuButton = new TextButton("Menu", Assets.__LIBGDX_DEFAULT_SKIN__);

        label = new Label("", Assets.__LIBGDX_DEFAULT_SKIN__);
        text = new Label("Mouse sensitivity", Assets.__LIBGDX_DEFAULT_SKIN__);
    }

    private void configureWidgets()
    {
        window.getTitleTable().add(closeDialog).height(window.getPadTop());

        table.add(restartButton).width(100);
        table.row();
        table.add(label);
        table.row();
        table.add(menuButton).width(100);
        table.row();
        table.add(label);
        table.row();
        table.add(quitButton).width(100);
        table.row();
        table.add(label);
        table.row();
        table.add(text);
        table.row();
        table.add(slider);

        window.add(table);
    }

    private void setListeners()
    {
        super.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE) {
                    handleUpdates();
                    return true;
                }
                return false;
            }
        });

        closeDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleUpdates();
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

       slider.addListener(new DragListener() {
           @Override
           public void drag(InputEvent event, float x, float y, int pointer) {
               Settings.__MOUSE_SENSITIVITY__ = slider.getValue();
           }
       });

       slider.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               Settings.__MOUSE_SENSITIVITY__ = slider.getValue();
           }
       });
    }

    private void handleUpdates()
    {
        if(window.getStage() == null)
        {
            stage.addActor(window);
            Gdx.input.setCursorCatched(false);
            Settings.__PAUSED__ = true;
        }
        else
        {
            window.remove();
            Gdx.input.setCursorCatched(true);
            Settings.__PAUSED__ = false;
        }
    }

    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        window.setPosition((float) Core.SCREEN_WIDTH / 2 - window.getWidth() / 2, (float) Core.SCREEN_HEIGHT / 2 - window.getHeight() / 2);
    }

    @Override
    public void setSize(float width, float height)
    {
        super.setSize(width, height);
        window.setSize(width * 2, height * 2);
    }

}
