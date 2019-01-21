package com.project.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.project.game.Assets;
import com.project.managers.Settings;

public class CrosshairWidget extends Actor
{
    private Image crossHairDot, crossHairIneerRing;

    public CrosshairWidget()
    {
        crossHairDot = new Image(Assets.__CROSSHAIR_DOT_TEXTURE__);
        crossHairIneerRing = new Image(Assets.__CROSSHAIR_INNER_RING_TEXTURE__);
    }

    @Override
    public void act(float delta)
    {
        if(Settings.__PAUSED__) return;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        if(Settings.__PAUSED__) return;
        crossHairDot.draw(batch, parentAlpha);
        crossHairIneerRing.draw(batch, parentAlpha);

    }

    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
        crossHairDot.setPosition(x - 16, y - 16);
        crossHairIneerRing.setPosition(x - 16, y - 16);
        crossHairIneerRing.setOrigin(crossHairIneerRing.getWidth() / 2, crossHairIneerRing.getHeight() / 2);
    }

    @Override
    public void setSize(float width, float height)
    {
        super.setSize(width, height);
        crossHairDot.setSize(width * 2, height * 2);
        crossHairIneerRing.setSize(width * 2, height * 2);
    }
}


