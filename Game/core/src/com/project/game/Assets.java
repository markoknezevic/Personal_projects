package com.project.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.project.managers.FrameRate;
import com.project.managers.GifDecoder;

public class Assets
{
    public static Sound __GUN_SHOT_SOUND__;
    public static Sound __STEP_SOUND__;
    public static Sound __GUN_RELOAD_SOUND__;
    public static Sound __GHOUL_SOUND__;
    public static Sound __GHOUL_DEAD_SOUND__;
    public static Sound __HURT_SOUND__;
    public static Sound __COLLECTED_SOUND__;

    public static Music __GAME_MUSIC__;
    public static Music __MENU_MUSIC__;

    public static Skin __LIBGDX_FLATEARTH_SKIN__;
    public static Skin __LIBGDX_DEFAULT_SKIN__;

    public static Model __GHOUL_MODEL__;
    public static Model __GUN_MODEL__;
    public static Model __GROUND_MODEL__;
    public static Model __WALL_MODEL__;
    public static Model __SKULL_MODEL__;

    public static FrameRate __FRAME_RATE__;

    public static Animation<TextureRegion> __SNOWFALL_ANIMATION__;
    public static Animation<TextureRegion> __BILL_CHIPER_ANIMATION__;

    public static Texture __GAME_TITLE_TEXTURE__;
    public static Texture __GAME_TITLE_NEW_TEXTURE__;
    public static Texture __GAME_TITLE_CHRISTMAS_TEXTURE__;
    public static Texture __BLOOD_TEXTURE__;
    public static Texture __PLAYER_TEXTURE__;
    public static Texture __CROSSHAIR_DOT_TEXTURE__;
    public static Texture __CROSSHAIR_INNER_RING_TEXTURE__;
    public static Texture __GAME_OVER_TEXTURE__;
    public static Texture __AMMO_TEXTURE__;
    public static Texture __HEAL_TEXTURE__;
    public static Texture __FRAME__;

    public Assets()
    {
        initializeSkin();
        initializeSounds();
        initializeSpritesAndTextures();
        initializeModels();
        initializeFrameRate();
    }

    private void initializeSkin()
    {
        __LIBGDX_FLATEARTH_SKIN__ = new Skin();
        FileHandle fileHandle = Gdx.files.internal("core/assets/skin/flat-earth-ui.json");
        FileHandle atlasFile = fileHandle.sibling("flat-earth-ui.atlas");
        if(atlasFile.exists())
        {
            __LIBGDX_FLATEARTH_SKIN__.addRegions(new TextureAtlas(atlasFile));
        }
        __LIBGDX_FLATEARTH_SKIN__.load(fileHandle);

        __LIBGDX_DEFAULT_SKIN__ = new Skin();
        FileHandle fileHandle1 = Gdx.files.internal("core/assets/skin1/uiskin.json");
        FileHandle atlasFile1 = fileHandle1.sibling("uiskin.atlas");
        if(atlasFile1.exists())
        {
            __LIBGDX_DEFAULT_SKIN__.addRegions(new TextureAtlas(atlasFile1));
        }
        __LIBGDX_DEFAULT_SKIN__.load(fileHandle1);
    }

    private void initializeSounds()
    {
        __GAME_MUSIC__ = Gdx.audio.newMusic(Gdx.files.internal("core/assets/music/gameMusic.mp3"));

        __MENU_MUSIC__ = Gdx.audio.newMusic(Gdx.files.internal("core/assets/music/menu.mp3"));
        __GUN_SHOT_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/shot.wav"));
        __STEP_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/step1.wav"));
        __GUN_RELOAD_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/reload.wav"));
        __GHOUL_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/ghoul.wav"));
        __GHOUL_DEAD_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/skeleton.wav"));
        __HURT_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/hurt.wav"));
        __COLLECTED_SOUND__ = Gdx.audio.newSound(Gdx.files.internal("core/assets/music/collect.wav"));
    }

    private void initializeSpritesAndTextures()
    {
        __SNOWFALL_ANIMATION__ = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/textures/Snow.gif").read());
        __BILL_CHIPER_ANIMATION__ = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("core/assets/textures/hidden.gif").read());

        __GAME_TITLE_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/Ghoul-Slayer.png"));
        __GAME_TITLE_NEW_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/Ghoul-Slayer-new.png"));
        __GAME_TITLE_CHRISTMAS_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/titlesanta.png"));
        __BLOOD_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/blood.png"));
        __PLAYER_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/badlogic.jpg"));
        __CROSSHAIR_DOT_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/crossHairPoint.png"));
        __CROSSHAIR_INNER_RING_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/crossHairInnerRing.png"));
        __GAME_OVER_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/Game-Over.png"));
        __AMMO_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/ammo3.png"));
        __HEAL_TEXTURE__ = new Texture(Gdx.files.internal("core/assets/textures/heal.png"));
        __FRAME__ = new Texture(Gdx.files.internal("core/assets/textures/frame1.jpg"));
    }

    private void initializeModels()
    {
        ModelLoader objLoader = new ObjLoader();
        ModelLoader<?> modelLoader = new G3dModelLoader(new JsonReader());

        ModelData modelDataForEnemy = modelLoader.loadModelData(Gdx.files.internal("core/assets/models/try.g3dj"));
        __GHOUL_MODEL__ = new Model(modelDataForEnemy, new TextureProvider.FileTextureProvider());

        ModelData modelDataForGun = modelLoader.loadModelData(Gdx.files.internal("core/assets/models/P90.g3dj"));
        __GUN_MODEL__ = new Model(modelDataForGun, new TextureProvider.FileTextureProvider());

        __GROUND_MODEL__ = objLoader.loadModel(Gdx.files.internal("core/assets/untitled.obj"));

        __WALL_MODEL__ = objLoader.loadModel(Gdx.files.internal("core/assets/plains.obj"));
        __SKULL_MODEL__ = objLoader.loadModel(Gdx.files.internal("core/assets/skull.obj"));

    }

    private void initializeFrameRate()
    {
        __FRAME_RATE__ = new FrameRate();
    }

    public static void dispose()
    {
        __LIBGDX_FLATEARTH_SKIN__.dispose();
        __LIBGDX_DEFAULT_SKIN__.dispose();

        __GUN_SHOT_SOUND__.dispose();
        __STEP_SOUND__.dispose();
        __GUN_RELOAD_SOUND__.dispose();
        __GHOUL_SOUND__.dispose();
        __GHOUL_DEAD_SOUND__.dispose();
        __HURT_SOUND__.dispose();
        __COLLECTED_SOUND__.dispose();

        __GAME_MUSIC__.dispose();

        __GHOUL_MODEL__.dispose();
        __GUN_MODEL__.dispose();
        __GROUND_MODEL__.dispose();
        __WALL_MODEL__.dispose();
        __SKULL_MODEL__.dispose();
        __FRAME_RATE__.dispose();

        __GAME_TITLE_TEXTURE__.dispose();
        __GAME_TITLE_NEW_TEXTURE__.dispose();
        __GAME_TITLE_CHRISTMAS_TEXTURE__.dispose();
        __BLOOD_TEXTURE__.dispose();
        __PLAYER_TEXTURE__.dispose();
        __CROSSHAIR_DOT_TEXTURE__.dispose();
        __CROSSHAIR_INNER_RING_TEXTURE__.dispose();
        __GAME_OVER_TEXTURE__.dispose();
        __AMMO_TEXTURE__.dispose();
        __HEAL_TEXTURE__.dispose();
        __FRAME__.dispose();

    }

}
