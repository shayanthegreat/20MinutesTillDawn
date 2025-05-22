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

    private String bullet1 = "bullet3.png";
    private String bullet2 = "bullet2.png";
    private String tree = "item/tree.png";
    Texture bulletTexture1 = new Texture(Gdx.files.internal(bullet1));
    Texture bulletTexture2 = new Texture(Gdx.files.internal(bullet2));
    Texture treeTexture = new Texture(Gdx.files.internal(tree));

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }


    public Animation<TextureRegion> getCharacter_idle_frames() {
        String heroName = App.getInstance().getCurrentCharacter().getName();
        String character1_idle0 = "Heros/"+heroName+"/idle/Idle_0.png";
        String character1_idle1 = "Heros/"+heroName+"/idle/Idle_1.png";
        String character1_idle2 = "Heros/"+heroName+"/idle/Idle_2.png";
        String character1_idle3 = "Heros/"+heroName+"/idle/Idle_3.png";
        String character1_idle4 = "Heros/"+heroName+"/idle/Idle_4.png";
        String character1_idle5 = "Heros/"+heroName+"/idle/Idle_5.png";
        Texture texture0 = new Texture(Gdx.files.internal(character1_idle0));
        Texture texture1 = new Texture(Gdx.files.internal(character1_idle1));
        Texture texture2 = new Texture(Gdx.files.internal(character1_idle2));
        Texture texture3 = new Texture(Gdx.files.internal(character1_idle3));
        Texture texture4 = new Texture(Gdx.files.internal(character1_idle4));
        Texture texture5 = new Texture(Gdx.files.internal(character1_idle5));
        TextureRegion[] frames = new TextureRegion[] {
            new TextureRegion(texture0),
            new TextureRegion(texture1),
            new TextureRegion(texture2),
            new TextureRegion(texture3),
            new TextureRegion(texture4),
            new TextureRegion(texture5)
        };
        return new Animation<>(0.5f, frames);
    }

    public Animation<TextureRegion> getCharacter_run_frames() {
        String heroName = App.getInstance().getCurrentCharacter().getName();
        String character1_run0 = "Heros/"+heroName+"/run/Run_0.png";
        String character1_run1 = "Heros/"+heroName+"/run/Run_1.png";
        String character1_run2 = "Heros/"+heroName+"/run/Run_2.png";
        String character1_run3 = "Heros/"+heroName+"/run/Run_3.png";
        Texture texture0 = new Texture(Gdx.files.internal(character1_run0));
        Texture texture1 = new Texture(Gdx.files.internal(character1_run1));
        Texture texture2 = new Texture(Gdx.files.internal(character1_run2));
        Texture texture3 = new Texture(Gdx.files.internal(character1_run3));
        TextureRegion[] frames = new TextureRegion[] {
            new TextureRegion(texture0),
            new TextureRegion(texture1),
            new TextureRegion(texture2),
            new TextureRegion(texture3),
        };
        return new Animation<>(0.5f, frames);
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
