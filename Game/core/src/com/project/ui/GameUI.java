package com.project.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.project.game.Core;
import com.project.systems.PlayerSystem;
import com.project.ui.widgets.*;

public class GameUI
{
    private Core game;
    public Stage stage;
    public HealthWidget healthWidget;
    private ScoreWidget scoreWidget;
    private PauseWidget pauseWidget;
    private AmmoWidget ammoWidget;
    private CrosshairWidget crosshairWidget;
    public GameOverWidget gameOverWidget;

    public GameUI(Core game)
    {
        this.game = game;
        stage = new Stage(new FitViewport(Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT));
        setWidgets();
        configureWidgets();
    }

    private void setWidgets()
    {
        healthWidget = new HealthWidget();
        scoreWidget = new ScoreWidget();
        pauseWidget = new PauseWidget(game, stage);
        crosshairWidget = new CrosshairWidget();
        gameOverWidget = new GameOverWidget(game, stage);
        ammoWidget = new AmmoWidget();
    }

    private void configureWidgets()
    {
        healthWidget.setSize(300, 30);
        healthWidget.setPosition((float) Core.SCREEN_WIDTH / 2 - healthWidget.getWidth() / 2, 0);

        scoreWidget.setSize(140, 25);
        scoreWidget.setPosition((float) Core.SCREEN_WIDTH / 2 - scoreWidget.getWidth() / 2, Core.SCREEN_HEIGHT -  scoreWidget.getHeight());

        pauseWidget.setSize(150, 150);
        pauseWidget.setPosition(Core.SCREEN_WIDTH - pauseWidget.getWidth(), Core.SCREEN_HEIGHT - pauseWidget.getHeight());

        gameOverWidget.setSize(280, 100);
        gameOverWidget.setPosition((float) Core.SCREEN_WIDTH / 2 - 280f / 2, (float) Core.SCREEN_HEIGHT / 2);

        crosshairWidget.setSize(32, 32);
        crosshairWidget.setPosition((float) Core.SCREEN_WIDTH / 2 - 16, (float) Core.SCREEN_HEIGHT / 2 - 16);

        ammoWidget.setSize(150 , 50);
        ammoWidget.setPosition(0, 0);

        stage.addActor(healthWidget);
        stage.addActor(scoreWidget);
        stage.addActor(crosshairWidget);
        stage.addActor(ammoWidget);
        stage.setKeyboardFocus(pauseWidget);
    }

    public void update(float delta)
    {
        stage.act(delta);
    }

    public void render()
    {
        stage.draw();
    }

    public void dispose()
    {
        stage.dispose();
    }
}
