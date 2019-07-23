package com.project.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component
{
    public static int __SCORE__;
    public static int __TOTAL_AMMO__;
    public static int __SHOOT_AMMO__;
    public static boolean __IS_RELOADING__;
    public static boolean __IS_AMMO_EMPTY__;
    public static int __KILL_COUNTER__;
    public static boolean __DONE_RELOADING__;
    public static boolean __IS_DEAD__;
    public static int __REST__;

    public float health;
    public int damage;
    public boolean jumpable;
    public float reloadingTime;

    public PlayerComponent()
    {
        reset();
        reloadingTime = 0;
        jumpable = false;
        health = 100;
        damage = GunComponent.__DAMAGE__;
    }

    private static void reset()
    {
        __DONE_RELOADING__ = false;
        __KILL_COUNTER__ = 0;
        __IS_RELOADING__ = false;
        __TOTAL_AMMO__ = 150;
        __SHOOT_AMMO__ = 15;
        __SCORE__ = 0;
        __IS_DEAD__ = false;
        __REST__ = 0;
    }
}
