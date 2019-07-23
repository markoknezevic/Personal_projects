package com.project.ui.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.project.components.PlayerComponent;
import com.project.game.Assets;
import com.project.managers.Settings;

public class ScoreWidget extends Actor
{
    Label label;

    public ScoreWidget()
    {
        label = new Label("", Assets.__LIBGDX_FLATEARTH_SKIN__);
    }

    @Override
    public void act(float delta)
    {
        label.act(delta);
        label.setText("Score: " + PlayerComponent.__SCORE__);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if(Settings.__PAUSED__) return;
        label.draw(batch, parentAlpha);
    }

    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        label.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height)
    {
        super.setSize(width, height);
        label.setSize(width, height);
    }
}
