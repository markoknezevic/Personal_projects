package com.project.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.math.Vector3;
import com.project.components.*;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.EnemyAnimations;
import com.project.managers.Settings;
import com.project.screens.InstructionScreen;
import com.project.states.GameState;

public class MenuSystem extends EntitySystem
{
    private Sprite gameTitleSpriteChristmas;
    private Sprite gameTitleSprite;

    private ImmutableArray<Entity> entities;

    private PerspectiveCamera perspectiveCamera;
    private Environment environment;
    private ModelBatch modelBatch;
    private DirectionalShadowLight shadowLight;
    private ModelBatch shadowBatch;
    private SpriteBatch spriteBatch;

    private Core game;

    private int counter = 0;

    public MenuSystem(Core game)
    {
        initializeGame(game);
        initializeSpriteBatchAndSprites();
        initializePerspectveCamera();
        initializeEnvironment();
        initializeModelBatch();
    }

    private void initializeGame(Core game)
    {
        this.game = game;
    }

    private void initializeSpriteBatchAndSprites()
    {
        spriteBatch = new SpriteBatch();

        gameTitleSprite = new Sprite(Assets.__GAME_TITLE_TEXTURE__);
        gameTitleSpriteChristmas = new Sprite(Assets.__GAME_TITLE_CHRISTMAS_TEXTURE__);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        entities = engine.getEntitiesFor(Family.all(ModelComponent.class).get());
    }

    @Override
    public void update(float deltaTime)
    {
        GameState.__ELAPSED__ += deltaTime;

        drawShadows(deltaTime);

        modelBatch.begin(perspectiveCamera);
        if(GameState.__STARTING_GAME__)
        {
            counter += 1;
            perspectiveCamera.far -= 1f;

            if(perspectiveCamera.far == -1f)
            {
                game.setScreen(new InstructionScreen(game));
            }
        }

        for(Entity e : entities)
        {
            modelBatch.render(e.getComponent(ModelComponent.class).instance, environment);

            if(e.getComponent(AnimationComponent.class) != null)
            {
                e.getComponent(AnimationComponent.class).update(deltaTime);
            }
        }

        if(counter == 40)
        {
            entities.get(5).getComponent(AnimationComponent.class).animate(EnemyAnimations.__DEAD_ANIMATION__[1], 1, 0.5f);
        }
        if(counter == 80)
        {
            entities.get(4).getComponent(AnimationComponent.class).animate(EnemyAnimations.__DEAD_ANIMATION__[0], 1, 0.5f);
        }
        if(counter == 120)
        {
            entities.get(3).getComponent(AnimationComponent.class).animate(EnemyAnimations.__DEAD_ANIMATION__[2], 1, 0.5f);
        }

        modelBatch.end();

        spriteBatch.begin();

        if(Settings.__CHRISTMAS_THEME__)
        {
            spriteBatch.draw(Assets.__SNOWFALL_ANIMATION__.getKeyFrame(GameState.__ELAPSED__), 0, 0, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        }

        if(GameState.__RENDER_MENU_TITLE__)
        {
            if(Settings.__CHRISTMAS_THEME__)
            {
                spriteBatch.draw(gameTitleSpriteChristmas, (float) Core.SCREEN_WIDTH / 2 - 400 , (float) Core.SCREEN_HEIGHT / 2 - 20);
            }
            else
            {
                spriteBatch.draw(gameTitleSprite, (float) Core.SCREEN_WIDTH / 2 - 356, (float) Core.SCREEN_HEIGHT / 2 - 13);
            }
        }

        spriteBatch.end();
    }

    private void initializePerspectveCamera()
    {
        perspectiveCamera = new PerspectiveCamera(GameState.__FIELD_OF_VIEW__, Core.SCREEN_WIDTH, Core.SCREEN_HEIGHT);
        perspectiveCamera.position.set(0, 2, 0);
        perspectiveCamera.lookAt(1, 2 ,0);
        perspectiveCamera.far = 300f;
        perspectiveCamera.update();
    }

    private void initializeEnvironment()
    {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add((this.shadowLight = new DirectionalShadowLight(1024, 1024, 60f, 60f, .1f, 100f)).set(1f, 1f, 0.2f, 0.0f, -35f, -35f));
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0, 0, 0, 1f));
        environment.shadowMap = this.shadowLight;
        environment.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, -1f, -0.8f, -0.2f));
        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    private void drawShadows(float delta)
    {
        shadowLight.begin(Vector3.Zero, perspectiveCamera.direction);
        shadowBatch.begin(shadowLight.getCamera());

        for(int x = 0; x < entities.size(); x++)
        {
            ModelComponent mod = entities.get(x).getComponent(ModelComponent.class);
            shadowBatch.render(mod.instance);
        }

        shadowBatch.end();
        shadowLight.end();
    }

    private void initializeModelBatch()
    {
        modelBatch = new ModelBatch();
    }
}
