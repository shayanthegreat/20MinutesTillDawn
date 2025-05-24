package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public enum MonsterType {
    Tentacle("tentacle", 25, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("tentacle/BrainMonster_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("tentacle/BrainMonster_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("tentacle/BrainMonster_2.png"))))),
    EyeBat("EyeBat", 50, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("eyeBat/T_EyeBat_3.png"))))),
    Shub("Shub", 400, new Animation<>(0.5f, new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_0.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_1.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_2.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_3.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_4.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_5.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_6.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_7.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_8.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_9.png"))),
        new TextureRegion(new Texture(Gdx.files.internal("shub/T_ShubNiggurath_10.png"))))),
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
