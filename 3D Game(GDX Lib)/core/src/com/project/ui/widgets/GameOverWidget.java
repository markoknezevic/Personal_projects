package com.project.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.project.components.PlayerComponent;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.screens.GameScreen;
import com.project.screens.MenuScreen;
import com.project.screens.SubmitScoreScreen;

public class GameOverWidget extends Actor
{
    private Core game;
    private Stage stage;
    private Image image;
    private TextButton retryB, menuB, quitB, submit;

    public GameOverWidget(Core game, Stage stage)
    {
        this.game = game;
        this.stage = stage;
        setWidgets();
        setListeners();
    }

    private void setWidgets()
    {
        image = new Image(Assets.__GAME_OVER_TEXTURE__);
        retryB = new TextButton("Retry", Assets.__LIBGDX_FLATEARTH_SKIN__);
        menuB = new TextButton("Menu", Assets.__LIBGDX_FLATEARTH_SKIN__);
        quitB = new TextButton("Quit", Assets.__LIBGDX_FLATEARTH_SKIN__);
        submit = new TextButton("Submit", Assets.__LIBGDX_FLATEARTH_SKIN__);
    }

    private void setListeners()
    {
        retryB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        menuB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        quitB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SubmitScoreScreen(game, PlayerComponent.__SCORE__));
            }
        });

    }

    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(0, 0);
        image.setPosition(x - 90, y + 32);
        retryB.setPosition(x - 85, y - 96);
        menuB.setPosition(x - 40 + menuB.getWidth() - 25, y - 96);
        submit.setPosition(x  - 50 + retryB.getWidth() + menuB.getWidth(), y - 96);
        quitB.setPosition( x  - 50 + retryB.getWidth() + menuB.getWidth() + submit.getWidth() + 15 , y - 96);
    }

    @Override
    public void setSize(float width, float height)
    {
        super.setSize(width, height);
        retryB.setSize(width / 2.5f, height / 2f);
        menuB.setSize(width / 2.5f, height / 2f);
        quitB.setSize(width / 2.5f, height / 2f);
        submit.setSize(width / 2.5f, height / 2f);
    }

    public void gameOver()
    {
        stage.addActor(image);

    }

    public void gameOverButtons()
    {
        stage.addActor(retryB);
        stage.addActor(menuB);
        stage.addActor(quitB);
        stage.addActor(submit);
        stage.unfocus(stage.getKeyboardFocus());
        Gdx.input.setCursorCatched(false);
    }
}
