package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public enum MonsterType {
    Tentacle("tentacle", 25, 60, 60, "tentacle/BrainMonster_%d.png", 3, 0.5f),
    EyeBat("EyeBat", 50, 60, 60, "eyeBat/T_EyeBat_%d.png", 4, 0.5f),
    Shub("Shub", 4000, 120, 120, "shub/T_ShubNiggurath_%d.png", 11, 0.5f);

    private String name;
    private int hp;
    private Animation<TextureRegion> animation;
    private int width;
    private int height;
    private String animationPathPattern;
    private int frameCount;
    private float frameDuration;

    MonsterType(String name, int hp, int width, int height, String animationPathPattern, int frameCount, float frameDuration) {
        this.name = name;
        this.hp = hp;
        this.width = width;
        this.height = height;
        this.animationPathPattern = animationPathPattern;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;
        loadAnimation();
    }

    public void loadAnimation() {
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            String path = String.format(animationPathPattern, i);
            Texture tex = new Texture(Gdx.files.internal(path));
            frames[i] = new TextureRegion(tex);
        }
        animation = new Animation<>(frameDuration, frames);
    }

    public Animation<TextureRegion> getAnimation() {
        if (animation == null) {
            loadAnimation();
        }
        return animation;
    }
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
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
