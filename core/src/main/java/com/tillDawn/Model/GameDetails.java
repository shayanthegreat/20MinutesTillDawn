package com.tillDawn.Model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class GameDetails {
    public ArrayList<Tree> trees = new ArrayList<>();
    public boolean autoReload = false;
    public boolean sfxSound = false;
    public boolean musicSound = false;
    public int gameTime;
    public boolean autoAim;
    public boolean bigBoss = false;
    public ArrayList<Monster> monsters = new ArrayList<>();

    public ArrayList<Bullet> bullets = new ArrayList<>();

    public ArrayList<Bullet> enemyBullets = new ArrayList<>();
    @JsonIgnore
    public Fence fence;
    public float elapsedTime = 0f;
    public FenceData fenceData;
    @JsonIgnore
    public transient ShapeRenderer shapeRenderer;
    public Player player;

    public ArrayList<Egg> eggs = new ArrayList<>();
}
