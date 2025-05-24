package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public enum MonsterType {
    Tentacle("tentacle", 25, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("tentacle/BrainMonster_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("tentacle/BrainMonster_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("tentacle/BrainMonster_2.png")))), 60, 60),
    EyeBat("EyeBat", 50, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_3.png")))), 60, 60),
    Shub("Shub", 4000, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_3.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_4.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_5.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_6.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_7.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_8.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_9.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_10.png")))), 120, 120),
        ;
    private String name;
    private int hp;
    private Animation<TextureRegion> animation;
    private int width;
    private int height;

    MonsterType(String name, int hp, Animation<TextureRegion> animation, int width, int height) {
        this.name = name;
        this.hp = hp;
        this.animation = animation;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
