package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public enum MonsterType {
    Tentacle("tentacle", 25, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("tentacle/TentacleSpawn0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("tentacle/TentacleSpawn1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("tentacle/TentacleSpawn2.png"))))),
    EyeBat("EyeBat", 50, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_3.png"))))),
    ;
    private String name;
    private int hp;
    private Animation<TextureRegion> animation;

    MonsterType(String name, int hp, Animation<TextureRegion> animation) {
        this.name = name;
        this.hp = hp;
        this.animation = animation;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }
}
