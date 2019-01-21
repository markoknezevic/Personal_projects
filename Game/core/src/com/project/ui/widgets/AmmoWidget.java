package com.project.ui.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.project.components.PlayerComponent;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.Settings;

public class AmmoWidget extends Actor
{

    private Label ammoLabel;
    private Label reloadingLabel;

    public AmmoWidget()
    {
        ammoLabel = new Label("", Assets.__LIBGDX_FLATEARTH_SKIN__);
        reloadingLabel = new Label("RELOADING", Assets.__LIBGDX_FLATEARTH_SKIN__);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if(Settings.__PAUSED__) return;

        ammoLabel.draw(batch, parentAlpha);

        if (PlayerComponent.__IS_AMMO_EMPTY__)
        {
            reloadingLabel.setText("NO AMMO");
            reloadingLabel.draw(batch, parentAlpha);
        }
        else if (PlayerComponent.__IS_RELOADING__)
        {
            reloadingLabel.setText("RELOADING");
            reloadingLabel.draw(batch, parentAlpha);
        }

    }

    @Override
    public void act(float delta)
    {
        ammoLabel.act(delta);
        ammoLabel.setText("AMMO: " + PlayerComponent.__SHOOT_AMMO__ + " / " + PlayerComponent.__TOTAL_AMMO__);
        reloadingLabel.act(delta);
    }


    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        ammoLabel.setPosition(x, y);
        reloadingLabel.setPosition((float) Core.SCREEN_WIDTH / 2 - reloadingLabel.getWidth() / 2, (float) Core.SCREEN_HEIGHT / 2 + reloadingLabel.getHeight() / 2);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        ammoLabel.setSize(width, height);
        ammoLabel.setFontScale(1.5f);
    }
}
