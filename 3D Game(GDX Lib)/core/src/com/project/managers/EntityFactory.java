package com.project.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.project.components.*;
import com.project.game.Assets;
import com.project.states.GameState;
import com.project.states.MotionState;
import com.project.systems.BulletSystem;

public class        EntityFactory
{
    private static ModelBuilder modelBuilder = new ModelBuilder();
    private static Material material = new Material(TextureAttribute.createDiffuse(Assets.__PLAYER_TEXTURE__));
    private static Model playerModel = modelBuilder.createCapsule(2f, 6f, 16, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);

    public static Entity createCharacter(BulletSystem bulletSystem, float x, float y, float z)
    {
        Entity entity = new Entity();

        ModelComponent modelComponent = new ModelComponent(playerModel, x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.ghostObject = new btPairCachingGhostObject();
        characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        characterComponent.ghostShape = new btCapsuleShape(2f, 1f);
        characterComponent.ghostObject.setCollisionShape(characterComponent.ghostShape);
        characterComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        characterComponent.characterController = new btKinematicCharacterController(characterComponent.ghostObject, characterComponent.ghostShape, .35f,new Vector3(0,1f,0));
        characterComponent.ghostObject.userData = entity;
        characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        entity.add(characterComponent);

        bulletSystem.collisionWorld.addCollisionObject(entity.getComponent(CharacterComponent.class).ghostObject, (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter, (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        bulletSystem.collisionWorld.addAction(entity.getComponent(CharacterComponent.class).characterController);

        return entity;
    }

    public static Entity createPlayer(BulletSystem bulletSystem, float x, float y, float z)
    {
        Entity entity = createCharacter(bulletSystem, x, y, z);
        entity.add(new PlayerComponent());

        return entity;
    }

    public static Entity loadGun(float x, float y, float z)
    {
        Entity gunEntity = new Entity();

        ModelComponent modelComponent = new ModelComponent(Assets.__GUN_MODEL__, x, y, z);

        gunEntity.add(modelComponent);
        gunEntity.add(new GunComponent());
        gunEntity.add(new AnimationComponent(modelComponent.instance));

        return gunEntity;
    }

    public static Entity loadScene(int x, int y, int z)
    {
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(Assets.__GROUND_MODEL__, x, y, z);
        entity.add(modelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        btCollisionShape collisionShape = Bullet.obtainStaticNodeShape(Assets.__GROUND_MODEL__.nodes);
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, collisionShape, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);

        return entity;
    }

    public static Entity createEnemy(BulletSystem bulletSystem, float x, float y, float z)
    {
        Entity entity = new Entity();

        ModelComponent modelComponent = new ModelComponent(Assets.__GHOUL_MODEL__, x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = new CharacterComponent();
        characterComponent.ghostObject = new btPairCachingGhostObject();
        characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        characterComponent.ghostShape = new btCapsuleShape(1.5f, 3f);
        characterComponent.ghostObject.setCollisionShape(characterComponent.ghostShape);
        characterComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        characterComponent.characterController = new btKinematicCharacterController(characterComponent.ghostObject, characterComponent.ghostShape, .35f, new Vector3(0, 1, 0));
        characterComponent.ghostObject.userData = entity;
        characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
        entity.add(characterComponent);

        bulletSystem.collisionWorld.addCollisionObject(entity.getComponent(CharacterComponent.class).ghostObject, (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter, (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        bulletSystem.collisionWorld.addAction(entity.getComponent(CharacterComponent.class).characterController);

        entity.add(characterComponent);
        entity.add(new EnemyComponent(EnemyComponent.STATE.HUNTING));

        AnimationComponent animationComponent = new AnimationComponent(modelComponent.instance);
        animationComponent.animate(GameState.__ENEMY_SPEED__ < GameState.__ENEMY_RUNNING_SPEED__ ? EnemyAnimations.__WALK_ANIMATION__[(int) (Math.random() * 10) % 2] : EnemyAnimations.__RUN_ANIMATION__, -1, 0.5f);

        entity.add(animationComponent);
        entity.add(new StatusComponent(animationComponent));

        if(Settings.__SOUND_ENABLED__)
        {
            Assets.__GHOUL_SOUND__.play(.4f);
        }
        return entity;
    }

    public static Entity loadWall(int x, int y, int z)
    {
        Entity entity = new Entity();

        ModelComponent modelComponent = new ModelComponent(Assets.__WALL_MODEL__, x, y, z);
        entity.add(modelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        btCollisionShape collisionShape = Bullet.obtainStaticNodeShape(Assets.__WALL_MODEL__.nodes);
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, collisionShape, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);

        entity.add(bulletComponent);

        return entity;
    }

    public static Entity loadSkulls(int x, int y, int z)
    {
        Entity entity = new Entity();

        ModelComponent modelComponent = new ModelComponent(Assets.__SKULL_MODEL__, x, y, z);
        entity.add(modelComponent);

        return entity;
    }

    public static Entity createAmmo(int x, int y, int z)
    {
        Entity entity = new Entity();

        Model boxModel = modelBuilder.createBox(3f, 3f, 3f, new Material(TextureAttribute.createDiffuse(Assets.__AMMO_TEXTURE__)), VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);
        ModelComponent modelComponent = new ModelComponent(boxModel, x, y, z);
        entity.add(modelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        btCollisionShape collisionShape = new btBoxShape(new Vector3(1.5f, 1.5f, 1.5f));
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(1f, null, collisionShape, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);

        entity.add(bulletComponent);
        entity.add(new AmmoComponent());

        entity.add(new StatusComponent(null));

        return entity;
    }

    public static Entity createHeal(int x, int y, int z)
    {
        Entity entity = new Entity();

        Model boxModel = modelBuilder.createBox(3f, 3f, 3f, new Material(TextureAttribute.createDiffuse(Assets.__HEAL_TEXTURE__)), VertexAttributes.Usage.Normal | VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates);
        ModelComponent modelComponent = new ModelComponent(boxModel, x, y, z);
        entity.add(modelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        btCollisionShape collisionShape = new btBoxShape(new Vector3(1.5f, 1.5f, 1.5f));
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(1f, null, collisionShape, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);

        entity.add(bulletComponent);
        entity.add(new HealComponent());

        entity.add(new StatusComponent(null));

        return entity;
    }
}
