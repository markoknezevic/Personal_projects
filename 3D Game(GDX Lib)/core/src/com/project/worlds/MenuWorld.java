package com.project.worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector3;
import com.project.components.AnimationComponent;
import com.project.components.ModelComponent;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.EnemyAnimations;
import com.project.managers.Settings;
import com.project.systems.MenuSystem;

public class MenuWorld
{
    private Core game;
    private Engine engine;

    public MenuWorld(Core game)
    {
        initializeGame(game);
        initializeEngine();
        initializeLandScape();
    }

    private void initializeGame(Core game)
    {
        this.game = game;
    }

    private void initializeEngine()
    {
        engine = new Engine();
        engine.addSystem(new MenuSystem(game));
    }

    public void render(float delta)
    {
        engine.update(delta);
    }

    private void initializeLandScape()
    {
        ModelComponent groundModelComponent = new ModelComponent(Assets.__GROUND_MODEL__, 0, 0, 0);
        Entity groundModel = new Entity();
        groundModel.add(groundModelComponent);

        Entity wallModel = new Entity();
        ModelComponent wallModelComponent = new ModelComponent(Assets.__WALL_MODEL__, 0, 0, 0);
        wallModel.add(wallModelComponent);

        Entity skullModel = new Entity();
        ModelComponent skullModelComponent = new ModelComponent(Assets.__SKULL_MODEL__, 0 ,0, 0);
        skullModel.add(skullModelComponent);

        Entity ghouldModel1 = new Entity();
        ModelComponent ghoulModelComponent1 = new ModelComponent(Assets.__GHOUL_MODEL__, 10, 3.6f, 2);
        ghouldModel1.add(ghoulModelComponent1);
        AnimationComponent animationComponent = new AnimationComponent(ghouldModel1.getComponent(ModelComponent.class).instance);
        animationComponent.animate(EnemyAnimations.__IDLE_ANIMATION__, -1, 0.5f);
        ghouldModel1.add(animationComponent);

        Entity ghouldModel2 = new Entity();
        ModelComponent ghoulModelComponent2 = new ModelComponent(Assets.__GHOUL_MODEL__, 15, 3.6f, -9);
        ghoulModelComponent2.instance.transform.rotate(Vector3.Y, -30);
        ghouldModel2.add(ghoulModelComponent2);
        AnimationComponent animationComponent1 = new AnimationComponent(ghouldModel2.getComponent(ModelComponent.class).instance);
        animationComponent1.animate(EnemyAnimations.__IDLE_ANIMATION__, -1, 0.5f);
        ghouldModel2.add(animationComponent1);

        Entity ghouldModel3 = new Entity();
        ModelComponent ghoulModelComponent3 = new ModelComponent(Assets.__GHOUL_MODEL__, 25, 3.6f, 16);
        ghoulModelComponent3.instance.transform.rotate(Vector3.Y, 10);
        ghouldModel3.add(ghoulModelComponent3);
        AnimationComponent animationComponent2 = new AnimationComponent(ghouldModel3.getComponent(ModelComponent.class).instance);
        animationComponent2.animate(EnemyAnimations.__IDLE_ANIMATION__, -1, 0.5f);
        ghouldModel3.add(animationComponent2);

        engine.addEntity(groundModel);
        engine.addEntity(wallModel);
        engine.addEntity(skullModel);
        engine.addEntity(ghouldModel1);
        engine.addEntity(ghouldModel2);
        engine.addEntity(ghouldModel3);
    }

    public void dispose() { }
}
