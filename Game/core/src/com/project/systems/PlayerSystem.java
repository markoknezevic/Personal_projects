package com.project.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.project.components.*;
import com.project.game.Assets;
import com.project.game.Core;
import com.project.managers.EnemyAnimations;
import com.project.managers.Settings;
import com.project.states.GameState;
import com.project.ui.GameUI;
import com.project.worlds.GameWorld;

public class PlayerSystem extends EntitySystem implements EntityListener
{
    private Entity player;
    private GameWorld gameWorld;
    private PlayerComponent playerComponent;
    private GameUI gameUI;
    private CharacterComponent characterComponent;
    private ModelComponent modelComponent;
    private ClosestRayResultCallback rayTestCB;

    private final Vector3 tmp = new Vector3();
    private final Camera camera;

    private Vector3 rayFrom = new Vector3();
    private Vector3 rayTo = new Vector3();

    private int stepCounter = 0;

    public Entity gun;

    public PlayerSystem(Camera camera, GameWorld gameWorld, GameUI gameUI)
    {
        this.camera = camera;
        this.gameWorld = gameWorld;
        this.gameUI = gameUI;
        GameState.__COLLIDING__ = false;
        rayTestCB = new ClosestRayResultCallback(Vector3.Zero, Vector3.Z);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
    }

    @Override
    public void update(float deltaTime)
    {
        if(GameState.__START_DEAD_COUNTER__)
        {
            GameState.__DEAD_PLAYER_COUNTER_SCREEN__ += deltaTime;
        }

        checkGameOver();
        if(player == null)
        {
            return;
        }

        updateMovement(deltaTime);
        updateStatus();

    }

    private void checkGameOver()
    {
        if(playerComponent.health <= 0 && !Settings.__PAUSED__)
        {
            Settings.__PAUSED__ = true;
            gameUI.gameOverWidget.gameOver();
            GameState.__START_DEAD_COUNTER__ = true;
        }
    }

    private void updateMovement(float delta)
    {
        float deltaX = -Gdx.input.getDeltaX() * Settings.__MOUSE_SENSITIVITY__;
        float deltaY = -Gdx.input.getDeltaY() * Settings.__MOUSE_SENSITIVITY__;

        tmp.set(0, 0, 0);
        camera.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);
        tmp.set(0, 0, 0);

        characterComponent.characterDirection.set(-1, 0, 0).rot(modelComponent.instance.transform).nor();
        characterComponent.walkDirection.set(0, 0, 0);

        Vector3 prePosition = player.getComponent(ModelComponent.class).instance.transform.getTranslation(new Vector3());

        stepCounter ++;

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {
            characterComponent.walkDirection.add(camera.direction);
            if(prePosition.y < 6 && stepCounter % 40 == 0 && Settings.__SOUND_ENABLED__)
            {
                Assets.__STEP_SOUND__.stop();
                Assets.__STEP_SOUND__.play(0.05f);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            characterComponent.walkDirection.sub(camera.direction);
            if(prePosition.y < 6 && stepCounter % 40 == 0 && Settings.__SOUND_ENABLED__)
            {
                Assets.__STEP_SOUND__.stop();
                Assets.__STEP_SOUND__.play(0.05f);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            tmp.set(camera.direction).crs(camera.up).scl(-1);
            if(prePosition.y < 6 && stepCounter % 40 == 0 && Settings.__SOUND_ENABLED__)
            {
                Assets.__STEP_SOUND__.stop();
                Assets.__STEP_SOUND__.play(0.05f);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            tmp.set(camera.direction).crs(camera.up).scl(1);
            if(prePosition.y < 6 && stepCounter % 40 == 0 && Settings.__SOUND_ENABLED__)
            {
                Assets.__STEP_SOUND__.stop();
                Assets.__STEP_SOUND__.play(0.05f);
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.R) && !PlayerComponent.__IS_RELOADING__ && PlayerComponent.__SHOOT_AMMO__ < 15 && PlayerComponent.__TOTAL_AMMO__ > 0)
        {
            player.getComponent(PlayerComponent.class).reloadingTime = 0;
            PlayerComponent.__IS_RELOADING__ = true;
            PlayerComponent.__TOTAL_AMMO__ -= 15;

            if(PlayerComponent.__TOTAL_AMMO__ < 0) {
                PlayerComponent.__REST__ = 15 - Math.abs(PlayerComponent.__TOTAL_AMMO__);
                PlayerComponent.__TOTAL_AMMO__ = 0;
            }

            if(Settings.__SOUND_ENABLED__)
            {
                Assets.__GUN_RELOAD_SOUND__.play(1.5f);
            }
            gun.getComponent(AnimationComponent.class).animate("P90Armature|Reload", 1, 0.15f);

        }
        else if(PlayerComponent.__TOTAL_AMMO__ <= 0 && PlayerComponent.__SHOOT_AMMO__ <= 0)
        {
            PlayerComponent.__IS_AMMO_EMPTY__ = true;
        }
        else if(PlayerComponent.__TOTAL_AMMO__ > 0)
        {
            PlayerComponent.__IS_AMMO_EMPTY__ = false;
        }

        if(stepCounter > 1000000) stepCounter = 0;

        characterComponent.walkDirection.add(tmp);
        characterComponent.walkDirection.scl(GameState.__PLAYER_SPEED__);
        characterComponent.characterController.setWalkDirection(characterComponent.walkDirection);

        Matrix4 ghost = new Matrix4();
        Vector3 translation = new Vector3();

        characterComponent.ghostObject.getWorldTransform(ghost);
        ghost.getTranslation(translation);

        modelComponent.instance.transform.set(translation.x, translation.y, translation.z, camera.direction.x, camera.direction.y, camera.direction.z, 0);
        camera.position.set(translation.x, translation.y, translation.z);
        camera.update(true);


        if (Gdx.input.justTouched() && !PlayerComponent.__IS_RELOADING__ && PlayerComponent.__SHOOT_AMMO__ > 0)
        {
            fire();
            updateAmmo();
        }
        else if (player.getComponent(PlayerComponent.class).reloadingTime < 2.5f && PlayerComponent.__IS_RELOADING__)
        {
            player.getComponent(PlayerComponent.class).reloadingTime += delta;

            if (player.getComponent(PlayerComponent.class).reloadingTime >= 2.5f)
            {
                PlayerComponent.__DONE_RELOADING__ = true;
            }
        }
        else if(PlayerComponent.__DONE_RELOADING__)
        {
            PlayerComponent.__DONE_RELOADING__ = false;
            if(PlayerComponent.__REST__ != 0)
            {
                PlayerComponent.__SHOOT_AMMO__ = PlayerComponent.__REST__;
                PlayerComponent.__REST__ = 0;
            }
            else
            {
                PlayerComponent.__SHOOT_AMMO__ = 15;
            }
             PlayerComponent.__IS_RELOADING__ = false;
        }

        Vector3 position = player.getComponent(ModelComponent.class).instance.transform.getTranslation(new Vector3());

        if(position.y < 6)
        {
            player.getComponent(PlayerComponent.class).jumpable = true;
        }
        else
        {
            player.getComponent(PlayerComponent.class).jumpable = false;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.getComponent(PlayerComponent.class).jumpable)
        {
            characterComponent.characterController.jump(new Vector3(0, 15, 0));
        }

        if(player.getComponent(PlayerComponent.class).health <= 0)
        {
            PlayerComponent.__IS_DEAD__ = true;
        }

    }

    private void updateAmmo()
    {
        PlayerComponent.__SHOOT_AMMO__ -= 1;
    }

    private void updateStatus()
    {
        gameUI.healthWidget.setValue(playerComponent.health);
    }

    private void fire()
    {
        Ray ray = camera.getPickRay((float) Core.SCREEN_WIDTH / 2, (float) Core.SCREEN_HEIGHT / 2);
        rayFrom.set(ray.origin);
        rayTo.set(ray.direction).scl(500f).add(rayFrom);

        rayTestCB.setCollisionObject(null);
        rayTestCB.setClosestHitFraction(1f);
        rayTestCB.setRayFromWorld(rayFrom);
        rayTestCB.setRayToWorld(rayTo);

        gameWorld.getBulletSystem().collisionWorld.rayTest(rayFrom, rayTo, rayTestCB);

        if (rayTestCB.hasHit())
        {
            btCollisionObject obj = rayTestCB.getCollisionObject();

            if (((Entity) obj.userData).getComponent(EnemyComponent.class) != null)
            {
                ((Entity) obj.userData).getComponent(EnemyComponent.class).health -= player.getComponent(PlayerComponent.class).damage;

                if (((Entity) obj.userData).getComponent(EnemyComponent.class).health <= 0 && ((Entity) obj.userData).getComponent(StatusComponent.class).alive)
                {
                    ((Entity) obj.userData).getComponent(StatusComponent.class).setAlive(false);

                    PlayerComponent.__SCORE__ += 100;
                    PlayerComponent.__KILL_COUNTER__ += 1;

                    if (PlayerComponent.__KILL_COUNTER__ % 5 == 0)
                    {
                        GameState.__ENEMY_SPAWN_NUMBER__ += 1;
                    }

                    gameWorld.getBulletSystem().removeBody(((Entity) obj.userData));
                }
                else
                {
                    if (((Entity) obj.userData).getComponent(StatusComponent.class).alive)
                    {
                        ((Entity) obj.userData).getComponent(AnimationComponent.class).animate(EnemyAnimations.__HURT_ANIMATION__[(int) (Math.random() * 10) % 5], 1, 1);
                        ((Entity) obj.userData).getComponent(EnemyComponent.class).state = EnemyComponent.STATE.HURT;
                    }
                }
            }
        }

        gun.getComponent(AnimationComponent.class).animate("P90Armature|Fire", 1, 0.5f);

        if (Settings.__SOUND_ENABLED__)
        {
            Assets.__GUN_SHOT_SOUND__.play(.1f);
        }
    }

    @Override
    public void entityAdded(Entity entity)
    {
        player = entity;
        playerComponent = player.getComponent(PlayerComponent.class);
        characterComponent = player.getComponent(CharacterComponent.class);
        modelComponent = player.getComponent(ModelComponent.class);
    }

    @Override
    public void entityRemoved(Entity entity) { }
}
