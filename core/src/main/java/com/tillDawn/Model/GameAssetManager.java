package com.tillDawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager = new GameAssetManager();
    public static GameAssetManager getInstance() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private String character1_idle0 = "player1.png";
    private String character1_idle1 = "player2.png";
    private String character1_idle2 = "player3.png";
    private String bullet1 = "bullet3.png";
    private String bullet2 = "bullet2.png";
    private String tree = "item/tree.png";
    Texture texture1 = new Texture(Gdx.files.internal(character1_idle0));
    Texture texture2 = new Texture(Gdx.files.internal(character1_idle1));
    Texture texture3 = new Texture(Gdx.files.internal(character1_idle2));
    Texture bulletTexture1 = new Texture(Gdx.files.internal(bullet1));
    Texture bulletTexture2 = new Texture(Gdx.files.internal(bullet2));
    Texture treeTexture = new Texture(Gdx.files.internal(tree));
    private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, texture1, texture2, texture3);

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Texture getTexture1() {
        return texture1;
    }

    public Texture getTexture2() {
        return texture2;
    }

    public Texture getTexture3() {
        return texture3;
    }

    public Animation<TextureRegion> getCharacter1_idle_frames() {
        TextureRegion[] frames = new TextureRegion[] {
            new TextureRegion(texture1),
            new TextureRegion(texture2),
            new TextureRegion(texture3)
        };
        return new Animation<>(0.2f, frames);
    }

    public Texture getBulletTexture1() {
        return bulletTexture1;
    }

    public Texture getBulletTexture2() {
        return bulletTexture2;
    }

    public Texture getTreeTexture() {
        return treeTexture;
    }
}
