package com.project.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.math.Vector3;
import com.project.components.*;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.EntityFactory;
import com.project.managers.GifDecoder;
import com.project.managers.Settings;
import com.project.screens.MenuScreen;
import com.project.states.GameState;

import java.awt.*;

public class RenderSystem extends EntitySystem
{
    private PerspectiveCamera perspectiveCamera, gunCamera;
    private DirectionalShadowLight shadowLight;
    private SpriteBatch spriteBatch = new SpriteBatch();
    private ImmutableArray<Entity> entities;
    private ModelBatch modelBatch;
    private Environment environment, gunEnv;
    private Engine engine;

    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont fontForText;

    private Sprite blood = new Sprite(Assets.__BLOOD_TEXTURE__);

    public Entity gun;

    private float bloodTime = 0;

    public RenderSystem()
    {
        GameState.__RENDER_BLOOD__ = false;
        GameState.__RENDER_FRAME_RATE__ = false;
        initializeFonts();
        initializePerspectiveCamera();
        initializeGunCamera();
        initializeEnvironment();
        initializeModelBatch();

    }

    private void initializeFonts()
    {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("core/assets/fonts/8-BITWONDER.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        fontForText = fontGenerator.generateFont(parameter);
    }

    private void initializePerspectiveCamera()
    {
        perspectiveCamera = new PerspectiveCamera(GameState.__FIELD_OF_VIEW__, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        perspectiveCamera.far = 1000f;
    }

    private void initializeGunCamera()
    {
        gunCamera = new PerspectiveCamera(GameState.__FIELD_OF_VIEW__, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        gunCamera.far = 100f;
        gunEnv = new Environment();
        gunEnv.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        gunEnv.add(new DirectionalLight().set(0.9f, 0.9f, 0.9f, 1f, -0.8f, -0.2f));

    }

    public PerspectiveCamera getPerspectiveCamera()
    {
        return perspectiveCamera;
    }

    private void initializeEnvironment()
    {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, -1f, -0.8f, -0.2f));
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0, 0, 0, 1f));
        shadowLight = new DirectionalShadowLight(1024 * 5, 1024 * 5, 200f, 200f, 1f, 300f);
        shadowLight.set(0.8f, 0.8f, 0.8f, 0, -0.1f, 0.1f);
        environment.add(shadowLight);
        environment.shadowMap = shadowLight;
    }

    private void initializeModelBatch()
    {
        modelBatch = new ModelBatch();
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        this.engine = engine;
        entities = engine.getEntitiesFor(Family.all(ModelComponent.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        spriteBatch.begin();
        spriteBatch.draw(Assets.__FRAME__, 0, 0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        spriteBatch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.F))
        {
            GameState.__RENDER_FRAME_RATE__ = ! GameState.__RENDER_FRAME_RATE__;
        }

        drawShadows(deltaTime);
        drawModels(deltaTime);

        if(GameState.__RENDER_FRAME_RATE__)
        {
            Assets.__FRAME_RATE__.update();
            Assets.__FRAME_RATE__.render();
        }

        if(GameState.__START_DEAD_COUNTER__)
        {
            spriteBatch.begin();
            fontForText.draw(spriteBatch, "Score  :  " + PlayerComponent.__SCORE__, Core.SCREEN_WIDTH / 2 - 150 , Core.SCREEN_HEIGHT / 2 + 10 );
            spriteBatch.end();
        }
    }

    private void drawModels(float delta)
    {
        modelBatch.begin(perspectiveCamera);

        for(int i=0;i<entities.size();i++)
        {
            if (entities.get(i).getComponent(GunComponent.class) == null)
            {
                ModelComponent modelComponent = entities.get(i).getComponent(ModelComponent.class);


                modelBatch.render(modelComponent.instance, environment);


                if(entities.get(i).getComponent(AnimationComponent.class) != null && !Settings.__PAUSED__)
                {
                    entities.get(i).getComponent(AnimationComponent.class).update(delta);
                }
            }
        }

        GameState.__ELAPSED__ += delta;
        modelBatch.end();

        drawGun(delta);

        if(Settings.__CHRISTMAS_THEME__)
        {
            spriteBatch.begin();
            spriteBatch.draw(Assets.__SNOWFALL_ANIMATION__.getKeyFrame(GameState.__ELAPSED__), 0, 0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
            spriteBatch.end();
        }

        if(PlayerComponent.__IS_DEAD__)
        {
            Gdx.gl.glClearColor(0, 0, 0, 0.2f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }

        if(GameState.__RENDER_BLOOD__ || PlayerComponent.__IS_DEAD__)
        {
            if(!Settings.__PAUSED__)
            {
                bloodTime += delta;
            }

            spriteBatch.begin();
            spriteBatch.draw(blood,0 ,0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
            spriteBatch.end();

            if(bloodTime > 1)
            {
                bloodTime = 0;
                GameState.__RENDER_BLOOD__ = false;
            }
        }
    }

    private void drawShadows(float delta)
    {
        shadowLight.begin(Vector3.Zero, perspectiveCamera.direction);
        modelBatch.begin(shadowLight.getCamera());

        for(int x = 0; x < entities.size(); x++)
        {
            if(entities.get(x).getComponent(EnemyComponent.class) != null)
            {
                ModelComponent mod = entities.get(x).getComponent(ModelComponent.class);
                modelBatch.render(mod.instance);
            }

            if(entities.get(x).getComponent(AnimationComponent.class) != null && !Settings.__PAUSED__)
            {
                entities.get(x).getComponent(AnimationComponent.class).update(delta);
            }
        }

        modelBatch.end();
        shadowLight.end();
    }

    private void drawGun(float delta)
    {
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(gunCamera);
        modelBatch.render(gun.getComponent(ModelComponent.class).instance, gunEnv);
        gun.getComponent(AnimationComponent.class).update(delta);
        modelBatch.end();
    }

    public void dispose()
    {
        modelBatch.dispose();
    }
}
