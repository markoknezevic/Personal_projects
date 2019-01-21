package com.project.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;

public class Settings
{
    public static boolean __PAUSED__;
    public static boolean __SOUND_ENABLED__ = true;
    public static boolean __MUSIC_ENABLED__ = true;
    public static boolean __CHRISTMAS_THEME__ = true;
    public static float __MOUSE_SENSITIVITY__ = 0.5f;

    public static ArrayList<String> __USERNAMES__ = new ArrayList<String>();
    public static ArrayList<Integer> __HIGHSCORES__ = new ArrayList<Integer>();
    public static final String file = ".game";

    public Settings()
    {
        for(int i = 0; i < 5; i++)
        {
            __HIGHSCORES__.add(500);
            __USERNAMES__.add("undefined");
        }
    }

    public static void load()
    {
        try
        {
            FileHandle fileHandle = Gdx.files.external(file);
            String[] strings = fileHandle.readString().split("\n");
            __SOUND_ENABLED__ = Boolean.parseBoolean(strings[0]);
            __MUSIC_ENABLED__ = Boolean.parseBoolean(strings[1]);
            __CHRISTMAS_THEME__ = Boolean.parseBoolean(strings[12]);
            for(int i=2;i<=10;i+=2)
            {
                __HIGHSCORES__.add(Integer.parseInt(strings[i]) );
                __USERNAMES__.add(strings[i + 1]);
            }
            __MOUSE_SENSITIVITY__ = Float.parseFloat(strings[13]);
            sort();
        }
        catch (Throwable e)
        {
            sort();
        }
    }

    private static void sort()
    {
        for(int i = 0; i < __HIGHSCORES__.size() - 1; i++)
        {
            for(int j = i + 1; j < __HIGHSCORES__.size(); j++)
            {
                if(__HIGHSCORES__.get(i) <= __HIGHSCORES__.get(j))
                {
                    int scoreTemp = __HIGHSCORES__.get(i);
                    String nameTemp = __USERNAMES__.get(i);

                    __HIGHSCORES__.set(i,__HIGHSCORES__.get(j));
                    __HIGHSCORES__.set(j, scoreTemp);

                    __USERNAMES__.set(i,__USERNAMES__.get(j));
                    __USERNAMES__.set(j, nameTemp);
                }
            }
        }
    }

    public static void save()
    {
        try
        {
            FileHandle fileHandle = Gdx.files.external(file);
            fileHandle.writeString(Boolean.toString(__SOUND_ENABLED__) + "\n", false);
            fileHandle.writeString(Boolean.toString(__MUSIC_ENABLED__) + "\n", true);

            for(int i = 0; i < 5; i++)
            {
                fileHandle.writeString(Integer.toString(__HIGHSCORES__.get(i)) + "\n" + __USERNAMES__.get(i) + "\n", true);
            }
            fileHandle.writeString(Boolean.toString(__CHRISTMAS_THEME__) + "\n", true);
            fileHandle.writeString(Float.toString(__MOUSE_SENSITIVITY__) + "\n", true);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    public static void addScore(int score, String username)
    {
        __HIGHSCORES__.add(score);
        __USERNAMES__.add(username);
        sort();
    }
}
